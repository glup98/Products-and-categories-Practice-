package com.augusto.productos_categorias.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.augusto.productos_categorias.models.Category;
import com.augusto.productos_categorias.models.Product;

@Repository
public interface ProductRepository extends BaseRepository <Product>{
    List<Product> findByCategoriesNotContains (Category category);
}
