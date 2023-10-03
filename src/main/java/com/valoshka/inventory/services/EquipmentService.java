package com.valoshka.inventory.services;

import com.valoshka.inventory.models.Equipment;
import com.valoshka.inventory.repositories.EquipmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public List<Equipment> getAll() {
        return equipmentRepository.findAll();
    }

    public Optional<Equipment> getById(int id) {
        return equipmentRepository.findById(id);
    }

    @Transactional
    public void save(Equipment equipment) {
        equipmentRepository.save(equipment);
    }

    @Transactional
    public void update(int id, Equipment updatedEquipment) {
        updatedEquipment.setId(id);
        equipmentRepository.save(updatedEquipment);
    }

    @Transactional
    public void delete(int id) {
        equipmentRepository.deleteById(id);
    }
}
