package jp.co.example.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import jp.co.example.entity.FoodItem;
import jp.co.example.entity.Recipe;
import jp.co.example.entity.RecipeItem;

@Service
public class RecipeStatusChecker {

    public static class RecipeDisplayInfo {
        public Recipe recipe;
        public Map<String, Boolean> shortageMap = new HashMap<>(); // 食材名 → 不足かどうか
        public boolean canMake = false;
    }

    public List<RecipeDisplayInfo> evaluateRecipes(List<Recipe> recipes, List<FoodItem> fridgeItems) {
        List<RecipeDisplayInfo> result = new ArrayList<>();

        for (Recipe recipe : recipes) {
            RecipeDisplayInfo info = new RecipeDisplayInfo();
            info.recipe = recipe;

            boolean allAvailable = true;

            for (RecipeItem item : recipe.getItems()) {
                String foodName = item.getFoodName();
                int required = item.getRequiredQuantity();

                // 冷蔵庫の同名食材の合計量を調べる
                int available = fridgeItems.stream()
                    .filter(f -> f.getName().equals(foodName))
                    .mapToInt(FoodItem::getQuantity)
                    .sum();

                boolean isShortage = available < required;
                info.shortageMap.put(foodName, isShortage);

                if (isShortage) {
                    allAvailable = false;
                }
            }

            info.canMake = allAvailable;
            result.add(info);
        }

        return result;
    }
}
