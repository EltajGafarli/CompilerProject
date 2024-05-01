package com.example.sdpproject.entity.algorithm;

import com.example.sdpproject.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Table(
        name = "conversation_topic",
        uniqueConstraints = @UniqueConstraint(
                columnNames = "topic"
        )
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ConversationTopic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String topic;

    @OneToOne
    private Conversation conversation;

//    @ManyToOne(
//            cascade = {
//                    CascadeType.DETACH,
//                    CascadeType.MERGE,
//                    CascadeType.PERSIST,
//                    CascadeType.REFRESH
//            }
//    )
//    private User user;

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                topic,
                conversation
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        ConversationTopic conversationTopic = (ConversationTopic) obj;
        return Objects.deepEquals(conversationTopic.getId(), this.id) &&
                Objects.deepEquals(conversationTopic.getTopic(), this.topic) &&
                Objects.deepEquals(conversationTopic.getConversation(), this.conversation);
    }
}
