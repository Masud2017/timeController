package com.timeController.timeController.dao;

import com.timeController.timeController.model.TodoModel;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoModelRepository extends JpaRepository<TodoModel,Long>{
    
}
