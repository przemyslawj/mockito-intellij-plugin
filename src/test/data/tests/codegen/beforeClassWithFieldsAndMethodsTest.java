import org.junit.Test;

import static org.junit.Assert.assertEquals;

class ClassWithFieldsAndMethodsTest {

    private static final int VAL1 = 1;

    private String testValue = "value";

    @Test
    public void testFoo() throws Exception {
        assertEquals(0, getBar());
    }

    private int getBar() {
        return 0;
    }
}