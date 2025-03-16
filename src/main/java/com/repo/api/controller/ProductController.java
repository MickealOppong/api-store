package com.repo.api.controller;

import com.repo.api.dto.ResponseDto;
import com.repo.api.model.product.Product;
import com.repo.api.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/product")
    public ResponseDto<Object> addProduct(MultipartFile[] photos, Product product){
        return productService.saveWithImage(product,photos);
    }


}
