package com.valoshka.inventory.repositories;

import com.valoshka.inventory.models.EquipmentCard;
import com.valoshka.inventory.models.compositeKey.EquipmentCardKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipmentCardRepository extends JpaRepository<EquipmentCard, EquipmentCardKey> {

}
