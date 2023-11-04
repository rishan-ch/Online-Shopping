package com.bway.BroadwayProject.controller;

import com.bway.BroadwayProject.model.Category;
import com.bway.BroadwayProject.service.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class CategoryController {
    @Autowired
    private CategoryServiceImpl catService;
    @GetMapping("/admin/categories")
    public String getCategory(Model model, @ModelAttribute Category category){

        model.addAttribute("categories", catService.getAllCategory());

        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getAddCategory(Model model){
        //sending an empty object to the view
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String addCategory(@ModelAttribute Category category){

        catService.addCategory(category);

        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String delCategory(@PathVariable int id){

        catService.deleteCategory(id);

        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategory(Model model, @PathVariable int id){

        Optional<Category> category =  catService.getCategory(id);

        if(category.isPresent()){

            model.addAttribute("category", category.get());
            return "categoriesAdd";
        }
        return "404";

    }
}
