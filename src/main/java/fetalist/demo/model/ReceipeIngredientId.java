package fetalist.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class ReceipeIngredientId implements Serializable {
    private Receipe idReceipe;
    private Ingredient idIngredient;
    public ReceipeIngredientId() {}
    public ReceipeIngredientId(Receipe idReceipe, Ingredient idIngredient) {
        this.idReceipe = idReceipe;
        this.idIngredient = idIngredient;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        ReceipeIngredientId pk = (ReceipeIngredientId) o;
        return Objects.equals( idReceipe, pk.idReceipe ) &&
                Objects.equals( idIngredient, pk.idIngredient );
    }

    @Override
    public int hashCode() {
        return Objects.hash( idReceipe, idIngredient );
    }
}
