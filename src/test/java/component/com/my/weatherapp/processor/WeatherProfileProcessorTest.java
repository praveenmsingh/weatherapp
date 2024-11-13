package component.com.my.weatherapp.processor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import com.fasterxml.jackson.databind.ObjectMapper;
import component.com.my.weatherapp.ComponentTest;
import com.my.weatherapp.dto.UserProfileDto;
import com.my.weatherapp.processor.WeatherProfileProcessor;
import com.my.weatherapp.repository.WeatherProfileRepository;
import java.io.IOException;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.util.ReflectionTestUtils;

@ComponentTest
public class WeatherProfileProcessorTest {

  @Autowired
  private WeatherProfileProcessor processor;

  @Autowired
  private WeatherProfileRepository repository;

  @Autowired
  private ObjectMapper objectMapper;

  private static final String USER_PROFILE_REGISTRATION_REQUEST_DTO_PATH = "/files/UserProfileRequestDto.json";

  @Test
  @Sql(scripts = {"/db.scripts/cleanup_user_profile_data.sql", "/db.scripts/insert_user_profile_data.sql"}, executionPhase = BEFORE_TEST_METHOD)
  @Sql(scripts = {"/db.scripts/cleanup_user_profile_data.sql"}, executionPhase = AFTER_TEST_METHOD)
  public void givenUserDoesNotExist_newUserProfileReq_throwsNoSuchElementException() throws IOException {
    final UserProfileDto userProfileDto = objectMapper.readValue(getClass().getResourceAsStream(USER_PROFILE_REGISTRATION_REQUEST_DTO_PATH),
        UserProfileDto.class);
    ReflectionTestUtils.setField(userProfileDto, "userId", 1000L); // unknown user

    final NoSuchElementException exception = assertThrows(NoSuchElementException.class, () ->
        processor.createUserProfileRegistration(userProfileDto));

    assertEquals("no user found for given userId", exception.getMessage());

  }


}
