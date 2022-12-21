package com.milton.entity.mapper;

import com.milton.entity.User;
import com.milton.entity.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "loginname", target = "name")
    UserDTO userToUserDTO(User user);
}
