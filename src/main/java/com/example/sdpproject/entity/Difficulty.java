package com.example.sdpproject.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
public class Difficulty {
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
}
