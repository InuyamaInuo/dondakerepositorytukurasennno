package jp.co.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jp.co.example.entity.FoodItem;
import jp.co.example.repository.FoodItemRepository;
import jp.co.example.repository.ShoppingItemRepository;
import jp.co.example.service.FridgeToShoppingList;

@Controller
public class ShoppingListController {

	@Autowired
    private ShoppingItemRepository shoppingRepo;
	@Autowired
	private FridgeToShoppingList fridgeToShopping;
	@Autowired
	private FoodItemRepository foodItemRepo;

    public ShoppingListController(ShoppingItemRepository shoppingRepo) {
        this.shoppingRepo = shoppingRepo;
    }

    //リスト一覧の取得
    @GetMapping("/shopping/list")
    public String showShoppingList(Model model) {
        model.addAttribute("shoppingList", shoppingRepo.findAll());
        return "shopping/list";
    }

    //食材の追加
    @PostMapping("/shopping/update")
    @ResponseBody
    public void updateItem(@RequestParam Long id, @RequestParam int quantity) {
        shoppingRepo.findById(id).ifPresent(item -> {
            item.setQuantity(quantity);
            shoppingRepo.save(item);
        });
    }

    //食材の削除
    @PostMapping("/shopping/delete")
    @ResponseBody
    public void deleteItem(@RequestParam Long id) {
        shoppingRepo.deleteById(id);
    }
    
    //食材の追加(冷蔵庫リストから)
    @PostMapping("/shopping/addFromFridge")
    public String addFromFridge(@RequestParam("selectedIds") List<Integer> selectedIds) {
        for (Integer id : selectedIds) {
            FoodItem item = foodItemRepo.findById(id).orElse(null);
            if (item != null) {
                System.out.println("追加: " + item.getName());
                fridgeToShopping.addOrUpdateItem(item.getName(), 1);
            } else {
                System.out.println("IDが見つかりませんでした: " + id);
            }
        }
        return "redirect:/shopping/list";
    }
    
    //食材の追加(手動)
    @PostMapping("/shopping/manualAdd")
    @ResponseBody
    public ResponseEntity<?> manualAdd(@RequestParam String foodName, @RequestParam int quantity) {
        fridgeToShopping.addOrUpdateItem(foodName, quantity);
        return ResponseEntity.ok().build();
    }



}

