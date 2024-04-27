package com.example.sdpproject.entity.algorithm;

import com.example.sdpproject.entity.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Message extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String message;

    @ManyToOne(
            cascade = {
                    CascadeType.ALL
            }
    )
    private Conversation conversation;

    @ManyToOne(
            cascade = CascadeType.ALL
    )
    private User sender;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Message parent;

    @OneToMany(mappedBy = "parent")
    private List<Message> replies = new ArrayList<>();

    @Override
    public int hashCode() {
        return Objects.hash(
                this.conversation,
                this.sender,
                this.parent,
                this.id,
                this.replies
        );
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        Message message = (Message) obj;
        return Objects.deepEquals(this.id, message.id) &&
                Objects.deepEquals(this.message, message.message) &&
                Objects.deepEquals(this.sender, message.sender) &&
                Objects.deepEquals(this.replies, message.replies) &&
                Objects.deepEquals(this.conversation, message.conversation) &&
                Objects.deepEquals(this.parent, message.parent);
    }


    public void addMessage(Message message) {
        replies.add(message);
        message.setParent(this);
    }
}
