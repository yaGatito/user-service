package com.lazovskyi.userservice.controller.assembler;

import com.lazovskyi.userservice.controller.UserController;
import com.lazovskyi.userservice.controller.model.UserModel;
import com.lazovskyi.userservice.dto.UserDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserAssembler extends RepresentationModelAssemblerSupport<UserDto, UserModel> {

    private static final String GET_REL = "get_user";
    private static final String CREATE_REL = "create_user";
    private static final String PATCH_REL = "patch_user";
    private static final String PUT_REL = "put_user";
    private static final String DELETE_REL = "delete_user";

    public UserAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(UserDto entity) {
        UserModel userModel = new UserModel(entity);

        Link get = linkTo(methodOn(UserController.class).getById(entity.getId())).withRel(GET_REL).withType(HttpMethod.GET.toString());
        Link create = linkTo(methodOn(UserController.class).create(entity)).withRel(CREATE_REL).withType(HttpMethod.POST.toString());
        Link put = linkTo(methodOn(UserController.class).putUpdate(entity)).withRel(PUT_REL).withType(HttpMethod.PUT.toString());
        Link patch = linkTo(methodOn(UserController.class).patchUpdate(entity)).withRel(PATCH_REL).withType(HttpMethod.PATCH.toString());
        Link delete = linkTo(methodOn(UserController.class).delete(entity.getId())).withRel(DELETE_REL).withType(HttpMethod.DELETE.toString());

        userModel.add(get, create, put, patch, delete);
        return userModel;
    }
}
