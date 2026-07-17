package com.guna.expensight.service;

import com.guna.expensight.dto.UserDTO;
import com.guna.expensight.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User createUser(UserDTO userDTO);
    List<User> getAllUsers();
    Page<User> getAllUsers(Pageable pageable);
    User getUserById(Long id);
    User updateUser(Long id, UserDTO userDTO);
    void deleteUser(Long id);
}
