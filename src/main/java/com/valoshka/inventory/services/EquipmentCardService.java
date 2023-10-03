package com.valoshka.inventory.services;

import com.valoshka.inventory.models.EquipmentCard;
import com.valoshka.inventory.models.Waybill;
import com.valoshka.inventory.repositories.EquipmentCardRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class EquipmentCardService {

    private final EquipmentCardRepository equipmentCardRepository;

    public List<EquipmentCard> getAll() {
        return equipmentCardRepository.findAll();
    }

    public Optional<EquipmentCard> getById(int id) {
        return equipmentCardRepository.findById(id);
    }

    @Transactional
    public void save(EquipmentCard equipmentCard) {
        equipmentCardRepository.save(equipmentCard);
    }

    @Transactional
    public void update(int id, EquipmentCard updatedEquipmentCard) {
        updatedEquipmentCard.setId(id);
        equipmentCardRepository.save(updatedEquipmentCard);
    }

    @Transactional
    public void delete(int id) {
        equipmentCardRepository.deleteById(id);
    }
}
