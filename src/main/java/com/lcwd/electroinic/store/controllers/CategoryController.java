package com.lcwd.electroinic.store.controllers;

import com.lcwd.electroinic.store.dtos.*;
import com.lcwd.electroinic.store.services.CategoryService;
import com.lcwd.electroinic.store.services.FileService;
import com.lcwd.electroinic.store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String uploadFilePath;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO categoryDTO)
    {
        CategoryDTO categoryDTO1 = this.categoryService.create(categoryDTO);
       return new ResponseEntity<>(categoryDTO1, HttpStatus.CREATED);
    }

@PutMapping("/{categoryId}")
@PreAuthorize("hasRole('ADMIN')")

public ResponseEntity<CategoryDTO> update(@Valid @RequestBody CategoryDTO categoryDTO,@PathVariable String categoryId)
{
    CategoryDTO update = this.categoryService.update(categoryDTO, categoryId);
    return new ResponseEntity<>(update,HttpStatus.OK);
}
@DeleteMapping("/{categoryId}")
@PreAuthorize("hasRole('ADMIN')")

public ResponseEntity<ApiResponseMessage> delete(@PathVariable String categoryId)
{
    this.categoryService.delete(categoryId);
    ApiResponseMessage apiResponseMessage=ApiResponseMessage.builder().message("Category Deleted : "+categoryId).success(true).httpStatus(HttpStatus.OK).build();
    return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
}
@GetMapping
    public ResponseEntity<PageableResponse> getAll(@RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,@RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,@RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,@RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
{

    return new ResponseEntity<>(this.categoryService.getAll(pageNumber,pageSize,sortBy,sortDir),HttpStatus.OK);
}
@GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable String categoryId)
{
    return new ResponseEntity<>(this.categoryService.getById(categoryId),HttpStatus.OK);
}
  @PostMapping("/image/{categoryId}")
  @PreAuthorize("hasRole('ADMIN')")

  public ResponseEntity<ImageResponse> uploadCategoryImage(@RequestParam("categoryImage")MultipartFile file,@PathVariable String categoryId) throws IOException {
      CategoryDTO categoryDTO = this.categoryService.getById(categoryId);
      String imageName = this.fileService.uploadFile(file, uploadFilePath);
      categoryDTO.setCoverImage(imageName);
      this.categoryService.update(categoryDTO,categoryId);
      ImageResponse imageResponse=ImageResponse.builder().fileName(imageName).message("Image Uploaded !! ").success(true).httpStatus(HttpStatus.OK).build();
      return new ResponseEntity<>(imageResponse,HttpStatus.OK);
  }
  @GetMapping("/image/{categoryId}")
    public void servecategoryImage(@PathVariable String categoryId, HttpServletResponse response) throws IOException {
      CategoryDTO categoryDTO = this.categoryService.getById(categoryId);
      InputStream resourse = this.fileService.getResourse(uploadFilePath, categoryDTO.getCoverImage());
  response.setContentType(MediaType.IMAGE_JPEG_VALUE);
      StreamUtils.copy(resourse,response.getOutputStream());
    }
    @PostMapping("/{categoryId}/productes")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<ProductDTO> createProductWithCategory(@PathVariable String categoryId,@RequestBody ProductDTO productDTO)
    {
        ProductDTO productDTO1 = this.productService.createWithCategory(categoryId, productDTO);
    return new ResponseEntity<>(productDTO1,HttpStatus.OK);
    }
    @PostMapping("/{categoryId}/productes/{productId}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<ProductDTO> updateCategoryOfProduct(@PathVariable String categoryId,@PathVariable String productId)
    {
        ProductDTO productDTO = this.productService.updateCartegory(productId,categoryId);
        return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }

    @GetMapping("/{categoryId}/productes")
    public ResponseEntity<PageableResponse<ProductDTO>> getProductsOfCategory(@PathVariable String categoryId,@RequestParam(value="pageNumber",defaultValue = "0",required = false) int pageNumber,@RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,@RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,@RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
    {
        PageableResponse<ProductDTO> response = this.productService.getAllCategory(categoryId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
