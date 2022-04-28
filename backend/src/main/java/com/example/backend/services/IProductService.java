package com.example.backend.services;

import java.util.List;

import com.example.backend.entity.Product;

public interface IProductService {
	
	public List<Product> findAll();
	
	public Product findById(Long id);
	
	public Product save(Product product);
	
	public void deleteById(Long id);

}
