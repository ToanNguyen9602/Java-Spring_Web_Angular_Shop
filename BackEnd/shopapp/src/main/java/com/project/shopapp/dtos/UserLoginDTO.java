package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
  @JsonProperty("phone_number")
  @NotBlank
  private String phoneNumber;
  @NotBlank(message = "Cannot empty")
  private String password;
  @Min(value = 1, message = "You must enter role's Id")
  @JsonProperty("role_id")
  private Long roleId;
}
