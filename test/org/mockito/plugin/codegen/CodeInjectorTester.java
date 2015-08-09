package org.mockito.plugin.codegen;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.psi.PsiJavaFile;
import org.mockito.plugin.codegen.CodeInjector;

import static com.intellij.openapi.command.CommandProcessor.getInstance;

/**
 * Created by przemek on 8/9/15.
 */
public class CodeInjectorTester {

    private final CodeInjector underTest;

    public CodeInjectorTester(CodeInjector underTest) {
        this.underTest = underTest;
    }

    public void runAction(final PsiJavaFile psiFile) {
        getInstance().executeCommand(
                psiFile.getProject(), new Runnable() {
                    @Override
                    public void run() {
                        WriteCommandAction.runWriteCommandAction(null, new Runnable() {
                            @Override
                            public void run() {
                                underTest.insert(psiFile);
                            }
                        });
                    }
                }, "", "");
    }
}
