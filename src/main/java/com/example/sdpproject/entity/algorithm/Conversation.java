package com.example.sdpproject.entity.algorithm;

import com.example.sdpproject.entity.enums.ConversationTopic;
import com.example.sdpproject.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = "conversationName",
                        name = "COVERSATION_UNIQUE"
                )
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    String conversationName;

    @OneToMany(
            cascade = {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.PERSIST,
                    CascadeType.REFRESH
            },
            fetch = FetchType.LAZY)
    private List<Message> messages = new ArrayList<>();

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    private User user;

    @Column(name = "conversationTopic")
    @Enumerated(value = EnumType.STRING)
    private ConversationTopic conversationTopic;

    @Override
    public int hashCode() {
        return Objects.hash(
                this.id,
                this.messages,
                this.user,
                this.conversationTopic
        );
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Conversation conversation = (Conversation) obj;

        return Objects.deepEquals(this.id, conversation.id) &&
                Objects.deepEquals(this.messages, conversation.messages) &&
                Objects.deepEquals(this.conversationTopic, conversation.conversationTopic);
    }

    public void addMessage(Message message) {
        this.messages.add(message);
        message.setConversation(this);
    }
}
