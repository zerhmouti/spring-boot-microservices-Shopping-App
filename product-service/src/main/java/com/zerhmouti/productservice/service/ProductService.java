package com.zerhmouti.productservice.service;

import com.zerhmouti.productservice.dto.ProductRequest;
import com.zerhmouti.productservice.dto.ProductResponse;
import com.zerhmouti.productservice.model.Product;
import com.zerhmouti.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    public void CreateProduct(ProductRequest productRequest){
        Product product= Product.builder()
                .description(productRequest.getDescription())
                .name(productRequest.getName())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved",product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products=productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .description(product.getDescription())
                .name(product.getName())
                .id(product.getId())
                .price(product.getPrice())
                .build();
    }
}
