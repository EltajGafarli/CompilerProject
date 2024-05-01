package com.example.sdpproject.entity.algorithm;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SubjectImage extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imageId;
    private String imageUrl;

    @OneToOne(
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            },
            mappedBy = "subjectImage"
    )
    private Subject subject;


    @Override
    public int hashCode() {
        return Objects
                .hash(
                        this.imageId, this.imageUrl, this.subject
                );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        SubjectImage subjectImage = (SubjectImage) obj;
        return Objects.deepEquals(this.subject, subjectImage.subject)
                && Objects.deepEquals(this.imageId, subjectImage.imageId)
                && Objects.deepEquals(this.imageUrl, subjectImage.imageUrl)
                && Objects.deepEquals(this.getCreatedAt(), subjectImage.getCreatedAt())
                && Objects.deepEquals(this.getUpdatedAt(), subjectImage.getUpdatedAt());
    }
}
