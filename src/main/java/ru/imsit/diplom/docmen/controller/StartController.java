package ru.imsit.diplom.docmen.controller;


import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class StartController {

    @GetMapping()
    @Operation(summary = "Страница старта", description = "В ответе возвращается ссылка на swagger")
    public String index() {
        return "<a href=http://localhost:8080/swagger-ui/index.html#/>swagger</a>";
    }

}
