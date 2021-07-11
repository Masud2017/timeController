package com.timeController.timeController.dao;

import java.util.List;

import com.timeController.timeController.model.TodoModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoModelRepository extends JpaRepository<TodoModel,Long>{
    List<TodoModel> findByUserId(Long userId);
}
