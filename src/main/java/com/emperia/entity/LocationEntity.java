package com.emperia.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="location")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LocationEntity extends BaseEntity {
    private double latitude;
    private double longitude;

}
