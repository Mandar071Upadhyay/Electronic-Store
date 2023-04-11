package com.lcwd.electroinic.store.services.impl;

import com.lcwd.electroinic.store.dtos.CartDTO;
import com.lcwd.electroinic.store.dtos.CategoryDTO;
import com.lcwd.electroinic.store.dtos.PageableResponse;
import com.lcwd.electroinic.store.entities.Category;
import com.lcwd.electroinic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electroinic.store.repositories.CategoryRepository;
import com.lcwd.electroinic.store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Value("${user.profile.image.path}")
    private String uploadImagePath;
    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
String categoryId= UUID.randomUUID().toString();
categoryDTO.setCategoryId(categoryId);
        Category category = this.modelMapper.map(categoryDTO, Category.class);
        Category save = this.categoryRepository.save(category);
return modelMapper.map(save,CategoryDTO.class);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO,String categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new ResourceNotFoundException("category Id not valid in update !!");
        });
        category.setTitle(categoryDTO.getTitle());
        category.setDescription(categoryDTO.getDescription());
        category.setCoverImage(categoryDTO.getCoverImage());
        Category save = this.categoryRepository.save(category);
        return this.modelMapper.map(save, CategoryDTO.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new ResourceNotFoundException("category Id not valid in delete !!");
        });
        Path path= Paths.get(uploadImagePath+category.getCoverImage());
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDTO> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = this.categoryRepository.findAll(pageable);
        List<Category> content = page.getContent();

        List<CategoryDTO> categoryDTOList = content.stream().map((object) -> {
            return this.modelMapper.map(object, CategoryDTO.class);
        }).collect(Collectors.toList());
        PageableResponse<CategoryDTO> response=new PageableResponse<>();
        response.setContent(categoryDTOList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElement(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }

    @Override
    public CategoryDTO getById(String CategoryId) {
        Category category = this.categoryRepository.findById(CategoryId).orElseThrow(() -> {
            throw new ResourceNotFoundException("category Id not valid in getById !!");
        });
        return modelMapper.map(category,CategoryDTO.class);
    }


}
