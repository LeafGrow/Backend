package de.leafgrow.leafgrow_project.domain.dto;

import java.util.Objects;
import java.util.Set;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Set<RoleDTO> roles;
    private boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return isActive == userDTO.isActive && Objects.equals(id, userDTO.id) && Objects.equals(username, userDTO.username) && Objects.equals(email, userDTO.email) && Objects.equals(password, userDTO.password) && Objects.equals(roles, userDTO.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, roles, isActive);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", roles=" + roles +
                ", isActive=" + isActive +
                '}';
    }
}

