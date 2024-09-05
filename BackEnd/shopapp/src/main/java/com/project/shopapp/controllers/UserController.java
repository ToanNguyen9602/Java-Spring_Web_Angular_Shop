package com.project.shopapp.controllers;

import com.project.shopapp.dtos.*;
import com.project.shopapp.models.User;
import com.project.shopapp.responses.LoginResponse;
import com.project.shopapp.responses.RegisterResponse;
import com.project.shopapp.responses.UserResponse;
import com.project.shopapp.services.IUserService;
import com.project.shopapp.components.LocalizationUtils;
import com.project.shopapp.utils.MessageKeys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
  private final IUserService userService;
  private final LocalizationUtils localizationUtils;

  @PostMapping("/register")
  public ResponseEntity<RegisterResponse> createUser (@Valid @RequestBody UserDTO userDTO,
                                                      BindingResult result
  ) {
    try {
      if (result.hasErrors()) {
        List<String> errorMessages = result.getFieldErrors().stream()
         .map(FieldError::getDefaultMessage)
         .toList();
        return ResponseEntity.badRequest().body(RegisterResponse.builder()
                        .message(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY))
                .build());
      }
      if  (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
        return ResponseEntity.badRequest().body(RegisterResponse.builder()
                .message(localizationUtils.getLocalizedMessage(MessageKeys.PASSWORD_NOT_MATCH))
                .build());
      }
      User user = userService.createUser(userDTO);
      return ResponseEntity.ok(RegisterResponse.builder().message(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_SUCCESSFULLY))
              .user(user).build());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(RegisterResponse.builder()
              .message(localizationUtils.getLocalizedMessage(MessageKeys.REGISTER_FAILED))
              .build());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login (@Valid @RequestBody UserLoginDTO userLoginDTO) {
    //kiểm tra thông tin đăng nhập và sinh token
    try {
      String token = userService.login(
              userLoginDTO.getPhoneNumber(),
              userLoginDTO.getPassword(),
              userLoginDTO.getRoleId() == null ? 1 : userLoginDTO.getRoleId());
      return ResponseEntity.ok(LoginResponse.builder()
              .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_SUCCESSFULLY))
                      .token(token)
              .build());
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(
        LoginResponse.builder()
                .message(localizationUtils.getLocalizedMessage(MessageKeys.LOGIN_FAILED, e.getMessage()))
                .build()
      );
    }
  }

  @PostMapping("/details")
  public ResponseEntity<UserResponse> getUserDetails(@RequestHeader("Authorization") String token) {
    try {
      String extractedToken = token.substring(7);
      User user = userService.getUserDetailsFromToken(extractedToken);
      return ResponseEntity.ok(UserResponse.fromUser(user));
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

}
