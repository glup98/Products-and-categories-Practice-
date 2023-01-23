package com.augusto.productos_categorias.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.augusto.productos_categorias.models.Category;
import com.augusto.productos_categorias.models.Product;
import com.augusto.productos_categorias.repositories.ProductRepository;

@Service
public class ProductService extends BaseService <Product> {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        super(productRepository);
        this.productRepository = productRepository;
    }

    public List<Product> productsWithoutCategories(Category category) {
        return productRepository.findByCategoriesNotContains(category);
    }

}
