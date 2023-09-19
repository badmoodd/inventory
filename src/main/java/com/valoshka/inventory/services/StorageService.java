package com.valoshka.inventory.services;

import com.valoshka.inventory.models.Storage;
import com.valoshka.inventory.repositories.StorageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class StorageService {

    private final StorageRepository storageRepository;

    public List<Storage> getAll() {
        return storageRepository.findAll();
    }

    @Transactional
    public void saveStorage(Storage storage) {
        storageRepository.save(storage);
    }

}
