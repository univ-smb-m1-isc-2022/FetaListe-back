package fetalist.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class FavoriteId implements Serializable {
    private Users users;
    private Receipe receipe;
    public FavoriteId() {}
    public FavoriteId(Users users, Receipe receipe) {
        this.users = users;
        this.receipe = receipe;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        FavoriteId pk = (FavoriteId) o;
        return Objects.equals( users, pk.users ) &&
                Objects.equals( receipe, pk.receipe );
    }

    @Override
    public int hashCode() {
        return Objects.hash( users, receipe );
    }
}
