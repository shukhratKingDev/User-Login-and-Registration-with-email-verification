package com.company.springsecurity_jwt_emailsending.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {


    @Id
    @GeneratedValue
    private UUID id;// user id.

    @Column(nullable = false,length = 50)
    private String firstName;

    @Column(nullable = false)
    private String lastName;


    @Column(unique = true,nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false,updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(nullable = false,updatable = false)
    @UpdateTimestamp
    private Timestamp updatedAt;

    @ManyToMany
    private Set<Role> roles;

    private String emailCode;

    private boolean accountNonExpired=true;// user account not expired

    private boolean  nonLocked=true;

    private boolean credentialsNonExpired=true;

    private boolean enabled=false;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.nonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }




}
