package de.leafgrow.leafgrow_project.domain.dto;



import java.time.LocalDateTime;
import java.util.Objects;

public class ConfirmationCodeDTO {
    private Long id;
    private String code;
    private Long userId;
    private LocalDateTime expired;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getExpired() {
        return expired;
    }

    public void setExpired(LocalDateTime expired) {
        this.expired = expired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfirmationCodeDTO that = (ConfirmationCodeDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(code, that.code) && Objects.equals(userId, that.userId) && Objects.equals(expired, that.expired);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, userId, expired);
    }

    @Override
    public String toString() {
        return "ConfirmationCodeDTO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", userId=" + userId +
                ", expired=" + expired +
                '}';
    }
}
