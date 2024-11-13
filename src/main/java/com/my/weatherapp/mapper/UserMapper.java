package com.my.weatherapp.mapper;

import static org.mapstruct.NullValueMappingStrategy.RETURN_DEFAULT;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import com.my.weatherapp.dto.UserDto;
import com.my.weatherapp.dto.UserResponseDto;
import com.my.weatherapp.repository.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE,
    nullValueIterableMappingStrategy = RETURN_DEFAULT, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "weatherProfiles", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  User toEntity(UserDto userDto);

  @Mapping(target = "profileDtoList", ignore = true)
  UserResponseDto toResponseDto(User user);
}
