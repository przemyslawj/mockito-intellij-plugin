import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class EmptyClassTest {

    @InjectMocks
    private EmptyClass underTest;
}