package com.project.shopapp.services;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService{
  private UserRepository userRepository;
  private RoleRepository roleRepository;
  @Override
  public User createUser(UserDTO userDTO) throws DataNotFoundException {
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
                                  .build();
    Role role = roleRepository.findById(userDTO.getRoleId())
      .orElseThrow(()-> new DataNotFoundException("Role Not Found"));
    newUser.setRole(role);

    //KTra nếu có accountId, không yêu cầu pass
    if (userDTO.getFacebookAccountId()== 0 && userDTO.getGoogleAccountId() == 0) {
      String password = userDTO.getPassword();
      //String encodePassword = passwordEncoder.endcode(password);
      //newUser.setPassword(encodedPassword);
    }
    return userRepository.save(newUser);
  }

  @Override
  public String login(String phoneNumber, String password) {
    return null;
  }
}
