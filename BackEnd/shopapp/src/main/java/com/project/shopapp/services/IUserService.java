package com.project.shopapp.services;

import com.project.shopapp.dtos.UserDTO;
import com.project.shopapp.exception.DataNotFoundException;
import com.project.shopapp.models.*;

public interface IUserService {
  User createUser(UserDTO userDTO) throws DataNotFoundException;
  String login(String phoneNumber, String password);

}
