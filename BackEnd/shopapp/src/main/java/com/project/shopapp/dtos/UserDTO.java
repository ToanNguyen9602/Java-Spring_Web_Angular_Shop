package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Data @Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class UserDTO {

  @JsonProperty("fullname")
  private String fullName;

  @JsonProperty("phone_number")
  @NotBlank
  private String phoneNumber;

  private String address;
  @NotBlank(message = "Cannot empty")
  private String password;
  @NotBlank(message = "Cannot empty")
  @JsonProperty("retype_password")
  private String retypePassword;

  @JsonProperty("date_of_birth")
  private Date dateOfBirth;

  @JsonProperty("facebook_account_id")
  private int facebookAccountId;

  @JsonProperty("google_account_id")
  private int googleAccountId;

  @JsonProperty("role_id")
  @NotNull(message = "Role ID is required")
  private Long roleId;
}
