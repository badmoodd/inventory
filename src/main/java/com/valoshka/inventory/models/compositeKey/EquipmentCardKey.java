package com.valoshka.inventory.models.compositeKey;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EquipmentCardKey implements Serializable {

    @Column(name = "equip_id")
    private int equipmentId;

    @Column(name = "waybill_id")
    private int waybillId;

}
