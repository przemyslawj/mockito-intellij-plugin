package org.mockito.plugin.codegen;

import org.junit.Test;

/**
 * Created by przemek on 8/9/15.
 */
public class FieldsCodeInjectorTest extends MockitoPluginPsiTestCase {

    FieldsCodeInjector underTest = new FieldsCodeInjector();

    @Test
    public void testInsertUnderTestField() throws Exception {
        testFile(underTest, "EmptyClassTest.java", "expected/UnderTestFieldAdded.java");
    }

    @Test
    public void testInsertUnderTestFieldWhenItIsAlreadyPresent() throws Exception {
        testFile(underTest, "ClassWithUnderTestField.java", "ClassWithUnderTestField.java");
    }

    @Test
    public void testInsertUnderTestFieldWhenClassHasOtherFields() throws Exception {
        testFile(underTest, "ClassWithFieldsAndMethodsTest.java", "expected/ClassWithFieldsAndMethodsTest.java");
    }
}