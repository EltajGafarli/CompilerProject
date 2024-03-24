package com.example.sdpproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.Diff;

import java.io.Serializable;
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

    @OneToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    }, fetch = FetchType.EAGER,
            mappedBy = "difficulty"
    )
    @JsonIgnore
    private Algorithm algorithm;

    @Override
    public int hashCode() {
        return Objects
                .hash(this.id, this.algorithm, this.difficultyLevel,
                        this.getCreatedAt(), this.getUpdatedAt());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Difficulty difficulty = (Difficulty) obj;
        return Objects
                .deepEquals(this.id, difficulty.id)
                && Objects.deepEquals(this.difficultyLevel, difficulty.difficultyLevel)
                && Objects.deepEquals(this.algorithm, difficulty.algorithm)
                && Objects.deepEquals(this.getCreatedAt(), difficulty.getCreatedAt())
                && Objects.deepEquals(this.getUpdatedAt(), difficulty.getUpdatedAt());

    }
}
