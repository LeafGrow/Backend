package de.leafgrow.leafgrow_project.domain.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "pot")
public class Pot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "instruction_id")
    private Instruction instruction;

    @Column(name = "is_active")
    //private boolean isActive;
    private int isActive;

    public Pot() {
    }

    public Pot(Long id, User user, Instruction instruction, int isActive) {
        this.id = id;
        this.user = user;
        this.instruction = instruction;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public int isActive() {
        return isActive;
    }

    public void setActive(int active) {
        isActive = active;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Pot pot = (Pot) o;
        return isActive == pot.isActive && Objects.equals(id, pot.id) && Objects.equals(user, pot.user) && Objects.equals(instruction, pot.instruction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, instruction, isActive);
    }

    @Override
    public String toString() {
        return String.format("Pot: ID - %d, user - %s, instruction - %s, active - %s",
                id, user, instruction, isActive == 1 ? "yes" : "no");
    }
}
