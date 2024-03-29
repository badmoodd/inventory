package com.valoshka.inventory.services;

import com.valoshka.inventory.models.Storage;
import com.valoshka.inventory.repositories.StorageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@Transactional(readOnly = true)
@AllArgsConstructor
public class StorageService {

    private final StorageRepository storageRepository;

    public List<Storage> getAll() {
        return storageRepository.findAll();
    }

    public List<Storage> getAllSortedByNameLessWaybillDate(LocalDate date) {
        return storageRepository.findAllByOrderByNameAscLessDate(date);
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
        Optional<Storage> storageOpt = storageRepository.findById(id);

        if (storageOpt.isPresent()) {
            storageRepository.deleteById(id);
        } else {
            log.info("Attempt to delete no exist storage");
        }
    }

    public Map<String, Object> findEquipmentAndItsCountOnDate(int storageId, String equipmentName, LocalDate date) {
        return storageRepository.findEquipmentAndItsCountOnDate(storageId, equipmentName, date);
    }

}
