package com.valoshka.inventory.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "storage")
@Data
@NoArgsConstructor
public class Storage {

    @Id
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "storage")
    private List<Waybill> waybillList;

    public void addWaybillToStorage(Waybill waybill) {
        if (waybillList == null) {
            waybillList = new ArrayList<>();
        }
        waybillList.add(waybill);
        waybill.setStorage(this);
    }


}
