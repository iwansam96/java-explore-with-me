package ru.practicum.mainservice.controller.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.dto.UserDto;
import ru.practicum.mainservice.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
public class UserAdminController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> get(@RequestParam List<Long> users,
                             @RequestParam(defaultValue = "0") Integer from,
                             @RequestParam(defaultValue = "10") Integer size) {
        log.info("GET all users");
        return userService.get(users, from, size);
    }

    @PostMapping
    public UserDto save(@Valid @NotNull @RequestBody UserDto user) {
        log.info("POST user");
        return userService.save(user);
    }

    @DeleteMapping("/{userId}")
    public void delete(@Valid @NotNull @PathVariable Long userId) {
        log.info("DELETE user {}", userId);
        userService.delete(userId);
    }
}
