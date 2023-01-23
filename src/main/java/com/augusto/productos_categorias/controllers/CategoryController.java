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
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ProductService productService;

    @RequestMapping("")
    public String showCategory(Model model){
        List <Category> categories = categoryService.list();
        model.addAttribute("categories", categories);
        return"allCategories";

    }

    @GetMapping("/new")
    public String newCategory(@ModelAttribute("category") Category category) {
        return"newCategory";
    }

    //Creando Categoria
    @PostMapping("/new")
    public String createCategory(@Valid @ModelAttribute("category") Category category, BindingResult result ){
        if(result.hasErrors()){
            return"newCategory";
        }else{
            categoryService.save(category);
            return "redirect:/categories/new";
        }
    }

    //Añadir producto a una categoria
    @GetMapping("/{id}")
    public String addProduct(@PathVariable ("id") Long id, @ModelAttribute("category") Category category, Model model) {
        category = categoryService.findById(id);

        List<Product> products = productService.productsWithoutCategories(category);
        model.addAttribute("category", category);
        model.addAttribute("products", products);
        return "addProduct";
    }

    @PostMapping("/{id}")
    public String saveProductToCategory(
        @PathVariable("id") Long id,
        @Valid @ModelAttribute("category") Category category, 
        BindingResult result) {
            if (result.hasErrors()) {
                return "addProduct";
            } else {
                Category categoryInBaseData = categoryService.findById(id);
                List<Product> productsList = categoryInBaseData.getProducts();
                productsList.addAll(category.getProducts());
                categoryInBaseData.setProducts(productsList);
                categoryService.save(categoryInBaseData);
            }
        return "redirect:/categories/"+id;
    }

}
