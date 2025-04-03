package com.nadila.blogapi.Data;

import com.nadila.blogapi.enums.Roles;
import com.nadila.blogapi.model.Users;
import com.nadila.blogapi.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Transactional
@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
                createAdmin();
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    public void createAdmin(){

        String email = "Admin@gmail.com";
        String password = "Admin";
        String firstName = "Admin";
        String lastName = "Manager";
        String phoneNumber = "123456789";

        if (!userRepository.existsByEmail(email)) {
            Users user = new Users();
            user.setEmail(email);
            user.setPassword(passwordEncoder.encode(password));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPhone_number(phoneNumber);
            user.setRole(Roles.ADMIN);
            userRepository.save(user);
        }else {
            System.out.println("Admin already exists");
        }

    }
}
