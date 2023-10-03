package com.valoshka.inventory.repositories;

import com.valoshka.inventory.models.Waybill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WaybillRepository extends JpaRepository<Waybill, Integer> {
    Optional<Waybill> findById(int primaryKey);
}
