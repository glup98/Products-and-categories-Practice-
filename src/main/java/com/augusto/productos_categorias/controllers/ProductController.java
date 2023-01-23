package com.augusto.productos_categorias.controllers;


import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.augusto.productos_categorias.models.Category;
import com.augusto.productos_categorias.models.Product;
import com.augusto.productos_categorias.services.CategoryService;
import com.augusto.productos_categorias.services.ProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    //Listar productos
    @RequestMapping("")
    public String listProduct(Model model) {
        List <Product> products = productService.list();
        model.addAttribute("products", products);
        return "allProducts";
    }

    @GetMapping("/new")
    public String newProduct(@ModelAttribute("product") Product product) {
        return"newProduct";
    }

    //creando producto
    @PostMapping("/new")
    public String createProduct(@Valid @ModelAttribute("product") Product product, BindingResult result ) {
        if(result.hasErrors()){
            return"newProduct";
        }else{
            productService.save(product);
            return "redirect:/products/new";
        }
    }

//Añadir categoria a un producto 
    @GetMapping("/{id}")
    public String addCategory(@PathVariable("id") Long id, @ModelAttribute("product") Product product, Model model) {
        product = productService.findById(id);
        
        List <Category> categories = categoryService.categoriesWithoutproducts(product);
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);
        return"addCategory";
    }
    

    @PostMapping("/{id}")
    public String saveCategoryToProduct(
        @PathVariable("id") Long id,
        @Valid @ModelAttribute("product") Product product, 
        BindingResult result) {
            if (result.hasErrors()) {
                return "addCategory";
            } else {
                Product productInBaseData = productService.findById(id);
                List <Category> categoriesList = productInBaseData.getCategories();
                categoriesList.addAll(product.getCategories());
                productInBaseData.setCategories(categoriesList);
                productService.save(productInBaseData);
            }
        return "redirect:/products/" + id;
    }
}
