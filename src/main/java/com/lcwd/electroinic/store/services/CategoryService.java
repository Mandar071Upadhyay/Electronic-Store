package com.lcwd.electroinic.store.services;

import com.lcwd.electroinic.store.dtos.CategoryDTO;
import com.lcwd.electroinic.store.dtos.PageableResponse;

import java.util.List;

public interface CategoryService {
    public CategoryDTO create(CategoryDTO categoryDTO);
    public CategoryDTO update(CategoryDTO categoryDTO,String categoryId);
    public void delete(String categoryId);
    public PageableResponse<CategoryDTO> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);
    public CategoryDTO getById(String CategoryId);

}
