package com.valoshka.inventory.services;

import com.valoshka.inventory.models.Waybill;
import com.valoshka.inventory.repositories.WaybillRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@AllArgsConstructor
public class WaybillService {

    private final WaybillRepository waybillRepository;

    public List<Waybill> getAll() {
        return waybillRepository.findAll();
    }

    public Optional<Waybill> getById(int id) {
        return waybillRepository.findById(id);
    }

    @Transactional
    public void save(Waybill waybill) {
        waybillRepository.save(waybill);
    }

    @Transactional
    public void update(int id, Waybill updatedWaybill) {
        updatedWaybill.setId(id);
        waybillRepository.save(updatedWaybill);
    }

    @Transactional
    public void delete(int id) {
        Optional<Waybill> waybillOpt = waybillRepository.findById(id);

        if (waybillOpt.isPresent()) {
            waybillRepository.deleteById(id);
        } else {
            log.info("Attempt to delete no exist waybill");
        }
    }


}
