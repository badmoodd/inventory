package com.valoshka.inventory.services;

import com.valoshka.inventory.models.Storage;
import com.valoshka.inventory.repositories.StorageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class StorageService {

    private final StorageRepository storageRepository;

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
     *
     * @param id is primaryKey at which will be stored updated storage
     * @param updatedStorage new object to store at specified index
     */
    @Transactional
    public void update(int id, Storage updatedStorage) {
        updatedStorage.setId(id);
        storageRepository.save(updatedStorage);
    }

    @Transactional
    public void delete(int id) {
        storageRepository.deleteById(id);
    }

}
