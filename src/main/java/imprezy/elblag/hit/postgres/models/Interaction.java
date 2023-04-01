package imprezy.elblag.hit.postgres.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table( name = "interactions" )
public class Interaction {

    @EmbeddedId
    private InteractionId interactionId;

    private InteractionType interactionType;

    public Interaction() {
    }

    public Interaction(User sender, User receiver, InteractionType interactionType) {
        this.interactionId = new InteractionId(sender, receiver);
        this.interactionType = interactionType;
    }

    public InteractionType getInteractionType() {
        return interactionType;
    }

    public void setInteractionType(InteractionType interactionType) {
        this.interactionType = interactionType;
    }

    @Embeddable
    public static class InteractionId implements Serializable {
        @Column(name = "date", columnDefinition = "DATE")
        private LocalDate date;

        @ManyToOne
        private User sender;

        @ManyToOne
        private User receiver;

        public InteractionId(User sender, User receiver) {
            this.sender = sender;
            this.receiver = receiver;
            this.date = LocalDate.now();
        }

        public InteractionId() {
        }
    }
}
