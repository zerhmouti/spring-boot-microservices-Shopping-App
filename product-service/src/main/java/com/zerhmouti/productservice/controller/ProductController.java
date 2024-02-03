package com.zerhmouti.productservice.controller;

import com.zerhmouti.productservice.dto.ProductRequest;
import com.zerhmouti.productservice.dto.ProductResponse;
import com.zerhmouti.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void CreateProduct(
            @RequestBody ProductRequest productRequest
    ){
        productService.CreateProduct(productRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }
}
