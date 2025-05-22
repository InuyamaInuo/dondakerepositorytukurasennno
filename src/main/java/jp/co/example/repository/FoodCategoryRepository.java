package jp.co.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.example.entity.FoodCategory;

public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Integer> {
}
