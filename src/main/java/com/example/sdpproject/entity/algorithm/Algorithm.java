package com.example.sdpproject.entity.algorithm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.*;

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

    @Column
    private String title;

    @Column(columnDefinition = "text")
    private String constraints;

    @Column(columnDefinition = "text")
    private String problemStatement;

    @OneToMany(
            cascade = {
                    CascadeType.ALL
            },
            mappedBy = "algorithm"
    )
    @JsonIgnore
    private Set<AlgorithmTestCases> testCases = new HashSet<>();

    @ManyToOne(
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            },
            fetch = FetchType.EAGER
    )
    @JoinColumn(name = "algorithm_tag_id", referencedColumnName = "id")
    private AlgorithmTag algorithmTag;

    @OneToMany(
            cascade = {
                    CascadeType.ALL
            },
            mappedBy = "algorithm"
    )
    private Set<AlgorithmImage> algorithmImages = new HashSet<>();

    @OneToMany(
            cascade = {
                    CascadeType.ALL
            },
            mappedBy = "algorithm",
            fetch = FetchType.LAZY
    )
    @JsonIgnore
    private List<Submission> submissions = new ArrayList<>();

    @ManyToOne(
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            }
    )
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    private Subject subject;

    public void addTestCase(AlgorithmTestCases algorithmTestCases) {
        this.testCases.add(algorithmTestCases);
        algorithmTestCases.setAlgorithm(this);
    }

    public void addSubmission(Submission submission) {
        this.submissions.add(submission);
        submission.setAlgorithm(this);
    }

    public void addImage(AlgorithmImage algorithmImage) {
        this.algorithmImages.add(algorithmImage);
        algorithmImage.setAlgorithm(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id, this.title, this.problemStatement, this.testCases, this.constraints, this.algorithmTag,
                this.getCreatedAt(), this.getUpdatedAt());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Algorithm algorithm = (Algorithm) obj;
        return Objects
                .deepEquals(id, algorithm.id)
                && Objects.deepEquals(title, algorithm.title)
                && Objects.deepEquals(problemStatement, algorithm.problemStatement)
                && Objects.deepEquals(testCases, algorithm.testCases)
                && Objects.deepEquals(constraints, algorithm.constraints)
                && Objects.deepEquals(algorithmTag, algorithm.algorithmTag)
                && Objects.deepEquals(this.getCreatedAt(), algorithm.getCreatedAt())
                && Objects.deepEquals(this.getUpdatedAt(), algorithm.getUpdatedAt())
                && Objects.deepEquals(this.algorithmImages, algorithm.algorithmImages);
    }

}
