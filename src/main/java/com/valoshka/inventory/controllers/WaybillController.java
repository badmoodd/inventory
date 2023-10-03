package com.valoshka.inventory.controllers;


import com.valoshka.inventory.services.WaybillService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/storages")
public class WaybillController {

    private final WaybillService waybillService;


}
