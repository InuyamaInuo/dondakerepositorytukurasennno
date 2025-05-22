package jp.co.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.example.entity.RecipeItem;

public interface RecipeItemRepository extends JpaRepository<RecipeItem, Integer> {
}