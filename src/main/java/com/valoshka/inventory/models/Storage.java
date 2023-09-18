package com.valoshka.inventory.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "storage")
@Data
@NoArgsConstructor
public class Storage {

    @Id
    @Column(name = "id")
    int id;

    @NonNull
    @Column(name = "name")
    String name;

    @NonNull
    @Column(name = "phone_number")
    String phone_number;


}
