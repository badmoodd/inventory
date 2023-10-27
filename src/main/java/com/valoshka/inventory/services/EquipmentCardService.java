package com.valoshka.inventory.services;

import com.valoshka.inventory.models.EquipmentCard;
import com.valoshka.inventory.models.compositeKey.EquipmentCardKey;
import com.valoshka.inventory.repositories.EquipmentCardRepository;
import lombok.AllArgsConstructor;
import lombok.NonNull;
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

    public Optional<EquipmentCard> getByCompositeKey(EquipmentCardKey compositeKey) {
        return equipmentCardRepository.findById(compositeKey);
    }

    @Transactional
    public void save(EquipmentCard equipmentCard) {
        equipmentCardRepository.save(equipmentCard);
    }

    @Transactional
    public void update(EquipmentCardKey compositeKey, @NonNull EquipmentCard updatedEquipmentCard) {
        updatedEquipmentCard.setId(compositeKey);
        equipmentCardRepository.save(updatedEquipmentCard);
    }

    @Transactional
    public void delete(EquipmentCardKey compositeKey) {
        equipmentCardRepository.deleteById(compositeKey);
    }
}
