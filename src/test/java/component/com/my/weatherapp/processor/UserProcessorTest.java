package component.com.my.weatherapp.processor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

import com.fasterxml.jackson.databind.ObjectMapper;
import component.com.my.weatherapp.ComponentTest;
import com.my.weatherapp.dto.UserDto;
import com.my.weatherapp.dto.UserResponseDto;
import com.my.weatherapp.exception.InvalidDataException;
import com.my.weatherapp.processor.UserProcessor;
import com.my.weatherapp.repository.UserRepository;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.util.ReflectionTestUtils;

@ComponentTest
public class UserProcessorTest {

  @Autowired
  private UserProcessor processor;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private static final String USER_REGISTRATION_REQUEST_DTO_PATH = "/files/UserRequestDto.json";

  @Test
  @Sql(scripts = {"/db.scripts/cleanup_user_profile_data.sql"}, executionPhase = BEFORE_TEST_METHOD)
  @Sql(scripts = {"/db.scripts/cleanup_user_profile_data.sql"}, executionPhase = AFTER_TEST_METHOD)
  public void givenUserDoesNotExist_newUserReq_success() throws IOException {
    final UserDto userDto = objectMapper.readValue(getClass().getResourceAsStream(USER_REGISTRATION_REQUEST_DTO_PATH),
        UserDto.class);

    final UserResponseDto userResponseDto = assertDoesNotThrow(() -> processor.createUserRegistration(userDto));

    assertNotNull(userResponseDto);
    assertEquals(1, userResponseDto.id());
    assertEquals(userDto.getName(), userResponseDto.name());
    assertEquals(userDto.getEmail(), userResponseDto.email());
  }

  @Test
  @Sql(scripts = {"/db.scripts/cleanup_user_profile_data.sql", "/db.scripts/insert_user_profile_data.sql"}, executionPhase = BEFORE_TEST_METHOD)
  @Sql(scripts = {"/db.scripts/cleanup_user_profile_data.sql"}, executionPhase = AFTER_TEST_METHOD)
  public void givenUserAlreadyNotExist_newUserReqWithSameEmail_throwsInvalidDataException() throws IOException {
    final UserDto userDto = objectMapper.readValue(getClass().getResourceAsStream(USER_REGISTRATION_REQUEST_DTO_PATH),
        UserDto.class);
    ReflectionTestUtils.setField(userDto, "email", "john@doe.com");
    assertTrue(userRepository.findByEmail(userDto.getEmail()).isPresent());

    final InvalidDataException exception = assertThrows(InvalidDataException.class, () ->
        processor.createUserRegistration(userDto));

    assertEquals("this email is already in use", exception.getMessage());
  }


}
