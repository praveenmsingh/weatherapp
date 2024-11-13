package com.my.weatherapp.controllers;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.my.weatherapp.api.User;
import com.my.weatherapp.api.UserProfile;
import com.my.weatherapp.api.UserProfileResponse;
import com.my.weatherapp.dto.UserDto;
import com.my.weatherapp.dto.UserProfileDto;
import com.my.weatherapp.dto.UserResponseDto;
import com.my.weatherapp.dto.WeatherProfileResponseDto;
import com.my.weatherapp.service.UserService;
import com.my.weatherapp.service.WeatherProfileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1", produces = APPLICATION_JSON_VALUE)
public class UserController {

  private final UserService userService;
  private final WeatherProfileService weatherProfileService;

  @PostMapping("/users/registration")
  public ResponseEntity<User> registerUsers(
      @RequestHeader final HttpHeaders httpHeaders,
      @RequestBody @Valid final User user, final HttpServletRequest request) {

    log.info("message=\"received new user registration request: {}\"", user);
    final UserResponseDto savedUser = userService.createUserRegistration(new UserDto(user.name(), user.email()));

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(new User(savedUser.id().toString(), savedUser.name(), savedUser.email()));
  }

  @PostMapping("/users/{userId}/profiles/registration")
  public ResponseEntity<UserProfileResponse> registerUserProfile(
      @PathVariable final Long userId, @RequestHeader final HttpHeaders httpHeaders,
      @RequestBody @Valid final UserProfile profile, final HttpServletRequest request) {
    log.info("message=\"received new user profile creation request for user: {}\"", userId);

    final UserProfileDto dto = UserProfileDto.builder()
        .userId(userId)
        .name(profile.getProfileName())
        .cities(profile.getCities())
        .build();
    final WeatherProfileResponseDto savedProfile = weatherProfileService.createUserProfileRegistration(dto);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(UserProfileResponse.builder().profileName(savedProfile.getName()).id(savedProfile.getId()).build());
  }

  @GetMapping("/users/{userId}/profiles/{profileId}")
  public ResponseEntity<UserProfileResponse> getUserProfileById(
      @PathVariable final Long userId, @PathVariable final Long profileId,
      final HttpServletRequest request) {
    log.info("message=\"received new fetchProfile request for user: {}, profile: {}\"", userId, profileId);

    final WeatherProfileResponseDto weatherProfileResponseDto = weatherProfileService.getUserProfile(userId, profileId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(UserProfileResponse.builder()
            .id(weatherProfileResponseDto.getId())
            .profileName(weatherProfileResponseDto.getName())
            .cityWeather(weatherProfileResponseDto.getCities())
            .build());
  }

  @PutMapping("/users/{userId}/profiles/{profileId}")
  public ResponseEntity<UserProfileResponse> updateUserProfile(
      @PathVariable final Long userId, @PathVariable final Long profileId,
      @RequestBody @Valid final UserProfile profile, final HttpServletRequest request) {
    log.info("message=\"received new user profile update request for user: {}, profile: {}\"", userId, profileId);

    final UserProfileDto dto = UserProfileDto.builder()
        .userId(userId)
        .userProfileId(profileId)
        .name(profile.getProfileName())
        .cities(profile.getCities())
        .build();

    final WeatherProfileResponseDto savedProfile = weatherProfileService.modifyUserProfile(dto);
    return ResponseEntity.status(OK)
        .body(UserProfileResponse.builder().profileName(savedProfile.getName()).id(savedProfile.getId()).build());
  }

  @DeleteMapping("/users/{userId}/profiles/{profileId}")
  public ResponseEntity<Void> deleteUserProfile(
      @PathVariable final Long userId, @PathVariable final Long profileId, @RequestHeader final HttpHeaders httpHeaders,
      final HttpServletRequest request) {
    log.info("message=\"received user profile deletion request for user: {}, profile: {}\"", userId, profileId);

    weatherProfileService.deleteUserProfile(userId, profileId);
    return new ResponseEntity<>(OK);
  }

}
