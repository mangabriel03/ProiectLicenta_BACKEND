package com.emperia.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="bill_status")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BillStatusEntity extends BaseEntity{
    private String status;
}
