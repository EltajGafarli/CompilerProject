package com.example.sdpproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "submission")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Submission extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "algorithm_id")
    private Algorithm algorithm;

    private String solutionCode;

    private boolean isSuccessful;

    @Override
    public int hashCode() {
        return Objects
                .hash(this.id, this.algorithm, this.isSuccessful, this.solutionCode, this.user,
                        this.getCreatedAt(), this.getUpdatedAt());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Submission submission = (Submission) obj;
        return Objects
                .deepEquals(this.id, submission.id)
                && Objects.deepEquals(this.algorithm, submission.algorithm)
                && Objects.deepEquals(this.user, submission.user)
                && Objects.deepEquals(this.isSuccessful, submission.isSuccessful)
                && Objects.deepEquals(this.solutionCode, submission.solutionCode)
                && Objects.deepEquals(this.getCreatedAt(), submission.getCreatedAt())
                && Objects.deepEquals(this.getUpdatedAt(), submission.getUpdatedAt());
    }


}
