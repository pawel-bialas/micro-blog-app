package com.github.MicroBlog.model;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Table(name = "Users")
@Where(clause = "status <> 'BLOCKED'" )
public class User implements UserDetails {

    private static final long serialVersionUID = 641597340859104987L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String login;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String unigueAccName;
    private String descAccName;
    @CreationTimestamp
    private LocalDateTime creationDate;
    @Enumerated(EnumType.STRING)
    private UserStatus status;
    @Enumerated(EnumType.STRING)
    private UserType type;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private Long avatarId;


    public User(String login,
                String password,
                String unigueAccName,
                String descAccName,
                LocalDateTime creationDate,
                UserStatus status,
                UserType type,
                UserRole role,
                Long avatarId) {
        this.login = login;
        this.password = password;
        this.unigueAccName = unigueAccName;
        this.descAccName = descAccName;
        this.creationDate = creationDate;
        this.status = status;
        this.type = type;
        this.role = role;
        this.avatarId = avatarId;
    }

    public User() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return unigueAccName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status != UserStatus.BLOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status == UserStatus.ACTIVE;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUnigueAccName() {
        return unigueAccName;
    }

    public void setUnigueAccName(String unigueAccName) {
        this.unigueAccName = unigueAccName;
    }

    public String getDescAccName() {
        return descAccName;
    }

    public void setDescAccName(String descAccName) {
        this.descAccName = descAccName;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", unigueAccName='" + unigueAccName + '\'' +
                ", descAccName='" + descAccName + '\'' +
                ", creationDate=" + creationDate +
                ", status=" + status +
                ", type=" + type +
                ", role=" + role +
                ", avatarId=" + avatarId +
                '}';
    }
}
