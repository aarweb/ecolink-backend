package com.ecolink.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecolink.spring.entity.Product;
import com.ecolink.spring.entity.Startup;
import com.ecolink.spring.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public void save(Product product){
        repository.save(product);
    }

    public Product findByNameAndStartup(String name, Startup startup){
        return repository.findByNameAndStartup(name, startup);
    }

    public Boolean existsByNameAndStartup(String name, Startup startup){
        return repository.existsByNameAndStartup(name, startup);
    }
}
