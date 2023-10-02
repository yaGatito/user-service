package com.lazovskyi.userservice.service;

import com.lazovskyi.userservice.dto.UserDto;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    UserDto create(UserDto user);

    UserDto getById(Long id);

    UserDto patch(UserDto userDto);

    UserDto put(UserDto userDto);

    void delete(Long id);

    List<UserDto> getUsersByBirthDateRange(LocalDate startDate, LocalDate endDate);
}
