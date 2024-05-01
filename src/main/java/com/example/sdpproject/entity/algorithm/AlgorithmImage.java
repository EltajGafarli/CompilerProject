package com.example.sdpproject.entity.algorithm;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AlgorithmImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imageId;
    private String imageUrl;

    @ManyToOne(
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            }
    )
    private Algorithm algorithm;

    @Override
    public int hashCode() {
        return Objects
                .hash(
                        this.imageId, this.imageUrl, this.algorithm
                );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        AlgorithmImage algorithmImage = (AlgorithmImage) obj;
        return Objects.deepEquals(this.algorithm, algorithmImage.algorithm)
                && Objects.deepEquals(this.imageId, algorithmImage.imageId)
                && Objects.deepEquals(this.imageUrl, algorithmImage.imageUrl)
                && Objects.deepEquals(this.getCreatedAt(), algorithmImage.getCreatedAt())
                && Objects.deepEquals(this.getUpdatedAt(), algorithmImage.getUpdatedAt());
    }

}
