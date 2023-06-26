package com.sheryians.major.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheryians.major.entities.Category;
import com.sheryians.major.entities.Product;
import com.sheryians.major.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;

	public List<Product> getAll() {
		
		return productRepository.findAll();
	}

	public Product saveProduct(Product product)
	{
		return productRepository.save(product);
	}

	public void deleteProductById(Long id) {
	productRepository.deleteById(id);
		
	}
	public Product getProductById(Long id)
	{
		return productRepository.findById(id).get();
	}
	public List<Product>getProductByCategory(int id)
	{
		return productRepository.findAllProductByCategoryId( id);
	}
	
}
