import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class ClassWithMockitoRunnerTest {

    @InjectMocks
    private ClassWithMockitoRunner underTest;
}