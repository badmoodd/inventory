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

    public Optional<EquipmentCard> getByCompositeKey(int equipmentId, int waybillId) {
        return equipmentCardRepository.findById(new EquipmentCardKey(equipmentId, waybillId));
    }

    @Transactional
    public void save(EquipmentCard equipmentCard) {
        equipmentCardRepository.save(equipmentCard);
    }

    @Transactional
    public void update(int equipmentId, int waybillId, @NonNull EquipmentCard updatedEquipmentCard) {
        updatedEquipmentCard.setId(new EquipmentCardKey(equipmentId, waybillId));
        equipmentCardRepository.save(updatedEquipmentCard);
    }

    @Transactional
    public void delete(int equipmentId, int waybillId) {
        equipmentCardRepository.deleteById(new EquipmentCardKey(equipmentId, waybillId));
    }
}
