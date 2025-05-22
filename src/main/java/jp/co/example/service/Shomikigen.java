package jp.co.example.service;

import java.time.LocalDate;

public class Shomikigen {

	public LocalDate calculateExpirationDate(String category, boolean frozen, LocalDate purchaseDate, Integer customDaysToExpire) {
	    int days = switch (category) {
	        case "生肉、生魚" -> frozen ? 30 : 5;
	        case "野菜" -> frozen ? 20 : 10;
	        case "加工品" -> frozen ? 60 : 20;
	        case "卵" -> frozen ? 4 : 15;
	        default -> days = (customDaysToExpire != null) ? customDaysToExpire : 10;
	    };
	    return purchaseDate.plusDays(days);
	}

}
