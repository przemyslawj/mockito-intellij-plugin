import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class ClassWithHierarchyTest {

    @Mock
    private Random random;
    @Mock
    private Matcher matcher;
    @InjectMocks
    private ClassWithHierarchy underTest;
}