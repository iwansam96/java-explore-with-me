package ru.practicum.mainservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.mainservice.dto.UserDto;
import ru.practicum.mainservice.dto.mapper.UserMapper;
import ru.practicum.mainservice.exception.DublicateNameException;
import ru.practicum.mainservice.exception.EntityNotFoundException;
import ru.practicum.mainservice.exception.IncorrectDataException;
import ru.practicum.mainservice.models.User;
import ru.practicum.mainservice.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDto> get(List<Long> users, Integer from, Integer size) {
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size);

        if (users != null)
            return userRepository.findAllByIdIn(pageRequest, users).stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());

        return userRepository.findAll(pageRequest).toList().stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto save(UserDto userDto) {
        if (userDto == null || userDto.getName() == null || userDto.getName().isBlank() ||
                userDto.getEmail() == null || userDto.getEmail().isBlank())
            throw new IncorrectDataException("incorrect user data");
        User oldUser = userRepository.findUserByNameLike(userDto.getName());
        if (oldUser != null)
            throw new DublicateNameException("incorrect user data");

        User user = UserMapper.toUser(userDto);
        User newUser = userRepository.save(user);
        return UserMapper.toUserDto(newUser);
    }

    @Transactional
    public void delete(Long id) {
        User userToDelete = userRepository.findById(id).orElse(null);
        if (userToDelete == null)
            throw new EntityNotFoundException("user " + id + " not found");

        userRepository.delete(userToDelete);
    }
}
