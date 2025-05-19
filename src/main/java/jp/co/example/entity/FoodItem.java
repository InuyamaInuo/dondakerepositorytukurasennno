package jp.co.example.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FoodItem {

	//食品ID(主キー)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	//品名
	private String name;
	
	//量
	private int quantity;
	
	//食品のカテゴリー(肉とか野菜とか)
	private String category;
	
	//買った日
	private LocalDate purchaseDate;
	
	//冷凍してるかどうか
	private boolean frozen;
	
	//getter, setter
	public Integer getId() {
	    return id;
	}
	public void setId(Integer id) {
	    this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	public boolean isFrozen() {
		return frozen;
	}
	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}
	
	// 賞味期限日数（カテゴリに応じて動的に計算する or デフォルト保持）
	// → 計算はサービスで行ってもOK

}
