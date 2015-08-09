package org.mockito.plugin.action;

import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
import com.intellij.psi.PsiJavaFile;
import org.mockito.plugin.codegen.FieldsCodeInjector;
import org.mockito.plugin.codegen.RunnerCodeInjector;

/**
 * Created by przemek on 8/8/15.
 */
public class GenMockitoActionHandler extends EditorWriteActionHandler {

    private final RunnerCodeInjector runnerCodeInjector = new RunnerCodeInjector();
    private final FieldsCodeInjector fieldsCodeInjector = new FieldsCodeInjector();

    @Override
    public void executeWriteAction(Editor editor, Caret caret, DataContext dataContext) {
        PsiJavaFile psiJavaFile = (PsiJavaFile) dataContext.getData(CommonDataKeys.PSI_FILE.getName());
        fieldsCodeInjector.insert(psiJavaFile);
        runnerCodeInjector.insert(psiJavaFile);

    }
}
