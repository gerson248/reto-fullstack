package com.example.backend.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.entity.Product;
import com.example.backend.services.IProductService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/productos")
public class ProductRestController {
	
	@Autowired
	private IProductService productService;
	
	@GetMapping("")
	public List<Product> listar(){
		return productService.findAll();
	}
	
	@GetMapping("/{id}")
	public Product detalle(@PathVariable Long id){
		
		Product product = productService.findById(id);
		
		return product;
	}
	
	@PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Product product, BindingResult result) {

        Product productNew = null;
        Map<String, Object> response = new HashMap<>();

        try {
        	product.setCreatedAt(new Date());
        	productNew = productService.save(product);
        } catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar el insert en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje", "El producto ha sido creado con éxito!");
        response.put("Producto", productNew);
        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
    }
	
	@PutMapping("/update/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Product editar(@RequestBody Product product, @PathVariable Long id) {
		Product productDb = productService.findById(id);

		productDb.setNombre(product.getNombre());
		productDb.setMarca(product.getMarca());
		productDb.setPrecio(product.getPrecio());

		return productService.save(productDb);
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void eliminar(@PathVariable Long id) {
		productService.deleteById(id);
	}
}
