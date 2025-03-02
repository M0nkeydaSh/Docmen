package ru.imsit.diplom.docmen.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.UserDto;
import ru.imsit.diplom.docmen.entity.User;
import ru.imsit.diplom.docmen.filtr.UserFilter;
import ru.imsit.diplom.docmen.mapper.UserMapper;
import ru.imsit.diplom.docmen.repository.AuthorityRepository;
import ru.imsit.diplom.docmen.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    public Page<UserDto> getAll(UserFilter filter, Pageable pageable) {
        Specification<User> spec = filter.toSpecification();
        Page<User> users = userRepository.findAll(spec, pageable);
        return users.map(userMapper::toUserDto);
    }

    public UserDto getOne(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userMapper.toUserDto(userOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(username))));
    }

    public List<UserDto> getMany(List<UUID> ids) {
        List<User> users = userRepository.findAllById(ids);
        return users.stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    public UserDto create(String username, String password, String authority) {
        var role = authorityRepository.findByName(authority);
        var user = User.builder().username(username).password(passwordEncoder.encode(password)).enabled(true).authorities(Set.of(role)).build();
        return userMapper.toUserDto(userRepository.save(user));
    }

    public UserDto patch(String username, String password, boolean enabled) {
        var user = userRepository.findByUsername(username);
        user.ifPresent(u -> { u.setEnabled(enabled); u.setPassword(passwordEncoder.encode(password));});
        return userMapper.toUserDto(userRepository.save(user.orElseThrow()));
    }

}
