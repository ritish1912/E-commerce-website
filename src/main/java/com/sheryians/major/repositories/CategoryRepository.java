package com.sheryians.major.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sheryians.major.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
