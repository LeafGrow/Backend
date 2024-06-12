package de.leafgrow.leafgrow_project.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "instruction")
public class Instruction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "is_important")
    //private boolean isImportant;
    private int isImportant;

    @Column(name = "day")
    private int day;

    @Column(name = "image")
    private String image;

    public Instruction(Long id, String content, int isImportant, int day, String image) {
        this.id = id;
        this.content = content;
        this.isImportant = isImportant;
        this.day = day;
        this.image = image;
    }

    public Instruction() {
    }

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

    public int isImportant() {
        return isImportant;
    }

    public void setImportant(int important) {
        isImportant = important;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
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
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Instruction that = (Instruction) o;
        return isImportant == that.isImportant && day == that.day && Objects.equals(id, that.id) && Objects.equals(content, that.content) && Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, isImportant, day, image);
    }

    @Override
    public String toString() {
        return String.format("Instruction: ID - %d, content - %s, day - %d, " +
                        "image - %s, important - %s",
                id, content, day, image, isImportant == 1 ? "yes" : "no");
    }
}