package com.emperia.entity;

import com.emperia.security.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="home")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HomeEntity extends BaseEntity
{
    private String name;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private LocationEntity location;

    @ManyToOne
    private User user;

    @ManyToMany
    private List<ProviderServiceEntity> services;
}
