package com.lazovskyi.userservice.controller;

import com.lazovskyi.userservice.controller.assembler.UserAssembler;
import com.lazovskyi.userservice.controller.model.UserModel;
import com.lazovskyi.userservice.dto.UserDto;
import com.lazovskyi.userservice.exceptions.NonexistentEntityException;
import com.lazovskyi.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserController userController;

    @Mock
    private UserService userService;

    private UserAssembler userAssembler = new UserAssembler();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        userController = new UserController(userService, userAssembler);
    }

    @Test
    void testCreateUser() {
        UserDto userDto = new UserDto();
        UserDto createdUserDto = new UserDto();
        createdUserDto.setId(1L); // Simulate a user creation

        when(userService.create(userDto)).thenReturn(createdUserDto); // Mock the userService behavior

        ResponseEntity<UserModel> responseEntity = userController.create(userDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(createdUserDto.getId(), responseEntity.getBody().getUserDto().getId());
    }

    @Test
    void testGetUserById() {
        Long userId = 1L; // Replace with a valid user ID
        UserDto userDto = new UserDto();
        userDto.setId(userId); // Simulate a user retrieval

        when(userService.getById(userId)).thenReturn(userDto); // Mock the userService behavior

        ResponseEntity<UserModel> responseEntity = userController.getById(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(userDto.getId(), responseEntity.getBody().getUserDto().getId());
    }

    @Test
    void testPatchUser() {
        UserDto userDto = new UserDto();
        userDto.setId(1L); // Replace with a valid user ID
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setId(userDto.getId()); // Simulate a user update

        when(userService.patch(userDto)).thenReturn(updatedUserDto); // Mock the userService behavior

        ResponseEntity<UserModel> responseEntity = userController.patchUpdate(userDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updatedUserDto.getId(), responseEntity.getBody().getUserDto().getId());
    }

    @Test
    void testPutUser() {
        UserDto userDto = new UserDto();
        userDto.setId(1L); // Replace with a valid user ID
        UserDto updatedUserDto = new UserDto();
        updatedUserDto.setId(userDto.getId()); // Simulate a user update

        when(userService.put(userDto)).thenReturn(updatedUserDto); // Mock the userService behavior

        ResponseEntity<UserModel> responseEntity = userController.putUpdate(userDto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(updatedUserDto.getId(), responseEntity.getBody().getUserDto().getId());
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L; // Replace with a valid user ID

        ResponseEntity<Void> responseEntity = userController.delete(userId);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(userService, times(1)).delete(userId); // Verify that the delete method was called
    }

    @Test
    void testDeleteUserWithNonExistentId() {
        Long userId = 1L; // Replace with a non-existent user ID

        doThrow(new NonexistentEntityException("User not found")).when(userService).delete(userId);

        assertThrows(NonexistentEntityException.class, () -> userController.delete(userId));
    }

    @Test
    void testGetUsersByBirthDateRange() {
        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 12, 31);
        List<UserDto> users = new ArrayList<>(); // Simulate a list of users

        when(userService.getUsersByBirthDateRange(startDate, endDate)).thenReturn(users); // Mock the userService behavior

        ResponseEntity<List<UserModel>> responseEntity = userController.getUsersByBirthDateRange(startDate, endDate);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(users.size(), responseEntity.getBody().size());
    }
}