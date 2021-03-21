package com.timeController.timeController.util;

import com.timeController.timeController.controller.HomeController;
import com.timeController.timeController.model.User;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User,EntityModel<User>>{

    @Override
    public EntityModel<User> toModel(User user) {
        
        return EntityModel.of(user,
            linkTo(methodOn(HomeController.class).one(user.getId())).withSelfRel(),
            linkTo(methodOn(HomeController.class).all()).withRel("employees"));
    }
    
}
