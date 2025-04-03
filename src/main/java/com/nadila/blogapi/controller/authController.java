package com.nadila.blogapi.controller;

import com.nadila.blogapi.enums.ResponseStatus;
import com.nadila.blogapi.exception.AlreadyExistsException;
import com.nadila.blogapi.requests.LoginRequests;
import com.nadila.blogapi.requests.SiginUpRequest;
import com.nadila.blogapi.response.ApiResponse;
import com.nadila.blogapi.response.JwtResponse;
import com.nadila.blogapi.service.authService.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/auth")
public class authController {
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse> signin(@Valid @RequestBody LoginRequests loginRequests) {
        try {
            JwtResponse jwtResponse = authService.signin(loginRequests);
            return ResponseEntity.ok(new ApiResponse(ResponseStatus.SUCCESS, "Login successful", jwtResponse));

        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse(ResponseStatus.ERROR, "User not found", e.getMessage())
            );

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResponse(ResponseStatus.ERROR, "Invalid username or password", e.getMessage())
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse(ResponseStatus.FAILED, "An unexpected error occurred", e.getMessage())
            );
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse>signup(@Valid @ModelAttribute SiginUpRequest siginUpRequest) {
        try {
            JwtResponse jwtResponse = authService.signup(siginUpRequest);
            return ResponseEntity.ok(new ApiResponse(ResponseStatus.SUCCESS, "Login successful", jwtResponse));

        }catch (AlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ApiResponse(ResponseStatus.ERROR, "", e.getMessage())
            );
        }catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse(ResponseStatus.ERROR, "User not found", e.getMessage())
            );

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ApiResponse(ResponseStatus.ERROR, "Invalid username or password", e.getMessage())
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse(ResponseStatus.FAILED, "An unexpected error occurred", e.getMessage())
            );
        }
    }
}
