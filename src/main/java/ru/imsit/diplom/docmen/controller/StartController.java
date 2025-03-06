package ru.imsit.diplom.docmen.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class StartController {

    @GetMapping()
    public String index() {
        return "<a href=http://localhost:8080/swagger-ui/index.html#/>swagger</a>";
    }

}
