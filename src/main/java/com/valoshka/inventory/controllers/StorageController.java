package com.valoshka.inventory.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/storage")
public class StorageController {

    @GetMapping
    public String dm(){
        return "k";
    }


}
