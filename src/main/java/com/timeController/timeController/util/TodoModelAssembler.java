package com.timeController.timeController.util;

import com.timeController.timeController.controller.TodoController;
import com.timeController.timeController.model.TodoModel;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
@Component
public class TodoModelAssembler implements RepresentationModelAssembler<TodoModel,EntityModel<TodoModel>>{

    @Override
    public EntityModel<TodoModel> toModel(TodoModel todo) {
        EntityModel.of(linkTo(modelOn(TodoController.class).getTodoListById(Long.toString(todo.getId()))).withSelfRel(),
        linkTo(methodOn(TodoController.class).getTodoList().withRel("todoList"));
        );
        return null;
    }


   
}
