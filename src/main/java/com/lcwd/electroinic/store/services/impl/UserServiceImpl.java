package com.lcwd.electroinic.store.services.impl;

import com.lcwd.electroinic.store.dtos.PageableResponse;
import com.lcwd.electroinic.store.dtos.UserDTO;
import com.lcwd.electroinic.store.entities.Role;
import com.lcwd.electroinic.store.entities.User;
import com.lcwd.electroinic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electroinic.store.repositories.RoleRepository;
import com.lcwd.electroinic.store.repositories.UserRepository;
import com.lcwd.electroinic.store.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${user.profile.image.path}")
    private String imagePath;
    @Value("${normal.role.id}")

    private String normalRoleId;
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public UserDTO createUser(UserDTO userDTO) {
        String userId= UUID.randomUUID().toString();
        userDTO.setUserId(userId);
        userDTO.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        User user = dtoToEntity(userDTO);

        Role role = roleRepository.findById(this.normalRoleId).get();
        user.getRoles().add(role);
        User save = this.userRepository.save(user);
        return userDTO;
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO,String userId) {

User user=this.userRepository.findById(userId).orElseThrow(()->{
    throw new ResourceNotFoundException("User Not Found In update User");
});
        user.setName(userDTO.getName());
        user.setPassword(userDTO.getPassword());
        user.setGender(userDTO.getGender());
        user.setAbout(userDTO.getAbout());
        user.setImageName(userDTO.getImageName());
        this.userRepository.save(user);
        userDTO.setUserId(user.getUserId());
        return userDTO;
    }

    @Override
    public void deleteUser(String userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> {
            throw new ResourceNotFoundException("User not found in delete user");
        });

        String imageName=user.getImageName();
        String fullPath=this.imagePath+imageName;
        Path path= Paths.get(fullPath);
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.userRepository.delete(user);

    }

    @Override
    public PageableResponse getAllUser(int pageNumber,int pageSize,String sortBy,String sortDir) {

        Sort sort;
if(sortDir.equalsIgnoreCase("desc")) {
    sort = Sort.by(sortBy).descending();

}else {
    sort = Sort.by(sortBy).ascending();

}
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<User> page = this.userRepository.findAll(pageable);
        List<User> users = page.getContent();

        List<UserDTO> usersDto = users.stream().map((user) -> {
            return entityToDto(user);
        }).collect(Collectors.toList());

        PageableResponse<UserDTO> response=new PageableResponse<>();
        response.setContent(usersDto);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElement(page.getTotalElements());
        response.setLastPage(page.isLast());

return response;
    }

    @Override
    public UserDTO getUserById(String userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> {
            throw new ResourceNotFoundException("User not found in getByUserId");
        });
        UserDTO userDTO = entityToDto(user);
        return userDTO;
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            throw new ResourceNotFoundException("User not found getUserByEmail");
        });
        UserDTO userDTO = entityToDto(user);
        return userDTO;
    }

    @Override
    public List<UserDTO> userSearch(String keywords) {
        List<User> users = this.userRepository.findByNameContaining(keywords);

        List<UserDTO> usersDTO = users.stream().map((user) -> {
            return entityToDto(user);
        }).collect(Collectors.toList());
        return usersDTO;
    }

    @Override
    public Optional<User> findUserByEmailOptional(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User dtoToEntity(UserDTO userDTO)
{
    User user = this.modelMapper.map(userDTO, User.class);
return user;
}
public UserDTO entityToDto(User user)
{
    UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
    return userDTO;
}

}
