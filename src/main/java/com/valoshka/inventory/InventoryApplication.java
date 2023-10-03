package com.valoshka.inventory;

import com.valoshka.inventory.models.Equipment;
import com.valoshka.inventory.models.EquipmentCard;
import com.valoshka.inventory.models.Storage;
import com.valoshka.inventory.models.Waybill;
import com.valoshka.inventory.models.enums.EquipmentType;
import com.valoshka.inventory.models.enums.WaybillType;
import com.valoshka.inventory.services.WaybillService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

@SpringBootApplication
public class InventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}

}
