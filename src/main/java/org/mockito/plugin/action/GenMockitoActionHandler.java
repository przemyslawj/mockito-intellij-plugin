package org.mockito.plugin.action;

import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorWriteActionHandler;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiJavaFile;
import org.mockito.plugin.codegen.FieldsCodeInjector;
import org.mockito.plugin.codegen.ImportOrganizer;
import org.mockito.plugin.codegen.RunnerCodeInjector;

/**
 * Created by przemek on 8/8/15.
 */
public class GenMockitoActionHandler extends EditorWriteActionHandler {


    @Override
    public void executeWriteAction(Editor editor, Caret caret, DataContext dataContext) {
        PsiJavaFile psiJavaFile = (PsiJavaFile) dataContext.getData(CommonDataKeys.PSI_FILE.getName());
        ImportOrganizer importOrganizer = new ImportOrganizer(JavaPsiFacade.getInstance(psiJavaFile.getProject()));

        RunnerCodeInjector runnerCodeInjector = new RunnerCodeInjector(psiJavaFile, importOrganizer);
        runnerCodeInjector.inject();
        FieldsCodeInjector fieldsCodeInjector = new FieldsCodeInjector(psiJavaFile, importOrganizer);
        fieldsCodeInjector.inject();

    }
}
