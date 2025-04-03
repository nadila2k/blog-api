package com.nadila.blogapi.Data;

import com.nadila.blogapi.enums.Roles;
import com.nadila.blogapi.model.Category;
import com.nadila.blogapi.model.Users;
import com.nadila.blogapi.repository.CategoryRepository;
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

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
                createAdmin();
        createCategory();

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


    public void createCategory(){
        List<Category> categories = Arrays.asList(
                new Category(null, "Technology"),
                new Category(null, "Lifestyle"),
                new Category(null, "Health & Fitness"),
                new Category(null, "Food & Recipes"),
                new Category(null, "Travel")
        );

        for (Category category : categories) {
            if (!categoryRepository.existsByName(category.getName())) {
                categoryRepository.save(category);
            } else {
                System.out.println("Category " + category.getName() + " already exists");
            }
        }
    }
}
