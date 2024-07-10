package com.project.shopapp.services;

import com.project.shopapp.components.JwtTokenUtil;
import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exception.DataNotFoundException;
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

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService{
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenUtil jwtTokenUtil;
  private final AuthenticationManager authenticationManager;
  @Override
  public User createUser(UserDTO userDTO) throws DataNotFoundException {
    //register user
    String phoneNumber = userDTO.getPhoneNumber();
    //check xem username hay phone đã tồn tại chưa
    if (userRepository.existsByPhoneNumber(phoneNumber)) {
      throw new DataIntegrityViolationException("Phone number already exists");
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

    Role role = roleRepository.findById(userDTO.getRoleId())
            .orElseThrow(()-> new DataNotFoundException("Role not found"));
    //KTra nếu có accountId, không yêu cầu pass
    if (userDTO.getFacebookAccountId()== 0 && userDTO.getGoogleAccountId() == 0) {
      String password = userDTO.getPassword();
      String encodePassword = passwordEncoder.encode(password);
      newUser.setPassword(encodePassword);
    }
    return userRepository.save(newUser);
  }

  @Override
  public String login(String phoneNumber, String password)
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
    return jwtTokenUtil.generateToken(existingUser);
  }
}
