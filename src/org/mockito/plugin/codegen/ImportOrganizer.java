package org.mockito.plugin.codegen;

import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.search.ProjectScope;

/**
 * Created by przemek on 8/9/15.
 */
public class ImportOrganizer {

    private final JavaPsiFacade javaPsiFacade;

    public ImportOrganizer(JavaPsiFacade javaPsiFacade) {
        this.javaPsiFacade = javaPsiFacade;
    }

    protected void addImport(PsiJavaFile psiJavaFile, String className) {
        PsiClass mockitoRunnerClass = javaPsiFacade.findClass(className,
                ProjectScope.getAllScope(psiJavaFile.getProject()));
        if (mockitoRunnerClass != null) {
            psiJavaFile.importClass(mockitoRunnerClass);
        }
    }
}
