package com.valoshka.inventory.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/home")
public class HomeController {

    @GetMapping
    public String index() {
        return "home/index";
    }
}
