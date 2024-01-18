package com.valoshka.inventory.repositories;

import com.valoshka.inventory.models.Waybill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface WaybillRepository extends JpaRepository<Waybill, Integer> {
    Optional<Waybill> findById(int primaryKey);

    @Query(value = """
            SELECT ev.waybills_date,
                   ev.waybill_name,
                   ev.equip_count,
                   st.name AS storage_name
            FROM equipment_view ev
                     JOIN storages st ON ev.storage_id = st.id
            WHERE ev.equipment_name = :equipment_name
              AND ev.waybills_date <= :date
            GROUP BY ev.waybills_date, ev.waybill_name, ev.equip_count, st.name;""",
            nativeQuery = true)
    List<Map<String, Object>> findWaybillByEquipmentAndByDate(@Param("equipment_name") String equipmentName,
                                                              @Param("date") LocalDate date);
}
