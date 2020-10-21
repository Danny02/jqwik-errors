package de;

import io.vavr.control.Validation;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;

import java.util.function.Function;

public class PureFunctionSpec {

    @Property
    <T> boolean isPure(@ForAll T t, @ForAll Function<T, Validation<T, String>> f) {
           return f.apply(t).equals(f.apply(t));
    }
}
