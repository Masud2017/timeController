package com.timeController.timeController.controller;

import org.hibernate.engine.spi.CollectionEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.timeController.timeController.dao.TodoModelRepository;
import com.timeController.timeController.model.TodoModel;
import com.timeController.timeController.util.TodoModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping(value = "/api/v1/todo")
public class TodoController {
    @Autowired
    TodoModelRepository todoRepo;
    @Autowired
    TodoModelAssembler todoAssembler;
    
    @GetMapping(value = "/todo0")
    public CollectionModel<EntityModel<TodoModel>> getTodoList() {
        List<EntityModel<TodoModel>> todoList = todoRepo.findAll().stream().map(todoAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(todoList,linkTo(methodOn(TodoController.class).getTodoList()).withSelfRel());
    }

    @GetMapping(value = "/todo0/{id}")
    public EntityModel<TodoModel> getTodoById(@PathVariable String id) {
        Optional<TodoModel> todo = todoRepo.findById(Long.parseLong(id));

        return todoAssembler.toModel(todo.get());
    }
    
}
