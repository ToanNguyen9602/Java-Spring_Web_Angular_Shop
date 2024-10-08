package com.project.shopapp.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity @Builder @Data
@Getter @Setter
@Table(name = "users")
@AllArgsConstructor @NoArgsConstructor
public class User extends BaseEntity implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "fullname", length = 100)
  private String fullName;

  @Column(name = "phone_number", length = 10, nullable = false)
  private String phoneNumber;

  @Column(name = "address", length = 200)
  private String address;

  @Column(name = "password", length = 200)
  private String password;

  @Column(name = "is_active")
  private boolean active;

  @Column(name = "date_of_birth")
  private Date dateOfBirth;

  @Column(name = "facebook_account_id")
  private int facebookAccountId;

  @Column(name = "google_account_id")
  private int googleAccountId;

  @ManyToOne
  @JoinColumn(name = "role_id")
  private Role role;

  //lấy quyền = role
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
    authorityList.add(new SimpleGrantedAuthority(
            "ROLE_"+getRole().getName().toUpperCase()));
//    authorityList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
    return authorityList;
  }
  //trường username thì thằng User nó tự hiểu duy nhất k trùng
  //còn mình thì tùy quy ước phone hay id hay username tuy minh

  @Override
  public String getUsername() {
    return phoneNumber;
  }

  //tai khoan k gioi han
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  //không thể khóa dc
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
