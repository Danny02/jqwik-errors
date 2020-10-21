package de.providers;

import io.vavr.control.Option;
import io.vavr.control.Validation;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.providers.ArbitraryProvider;
import net.jqwik.api.providers.TypeUsage;

import java.util.Set;
import java.util.stream.Collectors;

import static net.jqwik.api.Combinators.combine;

public class ValidationArbProvider implements ArbitraryProvider {

    @Override
    public boolean canProvideFor(TypeUsage typeUsage) {
        return typeUsage.canBeAssignedTo(TypeUsage.forType(Validation.class));
    }

    @Override
    public Set<Arbitrary<?>> provideFor(TypeUsage typeUsage, SubtypeProvider subtypeProvider) {
        var failType = typeUsage.getTypeArgument(0);
        var successType = typeUsage.getTypeArgument(1);

        var failArbs = subtypeProvider.apply(failType);
        var successArbs = subtypeProvider.apply(successType);

        return failArbs.stream().flatMap(f -> successArbs.stream().map(s -> create(f, s))).collect(Collectors.toSet());
    }

    Arbitrary<?> create(Arbitrary<?> fail, Arbitrary<?> success) {
        return combine(fail, success.optional()).as((f, s) -> Option.ofOptional(s).toValidation(f));
    }
}
