package org.mockito.plugin.codegen;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.intellij.psi.PsiJavaFile;
import com.intellij.testFramework.PsiTestCase;

import java.io.IOException;
import java.net.URL;

/**
 * Created by przemek on 8/9/15.
 */
public class MockitoPluginPsiTestCase extends PsiTestCase {

    public void testFile(CodeInjector underTest, PsiJavaFile inputPsiFile, String expectedFile) throws Exception {

        CodeInjectorTester tester = new CodeInjectorTester(underTest);
        tester.runAction(inputPsiFile);

        assertChanges(expectedFile, inputPsiFile);
    }

    public void testFile(CodeInjector underTest, String inputFileName, String expectedFile) throws Exception {
        PsiJavaFile psiFile = (PsiJavaFile) this.createFile(inputFileName, loadFile(inputFileName));
        testFile(underTest, psiFile, expectedFile);
    }

    private void assertChanges(String expectedFile, PsiJavaFile psiFile) throws IOException {
        String actualText = psiFile.getText();
        String expectedText = loadFile(expectedFile);
        assertEquals(expectedText, actualText);
    }

    @Override
    protected String loadFile(String fileName) throws IOException {
        URL resource = Resources.getResource(fileName);
        return Resources.toString(resource, Charsets.UTF_8);
    }
}
