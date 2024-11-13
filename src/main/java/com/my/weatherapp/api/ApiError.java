package com.my.weatherapp.api;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@JsonInclude(NON_EMPTY)
@Builder
@Data
public class ApiError implements Serializable {

  private static final long serialVersionUID = 8447562876432845345L;

  private String errorId;
  private String message;
  private List<ApiErrorDetail> details;

  @JsonIgnore
  private HttpStatus httpStatus;
}
