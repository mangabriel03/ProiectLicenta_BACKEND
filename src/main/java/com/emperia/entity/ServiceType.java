package com.emperia.entity;
import lombok.*;
//import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.*;

@Entity
@Table(name="service_type")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ServiceType extends BaseEntity{
    private String type;
    private String priceType;
}
