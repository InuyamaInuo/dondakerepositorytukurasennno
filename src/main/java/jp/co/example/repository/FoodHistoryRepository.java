package jp.co.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.example.entity.FoodHistory;

public interface FoodHistoryRepository extends JpaRepository<FoodHistory, Integer> {
	List<FoodHistory> findAllByOrderByAddedAtDesc();
}
