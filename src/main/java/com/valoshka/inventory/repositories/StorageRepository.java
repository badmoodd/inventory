package com.valoshka.inventory.repositories;

import com.valoshka.inventory.models.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Integer> {

    Optional<Storage> findById(int primaryKey);

    @Query(value = "SELECT st.id, st.name, st.phone_number FROM storages st JOIN waybills wb ON st.id = wb.storage_id WHERE wb.date <= :date ORDER BY st.name",
            nativeQuery = true)
    List<Storage> findAllByOrderByNameAscLessDate(@Param("date") LocalDate date);

    @Query(value = """
           SELECT * FROM calculate_remaining_inventory(:equipment_name, :storage_id, :date )""",
            nativeQuery = true)
    Map<String, Object> findEquipmentAndItsCountOnDate(@Param("storage_id") int storageId,
                                                       @Param("equipment_name") String equipmentName,
                                                       @Param("date") LocalDate date);

}
