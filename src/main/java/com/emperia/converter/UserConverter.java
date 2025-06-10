package com.emperia.converter;

import com.emperia.dto.LocationDto;
import com.emperia.dto.UserDto;
import com.emperia.entity.LocationEntity;
import com.emperia.security.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());

        return userDto;
    }

}
