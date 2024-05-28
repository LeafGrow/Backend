package de.leafgrow.leafgrow_project.domain.dto;


import java.time.LocalDateTime;
import java.util.Objects;

public class InstructionDTO {
    private Long id;
    private String content;
    private boolean isImportant;
    private LocalDateTime day;
    private String image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public void setImportant(boolean isImportant) {
        this.isImportant = isImportant;
    }

    public LocalDateTime getDay() {
        return day;
    }

    public void setDay(LocalDateTime day) {
        this.day = day;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstructionDTO that = (InstructionDTO) o;
        return isImportant == that.isImportant && Objects.equals(id, that.id) && Objects.equals(content, that.content) && Objects.equals(day, that.day) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, isImportant, day, image);
    }

    @Override
    public String toString() {
        return "InstructionDTO{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", isImportant=" + isImportant +
                ", day=" + day +
                ", image='" + image + '\'' +
                '}';
    }
}
