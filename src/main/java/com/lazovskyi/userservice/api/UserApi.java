package com.lazovskyi.userservice.api;

import com.lazovskyi.userservice.controller.model.UserModel;
import com.lazovskyi.userservice.dto.UserDto;
import com.lazovskyi.userservice.dto.group.OnCreate;
import com.lazovskyi.userservice.dto.group.OnPatch;
import com.lazovskyi.userservice.dto.group.OnPut;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

@Api(tags = "User management API")
@RequestMapping("/api/v1/users")
@Validated
public interface UserApi {

    @ApiOperation("Create user entity")
    @PostMapping
    ResponseEntity<UserModel> create(@RequestBody @Validated(OnCreate.class) UserDto userDto);

    @ApiOperation("Get user entity by id")
    @GetMapping("/{userId}")
    ResponseEntity<UserModel> getById(@PathVariable @Positive Long userId);

    @ApiOperation("Patch user entity")
    @PatchMapping
    ResponseEntity<UserModel> patchUpdate(@RequestBody @Validated(OnPatch.class) UserDto userDto);

    @ApiOperation("Put user entity")
    @PutMapping
    ResponseEntity<UserModel> putUpdate(@RequestBody @Validated(OnPut.class) UserDto userDto);

    @ApiOperation("Delete use entity by user id")
    @DeleteMapping("/{userId}")
    ResponseEntity<Void> delete(@PathVariable @Positive Long userId);

    @ApiOperation("Get users by birthdate range")
    @ApiImplicitParams({@ApiImplicitParam(name = "start-date", paramType = "query", required = true, value = "Start date of the range"),
            @ApiImplicitParam(name = "end-date", paramType = "query", required = true, value = "End date of the range")})
    @GetMapping("/by-date-range")
    ResponseEntity<List<UserModel>> getUsersByBirthDateRange(
            @RequestParam("start-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate);
}