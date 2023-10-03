package com.valoshka.inventory.repositories;

import com.valoshka.inventory.models.EquipmentCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EquipmentCardRepository extends JpaRepository<EquipmentCard, Integer> {

    Optional<EquipmentCard> findById(int primaryKey);
}
