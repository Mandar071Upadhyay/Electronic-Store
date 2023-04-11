package com.lcwd.electroinic.store.services.impl;

import com.lcwd.electroinic.store.dtos.Helper;
import com.lcwd.electroinic.store.dtos.PageableResponse;
import com.lcwd.electroinic.store.dtos.ProductDTO;
import com.lcwd.electroinic.store.dtos.UserDTO;
import com.lcwd.electroinic.store.entities.Category;
import com.lcwd.electroinic.store.entities.Product;
import com.lcwd.electroinic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electroinic.store.repositories.CategoryRepository;
import com.lcwd.electroinic.store.repositories.ProductRepository;
import com.lcwd.electroinic.store.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDTO create(ProductDTO productDTO) {
        String productId= UUID.randomUUID().toString();
        productDTO.setProductId(productId);
        productDTO.setAddedDate(new Date());
        Product product = this.modelMapper.map(productDTO, Product.class);
        Product save = this.productRepository.save(product);
        return this.modelMapper.map(save,ProductDTO.class);

    }

    @Override
    public ProductDTO update(ProductDTO productDTO, String productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> {
            return new ResourceNotFoundException("Invalid Id " + productId + " !!");
        });
        product.setTitle(productDTO.getTitle());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setDiscountedPrice(productDTO.getDiscountedPrice());
        product.setQuantity(productDTO.getQuantity());
       product.setLive(productDTO.getLive());
product.setStock(productDTO.getStock());
product.setProductImageName(productDTO.getProductImageName());
        Product save = this.productRepository.save(product);
        return this.modelMapper.map(save,ProductDTO.class);
    }

    @Override
    public void delete(String productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> {
            return new ResourceNotFoundException("Invalid Id : " + productId);
        });
        this.productRepository.delete(product);
    }

    @Override
    public PageableResponse<ProductDTO> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = this.productRepository.findAll(pageable);
        List<Product> entityList = page.getContent();
        PageableResponse<ProductDTO> response=new PageableResponse<>();
        List<ProductDTO> dtoList = entityList.stream().map((obj) -> {
            return this.modelMapper.map(obj, ProductDTO.class);
        }).collect(Collectors.toList());
        response.setContent(dtoList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElement(page.getTotalElements());
        response.setLastPage(page.isLast());

        return response;
    }

    @Override
    public PageableResponse<ProductDTO> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = this.productRepository.findByLiveTrue(pageable);
        List<Product> content = page.getContent();
        List<ProductDTO> productDTOS = content.stream().map((obj) -> {
            return this.modelMapper.map(obj, ProductDTO.class);
        }).collect(Collectors.toList());
        PageableResponse<ProductDTO> response=new PageableResponse<>();

        response.setContent(productDTOS);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setTotalElement(page.getTotalElements());
        response.setLastPage(page.isLast());

        return response;


    }

    @Override
    public PageableResponse<ProductDTO> searchByTitle(String keyword,int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort=sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = this.productRepository.findByTitleContaining(keyword,pageable);
        List<Product> content = page.getContent();
        PageableResponse<ProductDTO> response = Helper.getPageableResponse(page, ProductDTO.class);
        return response;

    }

    @Override
    public ProductDTO getById(String productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Invalid Product Id : " + productId);
        });

        return this.modelMapper.map(product,ProductDTO.class);
    }

    @Override
    public ProductDTO createWithCategory(String categoryId, ProductDTO productDTO) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> {
            return new ResourceNotFoundException("Invalid Category Id : " + categoryId);
        });
        String productId=UUID.randomUUID().toString();
        Product product = this.modelMapper.map(productDTO, Product.class);
        product.setAddedDate(new Date());
        product.setProductId(productId);
        product.setCategory(category);
        Product save = this.productRepository.save(product);
        return this.modelMapper.map(save,ProductDTO.class);
    }

    @Override
    public ProductDTO updateCartegory(String productId, String categoryId) {

        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Invalid Category Id : " + categoryId);
        });
        Product product = this.productRepository.findById(productId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Invalid Product Id : " + productId);
        });
        product.setCategory(category);
        Product save = this.productRepository.save(product);
        return this.modelMapper.map(save,ProductDTO.class);
    }

    @Override
    public PageableResponse<ProductDTO> getAllCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> {
            throw new ResourceNotFoundException("Invalid Category Id : " + categoryId);
        });
        Sort sort=sortDir.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();

        Pageable pageable=PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = this.productRepository.findByCategory(category, pageable);
        return Helper.getPageableResponse(page,ProductDTO.class);

    }
}
