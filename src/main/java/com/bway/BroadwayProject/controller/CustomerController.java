package com.bway.BroadwayProject.controller;

import com.bway.BroadwayProject.model.Cart;
import com.bway.BroadwayProject.model.Product;
import com.bway.BroadwayProject.service.CategoryServiceImpl;
import com.bway.BroadwayProject.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CustomerController {

    @Autowired
    private CategoryServiceImpl catService;
    @Autowired
    private ProductServiceImpl prodService;


    @GetMapping("/home")
    public String getHome(Model model){
        model.addAttribute("cartCount",Cart.cart.size());
        return "home";
    }


    //get product lists
    @GetMapping("/shop")
    public String getShop(Model model){
        model.addAttribute("cartCount",Cart.cart.size());
        model.addAttribute("categories",catService.getAllCategory());
        model.addAttribute("products",prodService.getAllProduct());
        return "shop";
    }

    //get products by category
    @GetMapping("/shop/category/{id}")
    public String getProductByCategory(Model model, @PathVariable int id){
        model.addAttribute("categories",catService.getAllCategory());
        model.addAttribute("products",prodService.getAllProductByCategoryId(id));
        return "shop";
    }

    //view products
    @GetMapping("/shop/viewproduct/{id}")
    public String getViewProduct(Model model, @PathVariable Long id){
        model.addAttribute("product",prodService.getProduct(id).get());
        model.addAttribute("cartCount",Cart.cart.size());
        return "viewProduct";
    }

    //add to cart
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable long id){
        Cart.cart.add(prodService.getProduct(id).get());

        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String getCart(Model model){
        model.addAttribute("cartCount",Cart.cart.size());
        model.addAttribute("total", Cart.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", Cart.cart);
        return "cart";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String removeItem(@PathVariable int index, Model model){
        Cart.cart.remove(index);
        model.addAttribute("cart", Cart.cart);
        return "redirect:/cart";
    }

    @GetMapping("/purchase")
    public String purchase(){
        Cart.cart.clear();
        return "redirect:/shop";
    }

    @GetMapping("/logout")
    public String getUserLogout(){
        return "login";
    }

}
