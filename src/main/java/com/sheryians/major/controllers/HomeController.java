package com.sheryians.major.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.sheryians.major.global.GlobalData;
import com.sheryians.major.services.CategoryService;
import com.sheryians.major.services.ProductService;

@Controller
public class HomeController {
 
	@Autowired
	ProductService productService;
	@Autowired
	CategoryService categoryService;
	
	@GetMapping({"/","/home"})
	String home( Model model)
	{
		model.addAttribute("cartCount", GlobalData.cart.size());

		return "index";
	}
	@GetMapping("/shop")
	String Shop(Model model)
	{
		model.addAttribute("cartCount", GlobalData.cart.size());

		model.addAttribute("categories",  categoryService.getAll());
		model.addAttribute("products", productService.getAll());
		return "shop";
	}
	@GetMapping("/shop/category/{id}")
	String getCategorywiseProducts(@PathVariable int id, Model model)
	{
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("categories", categoryService.getAll());
		model.addAttribute("products", productService.getProductByCategory(id));
		return "shop";
	}
	@GetMapping("/shop/viewproduct/{id}")
	String viewProduct(@PathVariable Long id,Model model)
	{
		model.addAttribute("cartCount", GlobalData.cart.size());
		model.addAttribute("product", productService.getProductById(id));
		return "viewProduct";
	}
}
