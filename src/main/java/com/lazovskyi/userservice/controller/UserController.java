package com.lazovskyi.userservice.controller;

import com.lazovskyi.userservice.api.UserApi;
import com.lazovskyi.userservice.controller.assembler.UserAssembler;
import com.lazovskyi.userservice.controller.model.UserModel;
import com.lazovskyi.userservice.dto.UserDto;
import com.lazovskyi.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    private final UserAssembler userAssembler;

    @Override
    public ResponseEntity<UserModel> create(UserDto userDto) {
        UserModel body = userAssembler.toModel(userService.create(userDto));
        return new ResponseEntity<>(body, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<UserModel> getById(Long userId) {
        UserModel body = userAssembler.toModel(userService.getById(userId));
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserModel> patchUpdate(UserDto userDto) {
        UserModel body = userAssembler.toModel(userService.patch(userDto));
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserModel> putUpdate(UserDto userDto) {
        UserModel body = userAssembler.toModel(userService.put(userDto));
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> delete(Long userId) {
        userService.delete(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<List<UserModel>> getUsersByBirthDateRange(LocalDate startDate, LocalDate endDate) {
        List<UserModel> body = userService.getUsersByBirthDateRange(startDate, endDate).stream()
                .map(userAssembler::toModel).collect(Collectors.toList());
        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}