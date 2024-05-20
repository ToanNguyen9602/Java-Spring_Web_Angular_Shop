package com.project.shopapp.controllers;

import com.project.shopapp.dtos.*;
import com.project.shopapp.services.IUserService;
import com.project.shopapp.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
  private final IUserService userService;

  @PostMapping("/register")
  public ResponseEntity<?> createUser (@Valid @RequestBody UserDTO userDTO,
                                        BindingResult result
  ) {
    try {
      if (result.hasErrors()) {
        List<String> errorMessages = result.getFieldErrors().stream()
         .map(FieldError::getDefaultMessage)
         .toList();
        return ResponseEntity.badRequest().body(errorMessages);
      }
      if  (!userDTO.getPassword().equals(userDTO.getRetypePassword())) {
        return ResponseEntity.badRequest().body("Password not provided");
      }
      userService.createUser(userDTO);
      return ResponseEntity.ok("Register successfully");
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<String> login (@Valid @RequestBody UserLoginDTO userLoginDTO) {
    //kiểm tra thông tin đăng nhập và sinh token
    String token = userService.login(userLoginDTO.getPhoneNumber(), userLoginDTO.getPassword());
    //trả về token trong response
    return ResponseEntity.ok(token);
  }

}
