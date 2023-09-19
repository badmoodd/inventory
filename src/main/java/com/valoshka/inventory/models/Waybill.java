package com.valoshka.inventory.models;

import com.valoshka.inventory.models.enums.WaybillType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "waybill")
@Data
@NoArgsConstructor
public class Waybill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "name")
    private WaybillType name;

    @NonNull
    @Column(name = "employee_name")
    private String employeeName;

    @NonNull
    @Column(name = "employee_position")
    private String employeePosition;

    @NonNull
    @Column(name = "date")
    private LocalDateTime dateTime;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @ManyToMany
    @JoinTable(
            name = "equipment_card",
            joinColumns = @JoinColumn(name = "waybill_id"),
            inverseJoinColumns = @JoinColumn(name = "equip_id")
    )
    private List<Equipment> equipmentList;


}
