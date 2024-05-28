package de.leafgrow.leafgrow_project.domain.dto;

import java.util.Objects;

public class PotDTO {
    private Long id;
    private Long userId;
    private Long instructionId;
    private boolean isActive;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(Long instructionId) {
        this.instructionId = instructionId;
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
        PotDTO potDTO = (PotDTO) o;
        return isActive == potDTO.isActive && Objects.equals(id, potDTO.id) && Objects.equals(userId, potDTO.userId) && Objects.equals(instructionId, potDTO.instructionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, instructionId, isActive);
    }

    @Override
    public String toString() {
        return "PotDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", instructionId=" + instructionId +
                ", isActive=" + isActive +
                '}';
    }
}
