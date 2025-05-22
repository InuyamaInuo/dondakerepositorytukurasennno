package jp.co.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RecipeItem {

	//食材ID(非表示)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer itemId;

	//レシピエンティティと連携
	 @ManyToOne
	 @JoinColumn(name = "recipe_id")
	private Recipe recipe;

	//食材名
	private String foodName;

	//必要な食材の量
	private int requiredQuantity;

	
	//getterとsetter
	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	public int getRequiredQuantity() {
		return requiredQuantity;
	}

	public void setRequiredQuantity(int requiredQuantity) {
		this.requiredQuantity = requiredQuantity;
	}
}
