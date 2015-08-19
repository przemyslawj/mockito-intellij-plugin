package org.mockito.plugin.codegen;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ContentEntry;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.newvfs.impl.VfsRootAccess;
import com.intellij.testFramework.LightProjectDescriptor;
import com.intellij.testFramework.PsiTestUtil;
import com.intellij.testFramework.fixtures.DefaultLightProjectDescriptor;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import junit.framework.ComparisonFailure;
import org.jetbrains.annotations.NotNull;
import org.mockito.plugin.action.GenMockitoCodeAction;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by przemek on 8/9/15.
 */
public abstract class MockitoPluginPsiTestCase extends LightCodeInsightFixtureTestCase {

    static class MockitoProjectDescriptor extends DefaultLightProjectDescriptor {
        @Override
        public void configureModule(@NotNull Module module, @NotNull ModifiableRootModel model,
                                    @NotNull ContentEntry contentEntry) {
            PsiTestUtil.addLibrary(module, model, "mockito",  "lib", "mockito-all-1.10.19.jar");
            PsiTestUtil.addLibrary(module, model, "junit",  "lib", "junit-4.11.jar");
            super.configureModule(module, model, contentEntry);
        }
    }

    private static final MockitoProjectDescriptor DESCRIPTOR = new MockitoProjectDescriptor();

    @NotNull
    @Override
    protected LightProjectDescriptor getProjectDescriptor() {
        return DESCRIPTOR;
    }

    @Override
    protected String getTestDataPath() {
        return ".";
    }

    @Override
    protected String getBasePath() {
        return "src/test/data/tests";
    }

    @NotNull
    private String getTestDataPath(String inputFileName) {
        return getBasePath() + "/" + getTestDataPath() + "/" + inputFileName;
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        addTestedClassesToFixture();
    }

    private void addTestedClassesToFixture() throws IOException {
        String testedPath = "src/test/data/tested";
        String testedFiles = new File(testedPath).getCanonicalPath();
        String absoluteTestDataPath = new File(getTestDataPath(), getBasePath()).getCanonicalPath();
        VfsRootAccess.allowRootAccess(testedFiles, absoluteTestDataPath);
        loadFilesFrom(testedPath);
    }

    private void loadFilesFrom(final String srcPath) {
        List<File> filesByMask = FileUtil.findFilesByMask(Pattern.compile(".*\\.java"), new File(srcPath));
        for (File javaFile : filesByMask) {
            String filePath = javaFile.getPath().replace("\\", "/");
            myFixture.copyFileToProject(filePath, filePath.substring(srcPath.length() + 1));
        }
    }

    public void testFile(String inputFileName, String expectedFile) throws Exception {
        final String path = getTestDataPath(inputFileName);
        myFixture.configureByFile(path);
        myFixture.testAction(new GenMockitoCodeAction());
        assertChanges(expectedFile);
    }

    private void assertChanges(String expectedFile) throws Exception {
        try {
            myFixture.checkResultByFile(getBasePath() + "/" + expectedFile, true);
        } catch (ComparisonFailure ex) {
            String actualFileText = myFixture.getFile().getText();
            String expectedFileText = FileUtil.loadFile(new File(getTestDataPath(expectedFile)));
            assertEquals(expectedFileText, actualFileText);
        }
    }

}
