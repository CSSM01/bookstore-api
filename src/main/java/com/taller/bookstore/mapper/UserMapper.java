package com.taller.bookstore.mapper;

import com.taller.bookstore.dto.request.RegisterRequest;
import com.taller.bookstore.dto.response.UserResponse;
import com.taller.bookstore.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public User toEntity(RegisterRequest request, String encodedPassword) {
        return User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .build();
    }
}