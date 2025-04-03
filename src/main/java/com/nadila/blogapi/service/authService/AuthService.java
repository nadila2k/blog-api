package com.nadila.blogapi.service.authService;

import com.nadila.blogapi.Dto.UserDto;
import com.nadila.blogapi.InbildObjects.ImageObj;
import com.nadila.blogapi.config.jwt.JwtUtil;
import com.nadila.blogapi.enums.Roles;
import com.nadila.blogapi.exception.AlreadyExistsException;
import com.nadila.blogapi.model.Users;
import com.nadila.blogapi.repository.UserRepository;
import com.nadila.blogapi.requests.LoginRequests;
import com.nadila.blogapi.requests.SiginUpRequest;
import com.nadila.blogapi.response.JwtResponse;
import com.nadila.blogapi.service.image.IimageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final IimageService iimageService;


    public JwtResponse signup(@ModelAttribute SiginUpRequest siginUpRequest) {
        return Optional.of(siginUpRequest)
                .filter(request -> !userRepository.existsByEmail(request.getEmail())) // Fix filter usage
                .map(request -> {

                    Users user = new Users();
                    if (siginUpRequest.getImage() != null) {
                        ImageObj imageObj= iimageService.uploadImage(siginUpRequest.getImage());
                        user.setImageName(imageObj.getImageName());
                        user.setUrl(imageObj.getUrl());
                    }

                    user.setEmail(request.getEmail());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setPhone_number(request.getPhone_number());
                    user.setRole(Roles.ARUTHER);
                    userRepository.save(user);

                    return signin(new LoginRequests(request.getEmail(), request.getPassword()));
                })
                .orElseThrow(() -> new AlreadyExistsException("User already exists"));
    }




    public JwtResponse signin(LoginRequests loginRequests) {

        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequests.getEmail(), loginRequests.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtil.generateTokenForUser(authentication);
            BlogUserDetails userDetails = (BlogUserDetails) authentication.getPrincipal();

            UserDto userDto = modelMapper.map(userDetails.getUser(), UserDto.class);
            return new JwtResponse(jwt, userDto);
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }
}
