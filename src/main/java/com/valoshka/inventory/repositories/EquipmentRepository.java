package com.valoshka.inventory.repositories;

import com.valoshka.inventory.models.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {

    Optional<Equipment> findById(int primaryKey);
}
