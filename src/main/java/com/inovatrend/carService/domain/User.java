package com.inovatrend.carService.domain;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", allocationSize = 10)
    @Setter(AccessLevel.PRIVATE)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "username", unique = true, nullable = false)
    private String username ;

    @NotNull
    @Column(name = "email", unique = true, nullable = false)
    private String email ;

    @Column(name = "password", nullable = false)
    private String password ;

    private String confirmPassword;

    @Column(name = "reset_token")
    private String resetToken;

    @Column(name = "active")
    private boolean active = false;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="user_permissions", joinColumns=@JoinColumn(name="user_id"))
    List<Permission> permissions = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions.stream()
                .map( p -> new SimpleGrantedAuthority(p.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

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
        return active;
    }
}
