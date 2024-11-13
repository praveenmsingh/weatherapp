package com.my.weatherapp.processor;

import com.my.weatherapp.dto.UserProfileDto;
import com.my.weatherapp.dto.WeatherProfileResponseDto;
import com.my.weatherapp.exception.InvalidDataException;
import com.my.weatherapp.mapper.WeatherProfileMapper;
import com.my.weatherapp.repository.CityRepository;
import com.my.weatherapp.repository.UserRepository;
import com.my.weatherapp.repository.WeatherProfileRepository;
import com.my.weatherapp.repository.entity.City;
import com.my.weatherapp.repository.entity.User;
import com.my.weatherapp.repository.entity.WeatherProfile;
import com.my.weatherapp.service.WeatherProfileService;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherProfileProcessor implements WeatherProfileService {

  private final WeatherProfileMapper dtoToEntityMapper;
  private final UserRepository userRepository;
  private final CityRepository cityRepository;
  private final WeatherProfileRepository weatherProfileRepository;

  @Override
  @Transactional
  public WeatherProfileResponseDto createUserProfileRegistration(final UserProfileDto userProfileDto) {
    try {
      final User user = getUser(userProfileDto);
      weatherProfileRepository.findByNameAndUserId(userProfileDto.getName(), user.getId())
          .ifPresent(profile -> {
            log.error("message=\"profile with given name: {}, already exists for user: {}\"", userProfileDto.getName(),
                userProfileDto.getUserId());
            throw new InvalidDataException("user already has profile with the same name");
          });
      Set<City> matchingCities = findMatchingCities(userProfileDto.getCities());

      WeatherProfile weatherProfile = WeatherProfile.builder()
          .user(user)
          .name(userProfileDto.getName())
          .cities(matchingCities)
          .build();

      final WeatherProfile savedProfile = weatherProfileRepository.save(weatherProfile);

      // Log for debugging
      log.debug("Saved WeatherProfile: {}", savedProfile);

      return WeatherProfileResponseDto.builder()
          .id(savedProfile.getId())
          .build();
    } catch (RuntimeException ex) {
      log.error("message=\"error while creating new user profile\"", ex);
      throw ex;
    }
  }

  @Override
  @Transactional
  public WeatherProfileResponseDto modifyUserProfile(final UserProfileDto userProfileDto) {
    final Long profileId = userProfileDto.getUserProfileId();
    final Long userId = userProfileDto.getUserId();
    try {
      final User user = getUser(userProfileDto);
      final WeatherProfile weatherProfile = weatherProfileRepository.findByIdAndUserId(profileId, userId)
          .orElseThrow(() -> {
            log.error("message=\"no profile found for given profileId: {} for user: {}\"", profileId, userId);
            throw new NoSuchElementException("no matching userProfile found for given user");
          });
      final Set<City> matchingCities = findMatchingCities(userProfileDto.getCities());

      weatherProfile.setName(userProfileDto.getName());
      weatherProfile.setCities(matchingCities);

      final WeatherProfile savedProfile = weatherProfileRepository.saveAndFlush(weatherProfile);
      return WeatherProfileResponseDto.builder()
          .id(savedProfile.getId())
          .name(savedProfile.getName())
          .build();
    } catch (RuntimeException ex) {
      log.error("message=\"error while modifying profile: " + profileId + " of user: " + userId + "\"", ex);
      throw ex;
    }
  }

  @Override
  public void deleteUserProfile(Long userId, Long profileId) {
    try {
      final User user = getUser(userId);
      weatherProfileRepository.findByIdAndUserId(profileId, userId)
          .ifPresentOrElse(weatherProfileRepository::delete, () -> {
            log.error("message=\"no profile found for given profileId: {} for user: {}\"", profileId, userId);
            throw new NoSuchElementException("no matching userProfile found for given user");
          });
      weatherProfileRepository.deleteById(profileId);
    } catch (RuntimeException ex) {
      log.error("message=\"error while deleting profile: \"" + " of user: " + "\"", ex);
      throw ex;
    }
  }

  @Override
  public WeatherProfileResponseDto getUserProfile(Long userId, Long profileId) {
    try {
      final WeatherProfile matchingProfile = weatherProfileRepository.findByIdAndUserId(profileId, userId)
          .orElseThrow(() -> {
            log.error("message=\"no profile found for given profileId: {} for user: {}\"", profileId, userId);
            throw new NoSuchElementException("no matching userProfile found for given user");
          });

      final List<Long> cityIds = matchingProfile.getCities().stream()
          .map(City::getId)
          .toList();
      final List<City> cities = cityRepository.findAllById(cityIds);
      if (cities.size() < cityIds.size()) {
        log.error("message=\"weather data not found for some cities\"");
        throw new InvalidDataException("weather data not found for some cities");
      }


      return dtoToEntityMapper.toResponseDataWithWeather(matchingProfile, cities);

    } catch (RuntimeException ex) {
      log.error("message=\"error while fetching profile: \"" + profileId + " of user: " + userId + "\"", ex);
      throw ex;
    }
  }

  @Override
  public List<WeatherProfileResponseDto> getAllUserProfilesForUserId(Long userId) {
    try {
      return null;

    } catch (RuntimeException ex) {
      log.error("message=\"error while fetching all the profiles for user: " + userId + "\"", ex);
      throw ex;
    }
  }

  private User getUser(UserProfileDto dto) {
    return getUser(dto.getUserId());
  }

  private User getUser(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> {
          log.error("message=\"no user found for given userId: {}\"", userId);
          throw new NoSuchElementException("no user found for given userId");
        });
  }

  private WeatherProfile getWeatherProfile(Long profileId) {
    return weatherProfileRepository.findById(profileId)
        .orElseThrow(() -> {
          log.error("message=\"no userProfile found for given profileId: {}\"", profileId);
          throw new InvalidDataException("no userProfile found for given profileId");
        });
  }

  private Set<City> findMatchingCities(Set<String> cities) {
    final Set<City> matchingCities = cityRepository.findAllByNameIn(cities);
    final Set<String> matchingCityNames = matchingCities.stream()
        .map(City::getName)
        .collect(Collectors.toSet());
    final List<String> missingCities = cities.stream()
        .filter(city -> !matchingCityNames.contains(city))
        .toList();

    if (!CollectionUtils.isEmpty(missingCities)) {
      final String[] cityArray = missingCities.toArray(new String[0]);
      StringBuilder concatCities = new StringBuilder();
      for (String city : cityArray) {
        concatCities.append(city).append(", ");
      }
      log.error("message=\"unable to create weather profile registration with given cities: {}\"", concatCities);
      throw new InvalidDataException("unable to create weather profile registration with given cities: " + concatCities);
    }

    return matchingCities;
  }

  private Optional<WeatherProfile> findMatchingProfileName(UserProfileDto userProfileDto, User user) {
    return user.getWeatherProfiles().stream()
        .filter(profile -> profile.getName().equalsIgnoreCase(userProfileDto.getName()))
        .findAny();
  }
}
