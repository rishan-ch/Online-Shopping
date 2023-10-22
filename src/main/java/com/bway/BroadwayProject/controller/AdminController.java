package com.bway.BroadwayProject.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.bway.BroadwayProject.dto.ProductDTO;
import com.bway.BroadwayProject.model.Category;
import com.bway.BroadwayProject.model.Product;
import com.bway.BroadwayProject.service.CategoryServiceImpl;
import com.bway.BroadwayProject.service.ProductServiceImpl;



@Controller
public class AdminController {

    @Autowired
    private CategoryServiceImpl catService;

    @Autowired
    private ProductServiceImpl prodService;

    @GetMapping("/admin")
    public String getAdmin(){
        return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String getcategory(Model model, @ModelAttribute Category category){
        
        model.addAttribute("categories", catService.getAllCategory());

        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getaddcategory(Model model){
        //sending an empty object to the view
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String addcategory(@ModelAttribute Category category){

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



    //gets products list
    @GetMapping("/admin/products")
    public String getproducts(Model model){
        model.addAttribute("products", prodService.getAllProduct());
        return "products";
    }

    //gets products adding page
    @GetMapping("/admin/products/add")
    public String getaddproduct(Model model){
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", catService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String addProduct(@ModelAttribute("productDTO") ProductDTO productDTO, @RequestParam("productImage") MultipartFile file, 
             Model model) throws IOException{

    	Product product = new Product();
    	product.setId(productDTO.getId());
    	product.setName(productDTO.getName());
    	product.setCategory(catService.getCategory(productDTO.getCategoryId()).get());
    	product.setPrice(productDTO.getPrice());
    	product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());
        String img;
        
        if(!file.isEmpty()){

            try {
            	String uploadDir = "src/main/resources/static/productImages/";
            	Path destination = Path.of(uploadDir + file.getOriginalFilename());
            	Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            	img = file.getOriginalFilename();
            	product.setImageName(img);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        
        prodService.addProduct(product);


        return "redirect:/admin/products";

    }

    @GetMapping("/admin/product/delete/{id}")
    public String delProduct(@PathVariable Long id){

        prodService.delProduct(id);

        return "redirect:/admin/products";
    }
   
    @GetMapping("/admin/product/update/{id}")
    public String updateProducts(Model model, @PathVariable Long id){
    	Product product = prodService.getProduct(id).get();
    	ProductDTO productDTO = new ProductDTO();
    	productDTO.setId(product.getId());
    	productDTO.setName(product.getName());
    	productDTO.setCategoryId(product.getCategory().getId());
    	productDTO.setDescription(product.getDescription());
    	productDTO.setPrice(product.getPrice());
    	productDTO.setWeight(product.getWeight());
    	productDTO.setImageName(product.getImageName());
    	
    	model.addAttribute("productDTO",productDTO);
    	model.addAttribute("categories", catService.getAllCategory());
    	
    	return "productsAdd";
    	
    	
        
    }
    
}
