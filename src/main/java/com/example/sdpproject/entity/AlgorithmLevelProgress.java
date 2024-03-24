package com.example.sdpproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "algorithm_level_progress")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class AlgorithmLevelProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private boolean isDone = false;

    @ManyToOne
    private User user;
    @ManyToOne
    private Algorithm algorithm;


}
