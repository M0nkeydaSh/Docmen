package ru.imsit.diplom.docmen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.UserDto;
import ru.imsit.diplom.docmen.dto.UserInfoDto;
import ru.imsit.diplom.docmen.entity.Authority;
import ru.imsit.diplom.docmen.entity.User;
import ru.imsit.diplom.docmen.filtr.UserFilter;
import ru.imsit.diplom.docmen.helper.UserInfoHelper;
import ru.imsit.diplom.docmen.mapper.UserInfoMapper;
import ru.imsit.diplom.docmen.mapper.UserMapper;
import ru.imsit.diplom.docmen.repository.AuthorityRepository;
import ru.imsit.diplom.docmen.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserInfoHelper userInfoHelper;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final UserInfoMapper userInfoMapper;

    private final UserRepository userRepository;

    public Page<UserDto> getAll(UserFilter filter, Pageable pageable) {
        Specification<User> spec = filter.toSpecification();
        Page<User> users = userRepository.findAll(spec, pageable);
        return users.map(userMapper::toUserDto);
    }

    public UserInfoDto getOne(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        return userInfoMapper.toUserInfoDto(userOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public UserDto create(String username, String password, Set<String> authority) {
        var user = new User();
        Set<Authority> roles = new HashSet<>();
        for (var i : authority) {
            var role = authorityRepository.findByName(i);
            roles.add(role);
        }
        user = User.builder().username(username).password(passwordEncoder.encode(password)).enabled(true).authorities(roles).build();
        return userMapper.toUserDto(userRepository.save(user));
    }

    public UserDto patch(String username, boolean enabled, Set<String> authorities) {
        var user = userRepository.findByUsername(username);
        Set<Authority> roles = new HashSet<>();
        for (var i : authorities) {
            var role = authorityRepository.findByName(i);
            roles.add(role);
        }

        user.ifPresent(u -> {
            u.setEnabled(enabled);
            u.setAuthorities(roles);
        });
        return userMapper.toUserDto(userRepository.save(user.orElseThrow()));
    }

    public UserDto patchDeactivate(String username, boolean enabled) {
        var user = userRepository.findByUsername(username);
        user.ifPresent(u -> u.setEnabled(enabled));
        return userMapper.toUserDto(userRepository.save(user.orElseThrow()));
    }

    public void patchPassword(String username, String password) {
        var user = userRepository.findByUsername(username);
        var autorizationUser = userInfoHelper.getUser();
        if (autorizationUser.getUsername().equals(username) || autorizationUser.getAuthorities().stream().anyMatch(i -> i.getName().equals("ADMIN"))) {
            user.ifPresent(u -> u.setPassword(passwordEncoder.encode(password)));
            userRepository.save(user.orElseThrow());
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

}

