package jp.co.example.service;

import org.springframework.stereotype.Service;

import jp.co.example.repository.RecipeRepository;

@Service
public class RecipeService {

	private final RecipeRepository recipeRepository;

	public RecipeService(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	public void deleteById(Iterable<? extends Integer> id) {
		recipeRepository.deleteAllById(id);
	}

}
