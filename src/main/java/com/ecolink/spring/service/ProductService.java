package com.ecolink.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    
    public List<Product> findAll(){
        return repository.findAll();
    }

    public List<Product> findTop5ByOrderByCreationDateDesc() {
        return repository.findTop5ByOrderByCreationDateDesc();
    }

    public Page<Product> findByPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }
}
