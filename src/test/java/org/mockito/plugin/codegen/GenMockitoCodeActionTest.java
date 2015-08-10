package org.mockito.plugin.codegen;

import org.junit.Test;

/**
 * Created by przemek on 8/9/15.
 */
public class GenMockitoCodeActionTest extends MockitoPluginPsiTestCase {

    @Test
    public void testInsertIgnoresFileWithMockitoRunner() throws Exception {
        testFile("codegen/beforeClassWithMockitoRunner.java", "codegen/afterClassWithMockitoRunner.java");
    }

    @Test
    public void testInsertUnderTestFieldWhenUnderTestFieldAdded() throws Exception {
        testFile("codegen/beforeEmptyClassTest.java", "codegen/afterEmptyClassTest.java");
    }

    @Test
    public void testInsertUnderTestFieldWhenItIsAlreadyPresent() throws Exception {
        testFile("codegen/beforeClassWithUnderTestField.java", "codegen/afterClassWithUnderTestField.java");
    }

    @Test
    public void testInsertUnderTestFieldWhenClassHasOtherFields() throws Exception {
        testFile("codegen/beforeClassWithFieldsAndMethodsTest.java",
                "codegen/afterClassWithFieldsAndMethodsTest.java");
    }
}