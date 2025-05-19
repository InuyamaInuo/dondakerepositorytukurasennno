package jp.co.example.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.entity.FoodItem;
import jp.co.example.repository.FoodItemRepository;

@Controller
@RequestMapping("/fridge")
public class FridgeController {

    @Autowired
    FoodItemRepository foodRepo;

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("items", foodRepo.findAllByOrderByPurchaseDateDesc());
        return "fridge/list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("foodItem", new FoodItem());
        return "fridge/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute FoodItem foodItem) {
        if (foodItem.getPurchaseDate() == null) {
            foodItem.setPurchaseDate(LocalDate.now());
        }
        foodRepo.save(foodItem);
        return "redirect:/fridge/list";
    }
}
