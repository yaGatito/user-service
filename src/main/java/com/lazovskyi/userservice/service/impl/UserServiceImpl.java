package com.lazovskyi.userservice.service.impl;


import com.lazovskyi.userservice.dto.UserDto;
import com.lazovskyi.userservice.exceptions.NonexistentEntityException;
import com.lazovskyi.userservice.mapper.UserMapper;
import com.lazovskyi.userservice.model.User;
import com.lazovskyi.userservice.repository.UserRepository;
import com.lazovskyi.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper mapper = UserMapper.INSTANCE;

    @Override
    public UserDto create(UserDto user) {
        User persistedUser = userRepository.save(mapper.mapUser(user));
        log.debug("Post operation. User with id:{}", persistedUser.getId());
        return mapper.mapUserDto(persistedUser);
    }

    @Override
    public UserDto getById(Long id) {
        User persistedUser = userRepository.findById(id).orElseThrow(
                () -> new NonexistentEntityException(String.format("User with id:%d doesn't exist", id)));
        log.debug("Get operation. User with id:{}", persistedUser.getId());
        return mapper.mapUserDto(persistedUser);
    }

    @Override
    public UserDto patch(UserDto userDto) {
        User persistedUser = userRepository.findById(userDto.getId()).orElseThrow(
                () -> new NonexistentEntityException(String.format("User with id:%d doesn't exist", userDto.getId())));
        mapper.populateUserWithPresentUserDtoFields(persistedUser, userDto);
        persistedUser = userRepository.save(persistedUser);
        log.debug("Patch operation. User with id:{}", persistedUser.getId());
        return mapper.mapUserDto(persistedUser);
    }

    @Override
    public UserDto put(UserDto userDto) {
        User persistedUser = userRepository.findById(userDto.getId()).orElseThrow(
                () -> new NonexistentEntityException(String.format("User with id:%d doesn't exist", userDto.getId())));
        mapper.populateUserWithUserDtoFields(persistedUser, userDto);
        persistedUser = userRepository.save(persistedUser);
        log.debug("Put operation. User with id:{}", persistedUser.getId());
        return mapper.mapUserDto(persistedUser);
    }

    @Override
    public void delete(Long id) {
        userRepository.delete(userRepository.findById(id).orElseThrow(
                () -> new NonexistentEntityException(String.format("User with id:%d doesn't exist", id))));
        log.debug("Delete operation to DB. User with id:{}", id);
    }

    @Override
    public List<UserDto> getUsersByBirthDateRange(LocalDate startDate, LocalDate endDate) {
        List<UserDto> users = userRepository.findAllByBirthdateBetween(startDate, endDate).stream().map(mapper::mapUserDto).collect(Collectors.toList());
        log.debug("Users were retrieved from DB to satisfy /by-date-range request");
        return  users;
    }
}
