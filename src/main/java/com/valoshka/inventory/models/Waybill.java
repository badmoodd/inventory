package com.valoshka.inventory.models;

import com.valoshka.inventory.models.enums.WaybillType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "waybill")
@Data
@NoArgsConstructor
public class Waybill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @NonNull
    @Column(name = "name")
    WaybillType name;

    @NonNull
    @Column(name = "employee_name")
    String employeeName;

    @NonNull
    @Column(name = "employee_position")
    String employeePosition;

    @NonNull
    @Column(name = "date")
    LocalDateTime dateTime;

    int storageId;




}
