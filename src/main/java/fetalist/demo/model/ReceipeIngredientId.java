package fetalist.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class ReceipeIngredientId implements Serializable {
    private Receipe receipe;
    private Ingredient ingredient;
    public ReceipeIngredientId() {}
    public ReceipeIngredientId(Receipe receipe, Ingredient ingredient) {
        this.receipe = receipe;
        this.ingredient = ingredient;
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
        return Objects.equals( receipe, pk.receipe ) &&
                Objects.equals( ingredient, pk.ingredient );
    }

    @Override
    public int hashCode() {
        return Objects.hash( receipe, ingredient );
    }
}
