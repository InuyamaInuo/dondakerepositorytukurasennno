package jp.co.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.example.entity.FoodHistory;
import jp.co.example.entity.FoodItem;
import jp.co.example.entity.Recipe;
import jp.co.example.entity.RecipeItem;
import jp.co.example.repository.FoodHistoryRepository;
import jp.co.example.repository.FoodItemRepository;
import jp.co.example.repository.RecipeRepository;
import jp.co.example.service.RecipeStatusChecker;

@Controller
@RequestMapping("/recipes")
public class RecipeController {

	//冷蔵庫の中身取得
	@Autowired
	private FoodItemRepository foodItemRepository;
	//献立一覧取得
	@Autowired
	private RecipeRepository recipeRepository;
	//冷蔵庫に追加した履歴の取得
	@Autowired
	private FoodHistoryRepository foodHistoryRepository;
	//冷蔵庫の中身と献立内容を比較
	@Autowired
	private RecipeStatusChecker statusChecker;

	//リストを表示
	@GetMapping("/list")
	public String listRecipes(Model model) {
	    List<Recipe> recipes = recipeRepository.findAll();
	    List<FoodItem> fridgeItems = foodItemRepository.findAll(); // 冷蔵庫の中身を取得

	    List<RecipeStatusChecker.RecipeDisplayInfo> recipeInfoList = statusChecker.evaluateRecipes(recipes, fridgeItems);

	    model.addAttribute("recipeInfoList", recipeInfoList);
	    return "recipe/list";
	}
	//献立を追加する
	@GetMapping("/new")
	public String showNewRecipeForm(Model model) {
		Recipe recipe = new Recipe();
		// 空の RecipeItem を1つ入れておく（表示用）
		recipe.setItems(new ArrayList<>());
		recipe.getItems().add(new RecipeItem());
		model.addAttribute("recipe", recipe);
		//冷蔵庫の履歴表示
		List<FoodHistory> historyList = foodHistoryRepository.findAllByOrderByAddedAtDesc();
		model.addAttribute("historyList", historyList);		
		return "recipe/new";
	}

	//追加した献立をリストに反映
	@PostMapping("/save")
	public String saveRecipe(@ModelAttribute Recipe recipe, RedirectAttributes redirectAttributes) {
		for (RecipeItem item : recipe.getItems()) {
			item.setRecipe(recipe);
		}
		recipeRepository.save(recipe);
		redirectAttributes.addFlashAttribute("addedmessage", "「" + recipe.getRecipeName() + "」を登録しました！");
		return "redirect:/recipes/list";
	}

	//既存の献立を編集
	@GetMapping("/edit/{id}")
	public String editRecipe(@PathVariable Integer id, Model model) {
		Recipe recipe = recipeRepository.findById(id).orElseThrow();
		List<FoodHistory> historyList = foodHistoryRepository.findAllByOrderByAddedAtDesc();
		model.addAttribute("recipe", recipe);
		model.addAttribute("historyList", historyList);
		return "recipe/edit";
	}

	//編集後の献立を更新
	@PostMapping("/update")
	public String updateRecipe(@ModelAttribute Recipe recipe, RedirectAttributes redirectAttributes) {
		for (RecipeItem item : recipe.getItems()) {
			item.setRecipe(recipe);
		}
		recipeRepository.save(recipe);
		redirectAttributes.addFlashAttribute("changedmessage", "「" + recipe.getRecipeName() + "」を更新しました");
		return "redirect:/recipes/list";
	}

	//献立を消す
	@PostMapping("/delete/{id}")
	public String deleteRecipe(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
		// 先に名前だけ取得しておく
		Recipe recipe = recipeRepository.findById(id).orElse(null);
		if (recipe != null) {
			String name = recipe.getRecipeName(); // ★ 削除前に取得
			recipeRepository.deleteById(id);
			redirectAttributes.addFlashAttribute("deletedmessage", "【" + name + "】を削除しました");
		} else {
			redirectAttributes.addFlashAttribute("deletedmessage", "献立が見つかりませんでした");
		}
		recipeRepository.deleteById(id);
		redirectAttributes.addFlashAttribute("deletedmessage", "「" + recipe.getRecipeName() + "」を削除しました");
		return "redirect:/recipes/list";
	}

}
