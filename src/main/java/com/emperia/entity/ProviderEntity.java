package com.emperia.entity;
import lombok.*;
//import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="provider")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProviderEntity extends BaseEntity{
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "provider")
    private List<ProviderServiceEntity> services;


}
