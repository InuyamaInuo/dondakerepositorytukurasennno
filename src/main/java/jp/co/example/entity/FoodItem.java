package jp.co.example.entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;

@Entity
public class FoodItem {

	//食品ID(主キー)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	//品名
	private String name;
	
	//量
	@Min(0)
	private int quantity;
	
	//食品のカテゴリー(肉とか野菜とか)
	private String category;
	
	//買った日
	private LocalDate purchaseDate;
	
	//冷凍してるかどうか
	private boolean frozen;
	
	//賞味期限
	private LocalDate expirationDate;
	
	//賞味期限まであと何日あるか
	public long getDaysUntilExpiration() {
        return ChronoUnit.DAYS.between(LocalDate.now(), expirationDate);
    }
	
	// 自由入力カテゴリ名
	private String customCategory;
	
	// 自由入力賞味期限（日数）
    private Integer customDaysToExpire;   

	
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
	
	public LocalDate getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(LocalDate expirationDate) {
	    this.expirationDate = expirationDate;
	}
	
	public String getCustomCategory() {
		return customCategory;
	}
	public void setCustomCategory(String customCategory) {
		this.customCategory = customCategory;
	}
	public Integer getCustomDaysToExpire() {
		return customDaysToExpire;
	}
	public void setCustomDaysToExpire(Integer customDaysToExpire) {
		this.customDaysToExpire = customDaysToExpire;
	}
}
