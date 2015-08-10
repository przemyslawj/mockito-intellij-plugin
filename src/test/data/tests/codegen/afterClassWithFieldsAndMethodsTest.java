import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class ClassWithFieldsAndMethodsTest {

    private static final int VAL1 = 1;

    private String testValue = "value";
    @Mock
    private Random random;
    @Mock
    private List<String> stringList;
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