package com.example.sdpproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "algorithms")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class Algorithm {

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

}
