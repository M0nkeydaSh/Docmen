package ru.imsit.diplom.docmen.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UploadFileController {

    @GetMapping("/index")
    public String fileUploadPage() {
        return "uploader";
    }
}
