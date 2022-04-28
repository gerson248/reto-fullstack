package com.example.backend.dao;

import com.example.backend.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface IProductDao extends CrudRepository<Product, Long>{

}
