package com.my.weatherapp.processor;

import com.my.weatherapp.dto.UserDto;
import com.my.weatherapp.dto.UserResponseDto;
import com.my.weatherapp.exception.InvalidDataException;
import com.my.weatherapp.mapper.UserMapper;
import com.my.weatherapp.repository.UserRepository;
import com.my.weatherapp.repository.entity.User;
import com.my.weatherapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserProcessor implements UserService {

  private final UserMapper dtoToEntityMapper;
  private final UserRepository userRepository;

  @Override
  public UserResponseDto createUserRegistration(UserDto userDto) {
    try {
      userRepository.findByEmail(userDto.getEmail())
          .ifPresent(user -> {
            log.error("message=\"there is already an account with this email: {}\"", userDto.getEmail());
            throw new InvalidDataException("this email is already in use");
          });
      final User user = dtoToEntityMapper.toEntity(userDto);
      final User savedUser = userRepository.saveAndFlush(user);
      return dtoToEntityMapper.toResponseDto(savedUser);

    } catch (InvalidDataException ex) {
      log.error("message=\"error while creating a new user. ex=\"", ex);
      throw ex;
    }
  }
}
