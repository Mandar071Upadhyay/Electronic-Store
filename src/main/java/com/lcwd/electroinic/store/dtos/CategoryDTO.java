package com.lcwd.electroinic.store.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {

    private String categoryId;
    @NotBlank(message = "title should not be blank !!")
    @Size(min = 4,message = "title length min be of 4 character !!")

    private String title;
    @NotBlank(message = "description should not be blank !!")
    private String description;
    private String coverImage;
}
