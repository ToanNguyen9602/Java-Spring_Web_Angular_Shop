package com.project.shopapp.services;

import com.project.shopapp.components.JwtTokenUtils;
import com.project.shopapp.dtos.UpdateUserDTO;
import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.exception.PermissionDenyException;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService{
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenUtils jwtTokenUtils;
  private final AuthenticationManager authenticationManager;
  @Override
  public User createUser(UserDTO userDTO) throws Exception {
    //register user
    String phoneNumber = userDTO.getPhoneNumber();
    //check xem username hay phone đã tồn tại chưa
    if (userRepository.existsByPhoneNumber(phoneNumber)) {
      throw new DataIntegrityViolationException("Phone number already exists");
    }
    Role role = roleRepository.findById(userDTO.getRoleId())
            .orElseThrow(()-> new DataNotFoundException("Role not found"));
    if (role.getName().toUpperCase().equals(Role.ADMIN)) {
      throw new PermissionDenyException("You cannot register an admin account");
    }
    //convert userDTO sang User
    User newUser = User.builder()
                                .fullName(userDTO.getFullName())
                                .phoneNumber(userDTO.getPhoneNumber())
                                .password(userDTO.getPassword())
                                .address(userDTO.getAddress())
                                .dateOfBirth(userDTO.getDateOfBirth())
                                .facebookAccountId(userDTO.getFacebookAccountId())
                                .googleAccountId(userDTO.getGoogleAccountId())
                                .active(true)
                                .build();

    //KTra nếu có accountId, không yêu cầu pass
    if (userDTO.getFacebookAccountId()== 0 && userDTO.getGoogleAccountId() == 0) {
      String password = userDTO.getPassword();
      String encodePassword = passwordEncoder.encode(password);
      newUser.setPassword(encodePassword);
    }
    return userRepository.save(newUser);
  }

  @Override
  public String login(String phoneNumber, String password, Long RoleId)
   throws Exception {
    Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
    if (optionalUser.isEmpty()) {
      throw new DataNotFoundException("Invalid phone number/password");
    }
//    return optionalUser.get(); //muốn trả về JWT token
    User existingUser = optionalUser.get();
    //check password
    if (existingUser.getFacebookAccountId()== 0 && existingUser.getGoogleAccountId() == 0) {
      if(!passwordEncoder.matches(password,existingUser.getPassword())) {
        throw new BadCredentialsException("Wrong phone number or password");
      }
    }

    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            phoneNumber,password, existingUser.getAuthorities());
    //authenticate with Java Spring security - xác thực vs security
    authenticationManager.authenticate(authenticationToken);
    return jwtTokenUtils.generateToken(existingUser);
  }

  @Override
  public User getUserDetailsFromToken(String token) throws Exception {
    if (jwtTokenUtils.isTokenExpired(token)) {
      throw new Exception("Token is expired");
    }

    String phoneNumber = jwtTokenUtils.extractPhoneNumber(token);
    Optional<User> user = userRepository.findByPhoneNumber(phoneNumber);

    if (user.isPresent()) {
      return user.get();
    } else {
      throw new Exception("User not found");
    }
  }

  @Transactional
  @Override
  public User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
    //Find the existing user by userId
    User existingUser = userRepository.findById(userId).orElseThrow(() ->
      new DataNotFoundException("User not found"));
    String newPhoneNumber = updatedUserDTO.getPhoneNumber();
    if (!existingUser.getPhoneNumber().equals(newPhoneNumber) && userRepository.existsByPhoneNumber(newPhoneNumber)) {
      throw new DataIntegrityViolationException("Phone number already exists");
    }
    // Update user information based on the DTO
    if (updatedUserDTO.getFullName() != null) {
      existingUser.setFullName(updatedUserDTO.getFullName());
    }
    if (newPhoneNumber != null) {
      existingUser.setPhoneNumber(newPhoneNumber);
    }
    if (updatedUserDTO.getAddress() != null) {
      existingUser.setAddress(updatedUserDTO.getAddress());
    }
    if (updatedUserDTO.getDateOfBirth() != null) {
      existingUser.setDateOfBirth(updatedUserDTO.getDateOfBirth());
    }
    if (updatedUserDTO.getFacebookAccountId() > 0) {
      existingUser.setFacebookAccountId(updatedUserDTO.getFacebookAccountId());
    }
    if (updatedUserDTO.getGoogleAccountId() > 0) {
      existingUser.setGoogleAccountId(updatedUserDTO.getGoogleAccountId());
    }
    // Update the password if it is provided in the DTO
    if (updatedUserDTO.getPassword() != null  && !updatedUserDTO.getPassword().isEmpty()) {
      if(!updatedUserDTO.getPassword().equals(updatedUserDTO.getRetypePassword())) {
        throw new DataNotFoundException("Password & Retype Password not the same");
      }
      String newPassword = updatedUserDTO.getPassword();
      String encodedPassword = passwordEncoder.encode(newPassword);
      existingUser.setPassword(encodedPassword);
    }
    return userRepository.save(existingUser);
  }
}
