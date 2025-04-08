package com.nadila.blogapi.service.authService;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class GetAuthId {

    public  Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof BlogUserDetails) {
            BlogUserDetails userDetails = (BlogUserDetails) authentication.getPrincipal();
            return userDetails.getUser().getId(); // or userDetails.getId() if you added that
        }

        throw new RuntimeException("User not authenticated");
    }
}
