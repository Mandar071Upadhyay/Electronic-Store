package com.lcwd.electroinic.store.controllers;

import com.lcwd.electroinic.store.dtos.ApiResponseMessage;
import com.lcwd.electroinic.store.dtos.ImageResponse;
import com.lcwd.electroinic.store.dtos.PageableResponse;
import com.lcwd.electroinic.store.dtos.UserDTO;
import com.lcwd.electroinic.store.services.FileService;
import com.lcwd.electroinic.store.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api(value = "UserController",description = "REST APIs related to perform user operation !!")
public class UserContoller {
    private Logger logger= LoggerFactory.getLogger(UserContoller.class);
    @Value("${user.profile.image.path}")
    private String uploadImagePath;

    @Autowired
   private FileService fileService;
    @Autowired
    private UserService userService;
    @PostMapping
    @ApiOperation(value = "create new user !!")
    @ApiResponses(value = {
                @ApiResponse(code = 200,message = "Success | Ok"),
            @ApiResponse(code=401,message = "not authorized !!"),
            @ApiResponse(code=201,message = "new user created !!")

    })
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO)
    {
        return new ResponseEntity<>(this.userService.createUser(userDTO), HttpStatus.CREATED);
    }
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable("userId") String userId)
    {
        return new ResponseEntity<>(this.userService.updateUser(userDTO,userId),HttpStatus.OK);
    }
@DeleteMapping("/{userId}")
public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("userId") String userId)
{
    this.userService.deleteUser(userId);
    ApiResponseMessage apiResponseMessage = ApiResponseMessage.builder().message("User deleted").success(true).httpStatus(HttpStatus.OK).build();
    return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
}
@GetMapping
@ApiOperation(value = "get all users",response = ResponseEntity.class,tags = {"user-contoller"})
public ResponseEntity<PageableResponse> getAllUser(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber, @RequestParam(value="pageSize",defaultValue = "10",required = false) int pageSize, @RequestParam(value = "sortBy",defaultValue = "name",required = false) String sortBy, @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir)
{
  return    new ResponseEntity<>(this.userService.getAllUser(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
}
@GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("userId") String userId)
{
    return new ResponseEntity<>(this.userService.getUserById(userId),HttpStatus.OK);
}
@GetMapping("/email/{userEmail}")

public ResponseEntity<UserDTO> getUserByEmail(@PathVariable("userEmail") String userEmail)
{
return new ResponseEntity<>(this.userService.getUserByEmail(userEmail),HttpStatus.OK);
}
@GetMapping("/search/{keywords}")
public ResponseEntity<List<UserDTO>> searchUser(@PathVariable("keywords") String keywords)
{

    return new ResponseEntity<>(this.userService.userSearch(keywords),HttpStatus.OK);
}

@PostMapping("/image/{userId}")
public ResponseEntity<ImageResponse> uploadFile(@RequestParam("userImage")MultipartFile file,@PathVariable String userId) throws IOException {
    UserDTO userDTO = this.userService.getUserById(userId);
    String fileName = this.fileService.uploadFile(file, uploadImagePath);
userDTO.setImageName(fileName);
    UserDTO userDTO1 = this.userService.updateUser(userDTO,userId);
    ImageResponse imageResponse=ImageResponse.builder().fileName(fileName).success(true).message("File Created").success(true).httpStatus(HttpStatus.CREATED).build();
    return new ResponseEntity<>(imageResponse,HttpStatus.OK);

    }
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDTO user = this.userService.getUserById(userId);
        InputStream resourse = this.fileService.getResourse(uploadImagePath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resourse,response.getOutputStream());

    }


}
