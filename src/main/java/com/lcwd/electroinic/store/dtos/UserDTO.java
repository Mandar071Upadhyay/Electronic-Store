package com.lcwd.electroinic.store.dtos;

import com.lcwd.electroinic.store.entities.Role;
import com.lcwd.electroinic.store.valid.ImageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private String userId;
    @NotBlank(message = "name should be there !!")
    @Size(min = 3,max = 30,message = "name shold be of min 3 chracter and max 30 chracter !!")
    private String name;

    @NotBlank(message = "email should be there !!")
    private String email;

    @NotBlank(message = "password should be there !!")
    @Size(min=3,max = 20,message = "password shold be of min 3 chracter and max 20 chracter !!")
    private String password;
    @NotBlank(message = "gender should be there !!")
    @Size(min = 1,max = 4,message = "gender shold be of min 1 chracter and max 4 chracter !!")
    private String gender;
    @NotBlank(message = "about should be there !!")
    private String about;
    @ImageNameValid
    private String imageName;
    private Set<RoleDTO> roles=new HashSet<>();
}
