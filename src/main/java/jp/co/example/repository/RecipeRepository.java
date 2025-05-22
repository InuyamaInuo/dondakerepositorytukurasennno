package jp.co.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.example.entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
	List<Recipe> findAll();
}
