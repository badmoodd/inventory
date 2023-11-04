package com.valoshka.inventory.services;

import com.valoshka.inventory.models.Equipment;
import com.valoshka.inventory.models.EquipmentCard;
import com.valoshka.inventory.models.Storage;
import com.valoshka.inventory.models.Waybill;
import com.valoshka.inventory.models.compositeKey.EquipmentCardKey;
import com.valoshka.inventory.repositories.EquipmentCardRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class EquipmentCardService {

    private final StorageService storageService;
    private final WaybillService waybillService;
    private final EquipmentService equipmentService;

    private final EquipmentCardRepository equipmentCardRepository;

    public List<EquipmentCard> getAll() {
        return equipmentCardRepository.findAll();
    }

    public Optional<EquipmentCard> getByCompositeKey(EquipmentCardKey compositeKey) {
        return equipmentCardRepository.findById(compositeKey);
    }

    @Transactional
    public void save(EquipmentCard equipmentCard) {
        equipmentCardRepository.save(equipmentCard);
    }

    @Transactional
    public void update(EquipmentCardKey compositeKey, @NonNull EquipmentCard updatedEquipmentCard) {

        Equipment equipmentToUpdate = equipmentService
                .getById(compositeKey.getEquipmentId())
                .orElseThrow();
        equipmentToUpdate.setName(updatedEquipmentCard.getEquipment().getName());

        Storage storageToAdd = storageService
                .getById(updatedEquipmentCard.getWaybill().getStorage().getId())
                .orElseThrow();

        Waybill waybillToUpdate = waybillService
                .getById(compositeKey.getWaybillId())
                .orElseThrow();

        waybillToUpdate.setName(updatedEquipmentCard.getWaybill().getName());
        waybillToUpdate.setEmployeeName(updatedEquipmentCard.getWaybill().getEmployeeName());
        waybillToUpdate.setEmployeePosition(updatedEquipmentCard.getWaybill().getEmployeePosition());
        waybillToUpdate.setDateTime(LocalDateTime.now());
        waybillToUpdate.setStorage(storageToAdd);

        EquipmentCard cardToUpdate = getByCompositeKey(compositeKey).orElseThrow();
        cardToUpdate.setEquipment(equipmentToUpdate);
        cardToUpdate.setWaybill(waybillToUpdate);
        cardToUpdate.setEquipCount(updatedEquipmentCard.getEquipCount());

        equipmentCardRepository.save(cardToUpdate);
    }

    @Transactional
    public void delete(EquipmentCardKey compositeKey) {
        equipmentCardRepository.deleteById(compositeKey);
    }
}
