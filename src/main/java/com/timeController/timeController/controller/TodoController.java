package com.timeController.timeController.controller;

import org.hibernate.engine.spi.CollectionEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.timeController.timeController.dao.TodoModelRepository;
import com.timeController.timeController.dao.UserRepository;
import com.timeController.timeController.model.TodoModel;
import com.timeController.timeController.model.User;
import com.timeController.timeController.service.ProfileImageService;
import com.timeController.timeController.service.ProfileImageServiceImpl;
import com.timeController.timeController.util.TodoModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping(value = "/api/v1/todo")
public class TodoController {
    @Autowired
    TodoModelRepository todoRepo;
    @Autowired
    TodoModelAssembler todoAssembler;
    @Autowired
    UserRepository userRepo;
    @Autowired
    ProfileImageServiceImpl profileImageService;
    
    @GetMapping(value = "/todo0")
    public CollectionModel<EntityModel<TodoModel>> getTodoList() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findByEmail(userDetails.getUsername()); // this is my user

        List<EntityModel<TodoModel>> todoList = todoRepo.findByUserId(user.getId()).stream().map(todoAssembler::toModel).collect(Collectors.toList());

        Collections.reverse(todoList); // reversing the list so the the most recent entry come at first.
        // List<EntityModel<TodoModel>> todoList = todoRepo.findAll().stream().map(todoAssembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(todoList,linkTo(methodOn(TodoController.class).getTodoList()).withSelfRel());
    }

    @GetMapping(value = "/todo0/{id}")
    public EntityModel<TodoModel> getTodoById(@PathVariable String id) {
        Optional<TodoModel> todo = todoRepo.findById(Long.parseLong(id));

        return todoAssembler.toModel(todo.get());
    }

    @CrossOrigin(origins="http://localhost:3000")
    @PostMapping(value = "/todo0")
    public TodoModel addTodo(@RequestBody TodoModel todo) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepo.findByEmail(userDetails.getUsername());
        todo.setUser(user);
        
        todoRepo.save(todo);
        return todo;
    }

    @PutMapping(value="/todo0/{id}")
    public void updateTodo(@RequestBody TodoModel todo,@PathVariable String id) {
        Optional<TodoModel> todoOptional = todoRepo.findById(Long.parseLong(id));
        TodoModel todoModel = todoOptional.get();

        todoModel.setNameFile(todo.getNameFile());
        todoModel.setDescription(todo.getDescription());
        todoModel.setDate(todo.getDate());
        todoModel.setDone(todo.getDone());
        
        todoRepo.save(todoModel);
    }

    @DeleteMapping(value = "/todo0/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable String id) {
        Optional<TodoModel> todoOptional = todoRepo.findById(Long.parseLong((id)));
        TodoModel todo = todoOptional.get();

        todoRepo.delete(todo);
        
        if (todo == null) {
            return new ResponseEntity<String>("Cannot delete",HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>("Deleted",HttpStatus.OK);
    }

    public String hello() {return "Hello world";}
}
