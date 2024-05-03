package com.example.sdpproject.entity.algorithm;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Subject extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;

    @Column(columnDefinition = "text")
    private String description;
    @Column(name = "`rank`")
    private Integer rank;

    @OneToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "algorithm_tag_id", referencedColumnName = "id")
    private AlgorithmTag algorithmTag;

    @OneToMany(
            cascade = CascadeType.ALL,
            mappedBy = "subject"
    )
    private List<Algorithm> algorithms = new ArrayList<>();

    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "image_id", unique = true, referencedColumnName = "imageId")
    private SubjectImage subjectImage;

    public void addAlgorithm(Algorithm algorithm) {
        algorithms.add(algorithm);
        algorithm.setSubject(this);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(
                        this.id,
                        this.rank,
                        this.description,
                        this.title
                );
    }

    @Override
    public boolean equals(Object object) {
        if(object == null || object.getClass() != this.getClass()) {
            return false;
        }

        Subject subject = (Subject) object;

        return Objects.deepEquals(this.id, subject.id) &&
                Objects.deepEquals(this.description, subject.description) &&
                Objects.deepEquals(this.rank, subject.rank) &&
                Objects.deepEquals(this.title, subject.title);
    }

}
