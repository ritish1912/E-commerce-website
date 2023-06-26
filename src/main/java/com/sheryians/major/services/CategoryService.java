package com.sheryians.major.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sheryians.major.entities.Category;
import com.sheryians.major.repositories.CategoryRepository;

@Service
public class CategoryService {
	@Autowired
	CategoryRepository categoryRepository;

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
	}
	public List<Category> getAll()
	{
		return categoryRepository.findAll();
	}
	public void deleteById(int id) {
		categoryRepository.deleteById(id);
		
	}
	public Category getById(int id)
	{
		return categoryRepository.findById(id).get();
				
	}
	public Category getCategoryById(int categoryId) {
		
		return categoryRepository.findById(categoryId).get();
	}
}
