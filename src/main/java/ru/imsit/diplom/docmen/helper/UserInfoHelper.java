package ru.imsit.diplom.docmen.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.imsit.diplom.docmen.entity.User;
import ru.imsit.diplom.docmen.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserInfoHelper {

    private final UserRepository userRepository;

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            return userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("User is not found"));
        }
        throw  new RuntimeException("User is not logged in");
    }

}
