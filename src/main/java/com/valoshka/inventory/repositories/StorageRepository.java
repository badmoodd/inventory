package com.valoshka.inventory.repositories;

import com.valoshka.inventory.models.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Integer> {

    Optional<Storage> findById(int primaryKey);
    List<Storage> findAllByOrderByNameAsc();

//    List<Object>
}
