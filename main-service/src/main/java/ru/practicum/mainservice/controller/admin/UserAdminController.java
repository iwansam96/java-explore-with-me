package ru.practicum.mainservice.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.UserDto;
import ru.practicum.mainservice.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserAdminController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> get(@RequestParam(required = false) List<Long> ids,
                             @Min(0) @RequestParam(defaultValue = "0") Integer from,
                             @Min(0) @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET all users");
        return userService.get(ids, from, size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto save(@Valid @NotNull @RequestBody UserDto user) {
        log.info("POST user");
        return userService.save(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void delete(@Min(0) @NotNull @PathVariable Long userId) {
        log.info("DELETE user {}", userId);
        userService.delete(userId);
    }
}
