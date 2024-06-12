package de.leafgrow.leafgrow_project.domain.dto;

import de.leafgrow.leafgrow_project.domain.entity.Instruction;

public class PotDto {
    private Long id;
    private int isActive;
    private Instruction instruction;

    public PotDto(Long id, int isActive, Instruction instruction) {
        this.id = id;
        this.isActive = isActive;
        this.instruction = instruction;
    }

    public Long getId() {
        return id;
    }

    public int isActive() {
        return isActive;
    }

    public void setActive(int active) {
        isActive = active;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    @Override
    public String toString() {
        return "PotDto{" +
                "id=" + id +
                ", isActive=" + isActive +
                ", instruction=" + instruction +
                '}';
    }
}

