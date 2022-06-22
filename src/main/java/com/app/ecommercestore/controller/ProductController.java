package com.app.ecommercestore.controller;

import com.app.ecommercestore.model.Product;
import com.app.ecommercestore.service.CartService;
import com.app.ecommercestore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductService productService;


	@GetMapping("/new")
	public String getProductUploadForm(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "add-product";
	}

	@PostMapping("/save")
	public String saveUser(Product product)  {
		productService.saveProduct(product);
		return "redirect:/admin/dashboard";
	}

	@GetMapping("/getById/{id}")
	public String getProductById(Model model, @PathVariable(name = "id") Long productId) {
		model.addAttribute("product", productService.getProductById(productId));
		return "view-product";
	}

	@GetMapping("/deleteById/{id}")
	public String deleteProductById(@PathVariable(name = "id") Long productId) {
		productService.deleteProduct(productId);
		return "redirect:/admin/dashboard";
	}

	@GetMapping("/update/{id}")
	public String updateProduct(Model model, @PathVariable(name = "id") Long productId) {
		model.addAttribute("productId", productId);
		model.addAttribute("product", productService.getProductById(productId));
		return "update-product";
	}

	@PostMapping("/update/{id}")
	public String updateProduct(@PathVariable(name = "id") Long productId, Product product) {
		product.setId(productId);
		productService.saveProduct(product);
		return "redirect:/admin/dashboard";
	}
}
