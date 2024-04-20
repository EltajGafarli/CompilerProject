package com.example.sdpproject.entity.algorithm;

import com.example.sdpproject.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "algorithm_level_progress")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AlgorithmLevelProgress extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean isDone = false;

    @ManyToOne
    private User user;
    @ManyToOne
    private Algorithm algorithm;

    @Override
    public int hashCode() {
        return Objects
                .hash(this.id, this.user, this.algorithm,
                        this.getCreatedAt(), this.getUpdatedAt());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        AlgorithmLevelProgress algorithmLevelProgress = (AlgorithmLevelProgress) obj;
        return Objects
                .deepEquals(this.id, algorithmLevelProgress.id)
                && Objects.deepEquals(this.algorithm, algorithmLevelProgress.algorithm)
                && Objects.deepEquals(this.user, algorithmLevelProgress.user)
                && Objects.deepEquals(this.isDone, algorithmLevelProgress.isDone)
                && Objects.deepEquals(this.getCreatedAt(), algorithmLevelProgress.getCreatedAt())
                && Objects.deepEquals(this.getUpdatedAt(), algorithmLevelProgress.getUpdatedAt());
    }

}
