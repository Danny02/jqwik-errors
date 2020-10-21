package de;

import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.Test;

public class UseSampleTest {

    @Test
    void useSample() {
        // strings uses EdgeCasesFacadeImpl, i.e. ints() does not
        Arbitraries.strings().sample();
    }
}
