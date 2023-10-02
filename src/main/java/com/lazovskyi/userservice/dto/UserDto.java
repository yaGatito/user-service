package com.lazovskyi.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lazovskyi.userservice.dto.group.OnCreate;
import com.lazovskyi.userservice.dto.group.OnPatch;
import com.lazovskyi.userservice.dto.group.OnPut;
import com.lazovskyi.userservice.dto.validation.date.DateType;
import com.lazovskyi.userservice.dto.validation.date.ValidateDate;
import com.lazovskyi.userservice.dto.validation.string.StringType;
import com.lazovskyi.userservice.dto.validation.string.ValidateString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @NotNull(message = "{userDto.id.NotNull}", groups = {OnPatch.class, OnPut.class})
    @Null(message = "{userDto.id.Null}", groups = {OnCreate.class})
    @Positive(message = "{userDto.id.Positive}", groups = {OnCreate.class, OnPatch.class, OnPut.class})
    private Long id;

    @NotBlank(message = "{userDto.name.NotBlank}", groups = {OnCreate.class, OnPut.class})
    @ValidateString(message = "{userDto.name.ValidateString}", value = StringType.NAME, groups = {OnCreate.class, OnPatch.class, OnPut.class})
    private String name;

    @NotBlank(message = "{userDto.lastname.NotBlank}", groups = {OnCreate.class, OnPut.class})
    @ValidateString(message = "{userDto.lastname.ValidateString}", value = StringType.LASTNAME, groups = {OnCreate.class, OnPatch.class, OnPut.class})
    private String lastname;

    @NotBlank(message = "{userDto.email.NotBlank}", groups = {OnCreate.class, OnPut.class})
    @Email(message = "{userDto.email.Email}", groups = {OnCreate.class, OnPatch.class, OnPut.class})
    private String email;

    @NotNull(message = "{userDto.birthdate.NotNull}", groups = {OnCreate.class, OnPut.class})
    @ValidateDate(message = "{userDto.birthdate.ValidateDate}", value = DateType.BIRTHDATE, groups = {OnCreate.class, OnPatch.class, OnPut.class})
    private LocalDate birthdate;

    @ValidateString(message = "{userDto.address.ValidateString}", value = StringType.ADDRESS, groups = {OnCreate.class, OnPatch.class, OnPut.class})
    private String address;

    @ValidateString(message = "{userDto.number.ValidateString}", value = StringType.NUMBER, groups = {OnCreate.class, OnPatch.class, OnPut.class})
    private String number;
}
