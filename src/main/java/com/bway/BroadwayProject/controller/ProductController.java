package com.bway.BroadwayProject.controller;

import com.bway.BroadwayProject.dto.ProductDTO;
import com.bway.BroadwayProject.model.Product;
import com.bway.BroadwayProject.service.CategoryServiceImpl;
import com.bway.BroadwayProject.service.ProductServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Controller
public class ProductController {
    @Autowired
    private CategoryServiceImpl catService;

    @Autowired
    private ProductServiceImpl prodService;

    //gets products list
    @GetMapping("/admin/products")
    public String getProducts(Model model, HttpSession session){
        if(session.getAttribute("validAdmin")==null){
            return "adminLogin";
        }
        model.addAttribute("products", prodService.getAllProduct());
        return "products";
    }

    //gets products adding page
    @GetMapping("/admin/products/add")
    public String getAddProduct(Model model, HttpSession session){
        if(session.getAttribute("validAdmin")==null){
            return "adminLogin";
        }
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", catService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String addProduct(@ModelAttribute("productDTO") ProductDTO productDTO, @RequestParam("productImage") MultipartFile file,
                             Model model) throws IOException {

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
    public String delProduct(@PathVariable Long id, HttpSession session){
        if(session.getAttribute("validAdmin")==null){
            return "adminLogin";
        }

        prodService.delProduct(id);

        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProducts(Model model, @PathVariable Long id, HttpSession session){
        if(session.getAttribute("validAdmin")==null){
            return "adminLogin";
        }
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
