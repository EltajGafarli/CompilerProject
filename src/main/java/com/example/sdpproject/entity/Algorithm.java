package com.example.sdpproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "algorithms")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Algorithm extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    @Column(columnDefinition = "text")
    private String problemStatement;

    @Column(columnDefinition = "text")
    private String testCase;

    @Column(columnDefinition = "text")
    private String constraints;

    @OneToOne(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "difficulty_id", referencedColumnName = "id")
    private Difficulty difficulty;

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id, this.title, this.problemStatement, this.testCase, this.constraints, this.difficulty,
                        this.getCreatedAt(), this.getUpdatedAt());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || this.getClass() == obj.getClass()) {
            return false;
        }
        Algorithm algorithm = (Algorithm) obj;
        return Objects
                .deepEquals(id, algorithm.id)
                && Objects.deepEquals(title, algorithm.title)
                && Objects.deepEquals(problemStatement, algorithm.problemStatement)
                && Objects.deepEquals(testCase, algorithm.testCase)
                && Objects.deepEquals(constraints, algorithm.constraints)
                && Objects.deepEquals(difficulty, algorithm.difficulty)
                && Objects.deepEquals(this.getCreatedAt(), algorithm.getCreatedAt())
                && Objects.deepEquals(this.getUpdatedAt(), algorithm.getUpdatedAt());
    }

}
