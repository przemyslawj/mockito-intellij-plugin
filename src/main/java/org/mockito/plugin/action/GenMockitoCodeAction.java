package org.mockito.plugin.action;

import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorAction;
import com.intellij.psi.PsiFile;

/**
 * Created by przemek on 8/8/15.
 */
public class GenMockitoCodeAction extends EditorAction {

    public static final String TEST_JAVA_FILE_NAME_SUFFIX = "Test.java";

    public GenMockitoCodeAction() {
        super(new GenMockitoActionHandler());
    }

    /**
     * Enables action for test Java files only.
     */
    @Override
    public void update(Editor editor, Presentation presentation, DataContext dataContext) {
        PsiFile psiFile = (PsiFile) dataContext.getData(CommonDataKeys.PSI_FILE.getName());
        boolean enabled = false;
        if (psiFile != null) {
            enabled = psiFile.getName().endsWith(TEST_JAVA_FILE_NAME_SUFFIX);
        }
        presentation.setEnabled(enabled);
    }
}
