package com.emperia.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public abstract class BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
