package com.emperia.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class HomeLocationDto extends LocationDto
{
    private Long idHome;

    public HomeLocationDto(long idHome, double latitude, double longitude)
    {
        super(latitude, longitude);
        this.idHome = idHome;
    }

}
