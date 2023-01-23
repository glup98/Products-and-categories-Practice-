package com.augusto.productos_categorias.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.augusto.productos_categorias.models.Category;
import com.augusto.productos_categorias.models.Product;

@Repository
public interface CategoryRepository extends BaseRepository <Category>{
    List<Category> findByProductsNotContains(Product product);
}
