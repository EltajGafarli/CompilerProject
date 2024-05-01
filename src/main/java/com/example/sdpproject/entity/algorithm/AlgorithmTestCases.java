package com.example.sdpproject.entity.algorithm;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AlgorithmTestCases extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.REFRESH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            }
    )
    private Algorithm algorithm;

    @Column(columnDefinition = "text", unique = true)
    private String testCase;

    @Column(columnDefinition = "text")
    private String correctAnswer;

}
