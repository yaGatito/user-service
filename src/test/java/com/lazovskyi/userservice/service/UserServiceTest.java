package com.lazovskyi.userservice.service;

import com.lazovskyi.userservice.dto.UserDto;
import com.lazovskyi.userservice.exceptions.NonexistentEntityException;
import com.lazovskyi.userservice.mapper.UserMapper;
import com.lazovskyi.userservice.model.User;
import com.lazovskyi.userservice.repository.UserRepository;
import com.lazovskyi.userservice.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    private UserMapper userMapper = UserMapper.INSTANCE;

    private static final long TEST_USER_ID = 1L;
    private static final String TEST_USER_NAME = "John";
    private static final String TEST_USER_LASTNAME = "Wick";
    private static final String TEST_USER_EMAIL = "johnwick@gmail.com";
    private static final LocalDate TEST_USER_BIRTHDATE = LocalDate.of(2000, 1, 10);
    private static final String TEST_USER_ADDRESS = "Sofiyvska str., 2";
    private static final String TEST_USER_NUMBER = "0776667788";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    void testCreateUser() {
        UserDto userDto = createUserDtoTestData();
        User userToSave = userMapper.mapUser(userDto);
        userToSave.setId(TEST_USER_ID);

        when(userRepository.save(any())).thenReturn(userToSave);

        UserDto createdUserDto = userService.create(userDto);

        assertNotNull(createdUserDto);
        assertNotNull(createdUserDto.getId());
        assertOnUserFieldsEqual(createdUserDto, userDto);
    }

    @Test
    void testGetUserById() {
        User user = createUserTestData();
        user.setId(TEST_USER_ID);
        UserDto expectedUserDto = userMapper.mapUserDto(user);

        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(user));

        UserDto retrievedUserDto = userService.getById(TEST_USER_ID);

        assertNotNull(retrievedUserDto);
        assertOnUserFieldsEqual(expectedUserDto, retrievedUserDto);
    }

    @Test
    void testGetUserWithNonExistentId() {
        UserDto userDto = createUserDtoTestData();
        userDto.setId(TEST_USER_ID);
        when(userRepository.findById(TEST_USER_ID)).thenThrow(NonexistentEntityException.class);
        assertThrows(NonexistentEntityException.class, () -> userService.getById(TEST_USER_ID));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testPatchUser() {
        UserDto userDto = new UserDto();
        userDto.setId(TEST_USER_ID);
        userDto.setNumber("0776663344");
        User userInDatabase = createUserTestData();
        userInDatabase.setId(userDto.getId());

        User savedUser = createUserTestData();
        savedUser.setNumber(userDto.getNumber());

        when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(userInDatabase));
        when(userRepository.save(any())).thenReturn(savedUser);

        UserDto updatedUserDto = userService.patch(userDto);

        assertNotNull(updatedUserDto);
        assertNotNull(updatedUserDto.getName());
        assertNotNull(updatedUserDto.getLastname());
        assertNotNull(updatedUserDto.getEmail());
        assertNotNull(updatedUserDto.getBirthdate());
        assertNotNull(updatedUserDto.getAddress());
        assertEquals(userDto.getNumber(), updatedUserDto.getNumber());
    }

    @Test
    void testPatchUserWithNonExistentId() {
        UserDto userDto = createUserDtoTestData();
        userDto.setId(TEST_USER_ID);
        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.empty());
        assertThrows(NonexistentEntityException.class, () -> userService.patch(userDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testPutUser() {
        UserDto userDto = createUserDtoTestData();
        userDto.setId(TEST_USER_ID);
        userDto.setAddress(null);

        User userInDatabase = createUserTestData();
        userInDatabase.setId(userDto.getId());

        User savedUser = createUserTestData();
        savedUser.setId(userDto.getId());
        savedUser.setAddress(null);

        when(userRepository.findById(userDto.getId())).thenReturn(Optional.of(userInDatabase));
        when(userRepository.save(any())).thenReturn(savedUser);

        UserDto updatedUserDto = userService.put(userDto);

        assertNotNull(updatedUserDto);
        assertOnUserFieldsEqual(userDto, savedUser);
    }

    @Test
    void testPutUserWithNonExistentId() {
        UserDto userDto = createUserDtoTestData();
        userDto.setId(TEST_USER_ID);
        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.empty());
        assertThrows(NonexistentEntityException.class, () -> userService.put(userDto));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser() {
        User user = createUserTestData();
        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.of(user));
        userService.delete(TEST_USER_ID);
        verify(userRepository).delete(user);
    }

    @Test
    void testDeleteUserWithNonExistentId() {
        when(userRepository.findById(TEST_USER_ID)).thenReturn(Optional.empty());
        assertThrows(NonexistentEntityException.class, () -> userService.delete(TEST_USER_ID));
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void testGetUsersByBirthDateRange() {
        LocalDate firstDate = TEST_USER_BIRTHDATE.minusYears(1);
        LocalDate secondDate = TEST_USER_BIRTHDATE.plusYears(1);
        List<User> userDtoList = List.of(createUserTestData(), createUserTestData());
        userDtoList.get(0).setId(TEST_USER_ID);
        userDtoList.get(1).setId(TEST_USER_ID);

        when(userRepository.findAllByBirthdateBetween(firstDate, secondDate)).thenReturn(userDtoList);

        List<UserDto> retrievedUserDtos = userService.getUsersByBirthDateRange(firstDate, secondDate);
        assertEquals(retrievedUserDtos.size(), userDtoList.size());
        for (int i = 0; i < retrievedUserDtos.size(); i++) {
            assertNotNull(retrievedUserDtos.get(i));
            assertEquals(retrievedUserDtos.get(i).getId(), userDtoList.get(i).getId());
            assertEquals(retrievedUserDtos.get(i).getName(), userDtoList.get(i).getName());
            assertEquals(retrievedUserDtos.get(i).getLastname(), userDtoList.get(i).getLastname());
            assertEquals(retrievedUserDtos.get(i).getEmail(), userDtoList.get(i).getEmail());
            assertEquals(retrievedUserDtos.get(i).getNumber(), userDtoList.get(i).getNumber());
            assertEquals(retrievedUserDtos.get(i).getAddress(), userDtoList.get(i).getAddress());
            assertEquals(retrievedUserDtos.get(i).getBirthdate(), userDtoList.get(i).getBirthdate());
        }
    }

    private UserDto createUserDtoTestData() {
        return new UserDto(null,
                TEST_USER_NAME,
                TEST_USER_LASTNAME,
                TEST_USER_EMAIL,
                TEST_USER_BIRTHDATE,
                TEST_USER_ADDRESS,
                TEST_USER_NUMBER);
    }

    private User createUserTestData() {
        return new User(null,
                TEST_USER_NAME,
                TEST_USER_LASTNAME,
                TEST_USER_EMAIL,
                TEST_USER_BIRTHDATE,
                TEST_USER_ADDRESS,
                TEST_USER_NUMBER);
    }

    private void assertOnUserFieldsEqual(UserDto first, UserDto second) {
        assertEquals(first.getName(), second.getName());
        assertEquals(first.getLastname(), second.getLastname());
        assertEquals(first.getEmail(), second.getEmail());
        assertEquals(first.getNumber(), second.getNumber());
        assertEquals(first.getBirthdate(), second.getBirthdate());
        assertEquals(first.getAddress(), second.getAddress());
    }

    private void assertOnUserFieldsEqual(UserDto first, User second) {
        assertEquals(first.getName(), second.getName());
        assertEquals(first.getLastname(), second.getLastname());
        assertEquals(first.getEmail(), second.getEmail());
        assertEquals(first.getNumber(), second.getNumber());
        assertEquals(first.getBirthdate(), second.getBirthdate());
        assertEquals(first.getAddress(), second.getAddress());
    }
}
