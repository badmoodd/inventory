package com.valoshka.inventory.services;

import com.valoshka.inventory.models.EquipmentCard;
import com.valoshka.inventory.models.Storage;
import com.valoshka.inventory.models.Waybill;
import com.valoshka.inventory.repositories.StorageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@AllArgsConstructor
public class StorageService {

    private final StorageRepository storageRepository;
    private final WaybillService waybillService;

    public List<Storage> getAll() {
        return storageRepository.findAll();
    }

    public Optional<Storage> getById(int id) {
        return storageRepository.findById(id);
    }

    @Transactional
    public void save(Storage storage) {
        storageRepository.save(storage);
    }

    /**
     * @param id             is primaryKey at which will be stored updated storage
     * @param updatedStorage new object to store at specified index
     */
    @Transactional
    public void update(int id, Storage updatedStorage) {
        updatedStorage.setId(id);
        storageRepository.save(updatedStorage);
    }

    @Transactional
    public void delete(int id) {
        try {
            Storage storageToDelete = storageRepository.findById(id).orElseThrow();
            List<Waybill> linkedWaybills = storageToDelete.getWaybillList();

            if (!linkedWaybills.isEmpty()) {
                for (Waybill waybillToDelete : linkedWaybills) {
                    waybillService.delete(waybillToDelete.getId());
                }
            }
            storageRepository.deleteById(id);

        } catch (NoSuchElementException ex) {
            log.info("Attempt to delete no exist storage");
        }

        storageRepository.deleteById(id);
    }

}
