package com.lazovskyi.userservice.mapper;

import com.lazovskyi.userservice.dto.UserDto;
import com.lazovskyi.userservice.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapUser(UserDto userDto);

    UserDto mapUserDto(User user);

    default User populateUserWithPresentUserDtoFields(User persisted, UserDto userDto) {
        if (Objects.nonNull(userDto.getLastname())) {
            persisted.setLastname(userDto.getLastname());
        }
        if (Objects.nonNull(userDto.getName())) {
            persisted.setName(userDto.getName());
        }
        if (Objects.nonNull(userDto.getEmail())) {
            persisted.setEmail(userDto.getEmail());
        }
        if (Objects.nonNull(userDto.getAddress())) {
            persisted.setAddress(userDto.getAddress());
        }
        if (Objects.nonNull(userDto.getBirthdate())) {
            persisted.setBirthdate(userDto.getBirthdate());
        }
        if (Objects.nonNull(userDto.getNumber())) {
            persisted.setNumber(userDto.getNumber());
        }
        return persisted;
    }

    default User populateUserWithUserDtoFields(User persisted, UserDto userDto) {
        persisted.setLastname(userDto.getLastname());
        persisted.setName(userDto.getName());
        persisted.setEmail(userDto.getEmail());
        persisted.setAddress(userDto.getAddress());
        persisted.setBirthdate(userDto.getBirthdate());
        persisted.setNumber(userDto.getNumber());
        return persisted;
    }

}
