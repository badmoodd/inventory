package com.valoshka.inventory.controllers;

import com.valoshka.inventory.InventoryApplication;
import com.valoshka.inventory.models.Equipment;
import com.valoshka.inventory.models.EquipmentCard;
import com.valoshka.inventory.models.Storage;
import com.valoshka.inventory.models.Waybill;
import com.valoshka.inventory.models.compositeKey.EquipmentCardKey;
import com.valoshka.inventory.models.enums.EquipmentType;
import com.valoshka.inventory.models.enums.WaybillType;
import com.valoshka.inventory.services.EquipmentCardService;
import com.valoshka.inventory.services.EquipmentService;
import com.valoshka.inventory.services.StorageService;
import com.valoshka.inventory.services.WaybillService;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
@RequestMapping("/storages")
public class StorageController {

    private final StorageService storageService;
    private final WaybillService waybillService;
    private final EquipmentCardService equipmentCardService;
    private final EquipmentService equipmentService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("storages", storageService.getAll());
        /*Storage storage = new Storage();
        storage.setId(99);
        storage.setName("Storage1");
        storage.setPhoneNumber("8800555");

//
        Waybill waybill = new Waybill();
        waybill.setName(WaybillType.DELIVERY);
        waybill.setEmployeeName("Vasya");
        waybill.setEmployeePosition("Clown");
        waybill.setDateTime(LocalDateTime.now());

        storage.addWaybillToStorage(waybill);*/



//        Equipment equipment1 = equipmentService.getById(3).orElseThrow();
//        Waybill waybill1 = waybillService.getById(1).orElseThrow();
//
//
//        EquipmentCard equipmentCard = new EquipmentCard(20, equipment1, waybill1);


        EquipmentCard eq = equipmentCardService.getByCompositeKey(3,1).orElseThrow();
        System.out.println();
//        equipmentCardService.save(equipmentCard);

        System.out.println();




        return "gh";
    }

    @GetMapping("/new")
    public String newStorage(Model model){
        model.addAttribute("storage", new Storage());
        return "new";
    }
    @PostMapping
    public String create(@ModelAttribute Storage storage) {
        storageService.save(storage);
        return "redirect:/storages";
    }




}
