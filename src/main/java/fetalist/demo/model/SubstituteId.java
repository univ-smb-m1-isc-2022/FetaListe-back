package fetalist.demo.model;

import java.io.Serializable;
import java.util.Objects;

public class SubstituteId implements Serializable {
    private Ingredient baseIngredient;
    private Ingredient substitute;

    public SubstituteId() {
    }

    public SubstituteId(Ingredient baseIngredient, Ingredient substitute) {
        this.baseIngredient = baseIngredient;
        this.substitute = substitute;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubstituteId that = (SubstituteId) o;
        return baseIngredient.equals(that.baseIngredient) && substitute.equals(that.substitute);
    }

    @Override
    public int hashCode() {
        return Objects.hash(baseIngredient, substitute);
    }
}
