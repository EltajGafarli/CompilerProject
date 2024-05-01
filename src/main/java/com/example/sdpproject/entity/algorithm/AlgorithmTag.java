package com.example.sdpproject.entity.algorithm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
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
public class AlgorithmTag extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "difficulty_level")
    private String algorithmTag;

    @OneToMany(cascade = {
            CascadeType.ALL
    }, fetch = FetchType.EAGER,
            mappedBy = "algorithmTag"
    )
    @JsonIgnore
    private List<Algorithm> algorithms = new ArrayList<>();


    @OneToOne(
            cascade = CascadeType.ALL,
            mappedBy = "algorithmTag"
    )
    private Subject subject;


    public void addAlgorithm(Algorithm algorithm) {
        this.algorithms.add(algorithm);
        algorithm.setAlgorithmTag(this);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(this.id, this.algorithms, this.algorithmTag,
                        this.getCreatedAt(), this.getUpdatedAt());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        AlgorithmTag algorithmTag = (AlgorithmTag) obj;
        return Objects
                .deepEquals(this.id, algorithmTag.id)
                && Objects.deepEquals(this.algorithmTag, algorithmTag.algorithmTag)
                && Objects.deepEquals(this.algorithms, algorithmTag.algorithms)
                && Objects.deepEquals(this.getCreatedAt(), algorithmTag.getCreatedAt())
                && Objects.deepEquals(this.getUpdatedAt(), algorithmTag.getUpdatedAt());

    }
}
