package com.lcwd.electroinic.store.services;

import com.lcwd.electroinic.store.dtos.PageableResponse;
import com.lcwd.electroinic.store.dtos.ProductDTO;

public interface ProductService {
    public ProductDTO create(ProductDTO productDTO);
    public ProductDTO update(ProductDTO productDTO,String productId);
    public void delete(String productId);
    public PageableResponse<ProductDTO> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);
    public PageableResponse<ProductDTO> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir);

    public PageableResponse<ProductDTO> searchByTitle(String keyword,int pageNumber,int pageSize,String sortBy,String sortDir);
public ProductDTO getById(String productId);

public ProductDTO createWithCategory(String categoryId,ProductDTO productDTO);

public ProductDTO updateCartegory(String productId,String categoryId);

public PageableResponse<ProductDTO> getAllCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);
}
