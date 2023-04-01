package imprezy.elblag.hit.postgres.models;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table( name = "friends" )
public class Friend {
    @EmbeddedId
    private FriendId friendId;

    private FriendStatus friendStatus;

    public static class FriendId implements Serializable {
        @ManyToOne
        private User sender;

        @ManyToOne
        private User receiver;
    }
}
