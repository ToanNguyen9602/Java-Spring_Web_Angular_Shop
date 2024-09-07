package com.project.shopapp.services;

import com.project.shopapp.dtos.UpdateUserDTO;
import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.models.*;

public interface IUserService {
  User createUser(UserDTO userDTO) throws Exception;
  String login(String phoneNumber, String password, Long RoleId) throws Exception;
  User getUserDetailsFromToken(String token) throws Exception;
  User updateUser(Long userId, UpdateUserDTO updatedUserDTO ) throws Exception;
}
