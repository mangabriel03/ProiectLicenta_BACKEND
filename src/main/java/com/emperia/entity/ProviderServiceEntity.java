package com.emperia.entity;
import lombok.*;
//import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.*;

@Entity
@Table(name="service")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString


public class ProviderServiceEntity extends BaseEntity{
    //tipul de serviciu
    @ManyToOne
    private ServiceType serviceType;
    private float price;
   // private List<HouseEntity>
    @ManyToOne
    @JoinColumn(name = "provider_id")
    private ProviderEntity provider;
}
