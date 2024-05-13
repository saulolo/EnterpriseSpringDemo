package edu.enterprise.spring.controllers;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//[62].RestControllers de ProductosJson
@RestController
@RequestMapping("/products")
public class ProductoJsonController {


	/* VER PRODUCTOS JSON */
	@GetMapping(value = "/productos-json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Resource> getProductosJson() {
		Resource resource = new ClassPathResource("/productos.json");
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(resource);
	}

}

