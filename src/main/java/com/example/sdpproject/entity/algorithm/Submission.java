package com.example.sdpproject.entity.algorithm;


import com.example.sdpproject.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;


@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Submission extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1905122041950251207L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "solution_code", columnDefinition = "text")
    private String solutionCode;

    private String cpuTime;
    private String memory;
    private boolean isSuccessful;

    private String programmingLanguage;

    @ManyToOne(cascade = {
            CascadeType.PERSIST,
            CascadeType.REFRESH,
            CascadeType.MERGE,
            CascadeType.DETACH
    })
    @JsonIgnore
    private Algorithm algorithm;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.REFRESH,
                    CascadeType.MERGE,
                    CascadeType.DETACH
            }
    )
    private User user;

    @Override
    public int hashCode() {
        return Objects
                .hash(
                        this.id,
                        this.algorithm,
                        this.solutionCode,
                        this.programmingLanguage,
                        this.user,
                        this.memory,
                        this.cpuTime,
                        this.isSuccessful
                );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() == obj.getClass()) {
            return false;
        }
        Submission solution = (Submission) obj;
        return Objects.deepEquals(this.id, solution.id) &&
                Objects.deepEquals(this.solutionCode, solution.solutionCode) &&
                Objects.deepEquals(this.programmingLanguage, solution.programmingLanguage) &&
                Objects.deepEquals(this.getCreatedAt(), solution.getCreatedAt()) &&
                Objects.deepEquals(this.algorithm, solution.algorithm) &&
                Objects.deepEquals(this.user, solution.user) &&
                Objects.deepEquals(this.memory, solution.memory) &&
                Objects.deepEquals(this.cpuTime, solution.cpuTime) &&
                Objects.deepEquals(this.isSuccessful, solution.isSuccessful);
    }
}
