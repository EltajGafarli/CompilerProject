package com.example.sdpproject.entity.algorithm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "difficulty", uniqueConstraints = @UniqueConstraint(
        columnNames = {"difficultyLevel"}
))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Difficulty extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "difficulty_level")
    private String difficultyLevel;

    @OneToMany(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    }, fetch = FetchType.EAGER,
            mappedBy = "difficulty"
    )
    @JsonIgnore
    private List<Algorithm> algorithms;

    @Override
    public int hashCode() {
        return Objects
                .hash(this.id, this.algorithms, this.difficultyLevel,
                        this.getCreatedAt(), this.getUpdatedAt());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Difficulty difficulty = (Difficulty) obj;
        return Objects
                .deepEquals(this.id, difficulty.id)
                && Objects.deepEquals(this.difficultyLevel, difficulty.difficultyLevel)
                && Objects.deepEquals(this.algorithms, difficulty.algorithms)
                && Objects.deepEquals(this.getCreatedAt(), difficulty.getCreatedAt())
                && Objects.deepEquals(this.getUpdatedAt(), difficulty.getUpdatedAt());

    }
}
