package com.valoshka.inventory.controllers;

import com.valoshka.inventory.models.Equipment;
import com.valoshka.inventory.models.EquipmentCard;
import com.valoshka.inventory.models.Storage;
import com.valoshka.inventory.models.Waybill;
import com.valoshka.inventory.models.compositeKey.EquipmentCardKey;
import com.valoshka.inventory.services.EquipmentCardService;
import com.valoshka.inventory.services.EquipmentService;
import com.valoshka.inventory.services.StorageService;
import com.valoshka.inventory.services.WaybillService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Controller
@AllArgsConstructor
@RequestMapping("/equipment")
public class WorkEquipmentController {

    EquipmentCardService equipmentCardService;
    StorageService storageService;
    WaybillService waybillService;
    EquipmentService equipmentService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("equipment_cards", equipmentCardService.getAll());
        return "equipment_records/index";
    }

    @GetMapping("/new")
    public String newEquipmentInfo(Model model) {
        model.addAttribute("equipment_card", new EquipmentCard());
        return "equipment_records/new";
    }

    @PostMapping
    public String create(@ModelAttribute EquipmentCard equipmentCard) {
        try {
            var storageToSet = storageService
                    .getById(equipmentCard.getWaybill().getStorage().getId())
                    .orElseThrow();
            equipmentCard
                    .getWaybill()
                    .setDateTime(LocalDateTime.now());
            equipmentCard
                    .getWaybill()
                    .setStorage(storageToSet);
            equipmentCard.
                    setId(equipmentCard.getWaybill().getId(), equipmentCard.getEquipment().getId());
            equipmentCardService.save(equipmentCard);
        } catch (NoSuchElementException e) {
            return "waybills/exceptions/noSuchStorage";
        }
        return "redirect:/equipment";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editEquipment(@RequestParam("equipmentId") int equipmentId,
                                @RequestParam("waybillId") int waybillId,
                                Model model) {
        try {
            var cardToEdit = equipmentCardService
                    .getByCompositeKey(new EquipmentCardKey(equipmentId, waybillId))
                    .orElseThrow();
            model.addAttribute("card_to_edit", cardToEdit);
            return "equipment_records/edit";

        } catch (NoSuchElementException e) {
            return "redirect:/equipment";
        }
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public String update(@RequestParam("equipmentId") int equipmentId,
                         @RequestParam("waybillId") int waybillId,
                         @ModelAttribute("card_to_edit") EquipmentCard editedCard) {

        Equipment equipmentToUpdate = equipmentService
                .getById(equipmentId)
                .orElseThrow();
        equipmentToUpdate.setName(editedCard.getEquipment().getName());

        Storage storageToAdd = storageService.
                getById(editedCard.getWaybill().getStorage().getId())
                .orElseThrow();


        Waybill waybillToUpdate = waybillService
                .getById(waybillId)
                .orElseThrow();
        waybillToUpdate.setName(editedCard.getWaybill().getName());
        waybillToUpdate.setEmployeeName(editedCard.getWaybill().getEmployeeName());
        waybillToUpdate.setEmployeePosition(editedCard.getWaybill().getEmployeePosition());
        waybillToUpdate.setDateTime(LocalDateTime.now());
        waybillToUpdate.setStorage(storageToAdd);

        EquipmentCard cardToUpdate = equipmentCardService
                .getByCompositeKey(new EquipmentCardKey(equipmentId, waybillId))
                .orElseThrow();
        cardToUpdate.setEquipment(equipmentToUpdate);
        cardToUpdate.setWaybill(waybillToUpdate);
        cardToUpdate.setEquipCount(editedCard.getEquipCount());

        equipmentCardService.update(new EquipmentCardKey(equipmentId, waybillId), cardToUpdate);
        return "redirect:/equipment";
    }


    @RequestMapping(method = RequestMethod.DELETE)
    public String delete(@RequestParam("equipmentId") int equipmentId,
                         @RequestParam("waybillId") int waybillId) {
        try {
            EquipmentCard cardToDelete = equipmentCardService
                    .getByCompositeKey(new EquipmentCardKey(equipmentId, waybillId))
                    .orElseThrow();
            Waybill waybillToDelete = cardToDelete.getWaybill();

            equipmentCardService.delete(cardToDelete.getId());
            waybillService.delete(waybillToDelete.getId());

        } catch (NoSuchElementException e) {
            System.out.println(e);
        }
        return "redirect:/equipment";
    }


}
