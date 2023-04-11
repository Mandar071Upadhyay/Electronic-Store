package com.lcwd.electroinic.store.services;

import com.lcwd.electroinic.store.dtos.PageableResponse;
import com.lcwd.electroinic.store.dtos.UserDTO;
import com.lcwd.electroinic.store.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface UserService {
public UserDTO createUser(UserDTO userDTO);
public UserDTO updateUser(UserDTO userDTO,String userId);
public void deleteUser(String userId);
public PageableResponse getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);
public UserDTO getUserById(String userId);
public UserDTO getUserByEmail(String email);
public List<UserDTO> userSearch(String keywords);

public Optional<User> findUserByEmailOptional(String email);

}
