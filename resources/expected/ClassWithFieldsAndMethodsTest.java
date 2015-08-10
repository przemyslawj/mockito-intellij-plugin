package org.mockito.plugin.action;

import org.junit.Test;
import org.mockito.Mock;

class ClassWithFieldsAndMethodsTest {

    private static final int VAL1 = 1;

    private String testValue = "value";

    @Mock
    private Random random;
    @InjectMocks
    private ClassWithFieldsAndMethods underTest;

    @Test
    public void testFoo() throws Exception {
        assertEquals(0, getBar());
    }

    private int getBar() {
        return 0;
    }
}