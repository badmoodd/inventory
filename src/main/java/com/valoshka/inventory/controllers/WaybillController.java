package com.valoshka.inventory.controllers;

import com.valoshka.inventory.services.WaybillService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
@AllArgsConstructor
@RequestMapping("/waybills")
public class WaybillController {

    private final WaybillService waybillService;

    @GetMapping()
    public String showWaybillEquipmentOnDate(@RequestParam(value = "name") String equipmentName,
                                             @RequestParam(value = "date") String dateStr,
                                             Model model) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dateStr, formatter);

        model.addAttribute("waybillEquipmentRecordList", waybillService.findWaybillByEquipmentAndByDate(equipmentName, date));
        model.addAttribute("dateToFilter", date);
        model.addAttribute("equipmentName", equipmentName);
        return "waybills/waybillEquipmentRecords";

    }
}
