package jp.co.example.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import jp.co.example.entity.FoodItem;

public interface FoodItemRepository extends JpaRepository<FoodItem, Integer> {
    List<FoodItem> findAllByOrderByPurchaseDateDesc();
}
