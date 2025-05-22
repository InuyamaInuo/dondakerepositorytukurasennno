package jp.co.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.example.entity.FoodItem;
import jp.co.example.entity.ShoppingItem;
import jp.co.example.repository.FoodItemRepository;
import jp.co.example.repository.ShoppingItemRepository;

@Service
public class FridgeToShoppingList {

	    @Autowired
	    private ShoppingItemRepository shoppingItemRepo;	    
	    @Autowired
	    private FoodItemRepository foodItemRepo;

	    
	    public FoodItem findById(Integer id) {
	        return foodItemRepo.findById(id).orElse(null);
	    }

	    public void addOrUpdateItem(String foodName, int quantityToAdd) {
	        Optional<ShoppingItem> existing = shoppingItemRepo.findByFoodName(foodName);
	        if (existing.isPresent()) {
	            ShoppingItem item = existing.get();
	            item.setQuantity(item.getQuantity() + quantityToAdd);
	            shoppingItemRepo.save(item);
	        } else {
	            ShoppingItem item = new ShoppingItem();
	            item.setFoodName(foodName);
	            item.setQuantity(quantityToAdd);
	            shoppingItemRepo.save(item);
	        }
	    }

	    public List<ShoppingItem> findAll() {
	        return shoppingItemRepo.findAll();
	    }

	    public void deleteById(Long id) {
	        shoppingItemRepo.deleteById(id);
	    }

	    public void updateQuantity(Long id, int newQuantity) {
	        ShoppingItem item = shoppingItemRepo.findById(id).orElse(null);
	        if (item != null) {
	            item.setQuantity(newQuantity);
	            shoppingItemRepo.save(item);
	        }
	    }
}
