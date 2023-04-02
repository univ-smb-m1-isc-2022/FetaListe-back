package fetalist.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class FriendId implements Serializable {
    private Users user1;

    private Users user2;

    public FriendId() {
    }

    public FriendId(Users user1, Users user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendId friendId = (FriendId) o;
        return user1.equals(friendId.user1) && user2.equals(friendId.user2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1, user2);
    }
}
