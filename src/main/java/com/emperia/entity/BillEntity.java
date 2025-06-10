package com.emperia.entity;
import lombok.*;
//import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="bill")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BillEntity extends BaseEntity{
    private float sum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "home_id")
    private HomeEntity home;

    @OneToOne
    private ProviderServiceEntity providerService;
    @Temporal(TemporalType.TIMESTAMP)
    private Date issueDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deadline;
    @OneToOne
    private BillStatusEntity status;


}
