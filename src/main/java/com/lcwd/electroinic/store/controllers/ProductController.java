package com.lcwd.electroinic.store.controllers;

import com.lcwd.electroinic.store.dtos.ApiResponseMessage;
import com.lcwd.electroinic.store.dtos.ImageResponse;
import com.lcwd.electroinic.store.dtos.PageableResponse;
import com.lcwd.electroinic.store.dtos.ProductDTO;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
private ProductService productService;
    @Autowired
    private FileService fileService;
    @Value("${product.profile.image.path}")
private String imageUploadPath;
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO)
    {
        ProductDTO productDTO1 = this.productService.create(productDTO);
    return new ResponseEntity<>(productDTO1, HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productDTO,@PathVariable String productId)
    {
        ProductDTO update = this.productService.update(productDTO, productId);
    return new ResponseEntity<>(update,HttpStatus.OK);
    }
    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<ApiResponseMessage> delete(@PathVariable String productId)
    {
        this.productService.delete(productId);
        ApiResponseMessage apiResponseMessage=ApiResponseMessage.builder().message("delete success fully").success(true).httpStatus(HttpStatus.OK).build();
        return new ResponseEntity<>(apiResponseMessage,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDTO>> getAll(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,@RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,@RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,@RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
    {
        PageableResponse<ProductDTO> pageableResponse = this.productService.getAll(pageNumber, pageSize, sortBy, sortDir);
    return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }

    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDTO>> getAllLive(@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,@RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,@RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,@RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
    {
        PageableResponse<ProductDTO> pageableResponse = this.productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }
    @GetMapping("/search/{keyword}")
    public ResponseEntity<PageableResponse<ProductDTO>> searchByTitle(@PathVariable String keyword,@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,@RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,@RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,@RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
    {
        PageableResponse<ProductDTO> pageableResponse = this.productService.searchByTitle(keyword, pageNumber, pageSize, sortBy, sortDir);
    return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }
@GetMapping("/{productId}")
public ResponseEntity<ProductDTO> getById(@PathVariable String productId)
    {
        ProductDTO productDTO = this.productService.getById(productId);
    return new ResponseEntity<>(productDTO,HttpStatus.OK);
    }
    @PostMapping("/image/{productId}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<ImageResponse> uploadProductImage(@RequestParam("productImage")MultipartFile file,@PathVariable String productId) throws IOException {
        ProductDTO productDTO = this.productService.getById(productId);
            String fileName = this.fileService.uploadFile(file, imageUploadPath);
productDTO.setProductImageName(fileName);
    this.productService.update(productDTO, productId);
    ImageResponse imageResponse=ImageResponse.builder().message("image uploaded : Id "+fileName).fileName(fileName).success(true).httpStatus(HttpStatus.OK).build();
    return new ResponseEntity<>(imageResponse,HttpStatus.OK);
    }
    @GetMapping("/image/{productId}")
    public void serveProductImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        ProductDTO productDTO = this.productService.getById(productId);
        InputStream resourse = this.fileService.getResourse(imageUploadPath, productDTO.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resourse,response.getOutputStream());
    }
}
