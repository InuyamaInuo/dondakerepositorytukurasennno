package jp.co.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.example.entity.ShoppingItem;

public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {
    Optional<ShoppingItem> findByFoodName(String foodName);
    
}

