package jp.co.example.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.example.entity.FoodHistory;
import jp.co.example.entity.FoodItem;
import jp.co.example.repository.FoodHistoryRepository;
import jp.co.example.repository.FoodItemRepository;
import jp.co.example.service.Shomikigen;

@Controller
public class FridgeController {

	@Autowired
	FoodItemRepository fir;
	@Autowired
	private FoodHistoryRepository foodHistoryRepository;

	//リストを表示する
	@GetMapping("/fridge/list")
	public String showList(Model model) {
		model.addAttribute("items", fir.findAllByOrderByPurchaseDateDesc());
		return "fridge/list";
	}

	//食材を追加するフォーム
	@GetMapping("/fridge/new")
	public String showForm(Model model) {
		model.addAttribute("foodItem", new FoodItem());
		return "fridge/form";
	}

	//入力された食材情報をリストに載せる
	@PostMapping("/fridge/save")
	public String save(@ModelAttribute FoodItem foodItem, RedirectAttributes redirectAttributes) {
		if (foodItem.getPurchaseDate() == null) {
			foodItem.setPurchaseDate(LocalDate.now());
		}
		Shomikigen s = new Shomikigen();
		LocalDate expiration = s.calculateExpirationDate(
				cate(foodItem),
				foodItem.isFrozen(),
				foodItem.getPurchaseDate(),
				foodItem.getCustomDaysToExpire());
		foodItem.setExpirationDate(expiration);
		fir.save(foodItem);
		redirectAttributes.addFlashAttribute("addedmessage", "「" + foodItem.getName() + "」を登録しました！");

		// 履歴に追加（同じ名前の重複を避けるには工夫してもOK）
		FoodHistory history = new FoodHistory();
		history.setFoodName(foodItem.getName());
		history.setAddedAt(LocalDateTime.now());
		foodHistoryRepository.save(history);

		// 最大50件を超えたら古いものから削除
		List<FoodHistory> all = foodHistoryRepository.findAllByOrderByAddedAtDesc();
		if (all.size() > 50) {
			foodHistoryRepository.deleteAll(all.subList(50, all.size()));
		}
		
		return "redirect:/fridge/list";
	}

	//その他カテの名前と賞味期限算出用
	public String cate(FoodItem form) {
		String finalCategory = form.getCategory();
		// カスタムカテゴリが入力されていたら foodItem に反映
	    if ("その他".equals(form.getCategory()) && form.getCustomCategory() != null && !form.getCustomCategory().isEmpty()) {
	        form.setCategory(form.getCustomCategory());
	    }
		if ("その他".equals(finalCategory) && form.getCustomCategory() != null && !form.getCustomCategory().isEmpty()) {
			finalCategory = form.getCustomCategory();
		} else {
			finalCategory = form.getCategory();
		}
		return finalCategory;
	}

	//リストの食材を編集するフォーム
	@GetMapping("/fridge/edit/{id}")
	public String editFoodItem(@PathVariable Integer id, Model model) {
		FoodItem item = fir.findById(id).orElseThrow();
		model.addAttribute("foodItem", item);
		return "fridge/edit";
	}

	//更新した情報をリストに登録
	@PostMapping("/fridge/update")
	public String updateFoodItem(@ModelAttribute FoodItem foodItem, RedirectAttributes redirectAttributes) {
		if (foodItem.getPurchaseDate() == null) {
			foodItem.setPurchaseDate(LocalDate.now());
		}
		Shomikigen s = new Shomikigen();
		LocalDate expiration = s.calculateExpirationDate(
				cate(foodItem),
				foodItem.isFrozen(),
				foodItem.getPurchaseDate(),
				foodItem.getCustomDaysToExpire());
		foodItem.setExpirationDate(expiration);
		redirectAttributes.addFlashAttribute("changedmessage", "「" + foodItem.getName() + "」を更新しました");

		//デバッグ用ログ残し
		FoodItem saved = fir.save(foodItem);
		System.out.println("保存された内容: " + saved.getName() + " / " + saved.getExpirationDate());
		return "redirect:/fridge/list";
	}

	//選択したものを消す
	@PostMapping("/fridge/delete")
	public String deleteItems(@RequestParam("selectedIds") List<Integer> ids,
			RedirectAttributes redirectAttributes) {
		List<FoodItem> itemsToDelete = fir.findAllById(ids);
		String deletedNames = itemsToDelete.stream()
				.map(FoodItem::getName)
				.collect(Collectors.joining("・")); // 「卵・牛乳・トマト」のように表示

		fir.deleteAllById(ids);
		redirectAttributes.addFlashAttribute("deletedmessage", "「" + deletedNames + "」を削除しました");

		return "redirect:/fridge/list";
	}

}
