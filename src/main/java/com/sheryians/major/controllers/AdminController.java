package com.sheryians.major.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sheryians.major.dto.ProductDTO;
import com.sheryians.major.entities.Category;
import com.sheryians.major.entities.Product;
import com.sheryians.major.services.CategoryService;
import com.sheryians.major.services.ProductService;

@Controller
public class AdminController {
 
	@Autowired
	CategoryService categoryService;
	@Autowired
	ProductService productService;
	
	public static String upLoadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
	
	@GetMapping("/admin")
	public String adminHome()
	{
	   return "adminHome";
	}
	
	@GetMapping("/admin/categories")
	public String getCategory(Model model)
	{
		model.addAttribute("categories", categoryService.getAll());
		return "categories";
	}
	
	@GetMapping("/admin/categories/add")
	public String addCategory(Model model)
	{
		model.addAttribute("category", new Category());
		return "categoriesAdd";
	}
	//Here @modelAttribute does same thing as model.addAttribute
	@PostMapping("/admin/categories/add")
	public String addCategoryPost(@ModelAttribute("category") Category category)
	{
		categoryService.saveCategory(category);
		return "redirect:/admin/categories";
	}
	//We cant use DeleteMapping here as not allowed in case of forms, only post and get are allowed
	//We use getMapping here as to delete something you first need to fetch it from the database
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCategory(@PathVariable int  id)
	{
		categoryService.deleteById(id);
		return "redirect:/admin/categories";
	}
	@GetMapping("/admin/categories/update/{id}")
	public String updateCategory(@PathVariable int  id,Model model)
	{
		Category newCat = categoryService.getById(id);
		model.addAttribute("category", newCat);
		return "categoriesAdd";
	
	}
	//==================================================================PRODUCT=======================================================================
	
	@GetMapping("/admin/products")
	String getAllProducts(Model model)
	{
		List<Product>products = productService.getAll();
		model.addAttribute("products", products);
		return "products";
	}
	@GetMapping("/admin/products/add")
	String createProduct(Model model)
	{
		model.addAttribute("productDTO", new ProductDTO());
		model.addAttribute("categories", categoryService.getAll());
		return "productsAdd";
	}
	//In case of a form anything that doesn't come in the request body comes under request param as here in case of file which is not part of the productDto
	@PostMapping("/admin/products/add")
	String createProductAdd( @ModelAttribute("productDTO") ProductDTO productDTO,
			                 @RequestParam("productImage")MultipartFile file,
			                 @RequestParam("imgName")String imgName) throws IOException
	{
			
		Product product = new Product();
		product.setDescription(productDTO.getDescription());
		product.setId(productDTO.getId());
		product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()));
		product.setName(productDTO.getName());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		
		String imageUUID;
		if(!file.isEmpty())
		{
			imageUUID = file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(upLoadDir, imageUUID);
			Files.write(fileNameAndPath,file.getBytes());
		}
		else imageUUID = imgName;
		
		product.setImageName(imageUUID);
		productService.saveProduct(product);
		
		
		return "redirect:/admin/products";
	}
	@GetMapping("/admin/product/delete/{id}")
	 String deleteProduct(@PathVariable Long id)
	 {
		productService.deleteProductById(id);
		
		return "redirect:/admin/products";
	 }
	@GetMapping("/admin/product/update/{id}")
	 String updateProduct(@PathVariable Long id,Model model)
	 {
		Product product = productService.getProductById(id);
		ProductDTO productDto = new ProductDTO();
		productDto.setCategoryId(product.getCategory().getId());
		productDto.setDescription(product.getDescription());
		productDto.setId(product.getId());
		productDto.setImageName(product.getImageName());
		productDto.setPrice(product.getPrice());
		productDto.setWeight(product.getWeight());
		productDto.setName(product.getName());
		model.addAttribute("productDTO", productDto);
		model.addAttribute("categories", categoryService.getAll());
		return "productsAdd";
	 }
}
