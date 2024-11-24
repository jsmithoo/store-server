package com.example.adapters.inbound.controller.mapper;
import com.example.adapters.outbound.repository.entities.UserEntity;
import java.util.List;
import java.util.stream.Collectors;
import lombok.experimental.UtilityClass;
import org.openapitools.model.UserEntityDto;

@UtilityClass
public class UserMapper {

    public UserEntityDto toDto(UserEntity userEntity) {
        return new UserEntityDto()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .username(userEntity.getUsername());                
    }

    public UserEntity toDomain(UserEntityDto userDto) {
        return UserEntity.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .build();
    }

   /* public UserEntity toDomain(UserParamsDto userParamsDto) {
        return UserEntity.builder()
                .name(userParamsDto.getName())
                .username(userParamsDto.getUsername())
                .password(userParamsDto.getPassword())
                .email(userParamsDto.getEmail())
                .address(userParamsDto.getAddress())
                .phone(userParamsDto.getPhone())
                .type(userParamsDto.getType())
                .build();
    }*/

    public List<UserEntityDto> map(List<UserEntity> userEntities) {
        return userEntities.stream().map(UserMapper::toDto).collect(Collectors.toList());
    }
}
