package org.mockito.plugin.codegen;

import org.junit.Test;

/**
 * Created by przemek on 8/8/15.
 */
public class RunnerCodeInjectorTest extends MockitoPluginPsiTestCase {

    private RunnerCodeInjector underTest = new RunnerCodeInjector();

    @Test
    public void testInsertSucceedsToInsertMockitoRunner() throws Exception {
        testFile(underTest, "EmptyClassTest.java", "expected/EmptyClassTest.java");
    }

    @Test
    public void testInsertIgnoresFileWithMockitoRunner() throws Exception {
        testFile(underTest, "ClassWithMockitoRunner.java", "ClassWithMockitoRunner.java");
    }
}