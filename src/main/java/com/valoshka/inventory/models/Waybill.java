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

    @Enumerated(EnumType.STRING)
    private WaybillType name;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_position")
    private String employeePosition;

    @Column(name = "date")
    private LocalDateTime dateTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "storage_id")
    private Storage storage;

    @OneToMany(mappedBy = "waybill")
    List<EquipmentCard> equipmentCardList;

    public Waybill(@NonNull WaybillType name,
                   @NonNull String employeeName,
                   @NonNull String employeePosition) {

        this.name = name;
        this.employeeName = employeeName;
        this.employeePosition = employeePosition;
        this.dateTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Waybill{" +
                "id=" + id +
                ", name=" + name +
                ", employeeName='" + employeeName + '\'' +
                ", employeePosition='" + employeePosition + '\'' +
                ", dateTime=" + dateTime +
                ", storage=" + storage.getId() +
                '}';
    }
}
