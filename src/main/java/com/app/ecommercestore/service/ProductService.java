package com.app.ecommercestore.service;

import com.app.ecommercestore.model.Product;
import com.app.ecommercestore.repository.ProductRepository;
import com.app.ecommercestore.util.FileUploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    
    public void saveProduct(Product product) throws IOException {
       productRepository.save(product);

    }

    
    public List<Product> getAllProducts() {
        return productRepository.getAllProducts();
    }

    
    public List<Product> getAllUnDeletedProducts() {
        return productRepository.getAllUnDeletedProducts();
    }

    
    public Product getProductById(Long productId) {
        return productRepository.getById(productId);
    }

    
    public void deleteProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            product.get().setDeleteStatus(true);
            productRepository.save(product.get());
        }
    }
}
