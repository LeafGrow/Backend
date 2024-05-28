package de.leafgrow.leafgrow_project.domain.dto;


import java.util.Objects;

public class RoleDTO {
    private Long id;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return Objects.equals(id, roleDTO.id) && Objects.equals(title, roleDTO.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "RoleDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
