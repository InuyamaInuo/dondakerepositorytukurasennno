package jp.co.example.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Recipe {
	//レシピ名
	private String recipeName;
	
	//レシピID(非表示)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer recipeId;
	
	@OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecipeItem> items = new ArrayList<>();
	
	
	
	//gettersetter 
	public String getRecipeName() {
		return recipeName;
	}
	public void setRecipeName(String recepeName) {
		this.recipeName = recepeName;
	}
	
	public Integer getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(Integer recepeId) {
		this.recipeId = recepeId;
	}
	public List<RecipeItem> getItems() {
		return items;
	}
	public void setItems(List<RecipeItem> items) {
		this.items = items;
	}
}
