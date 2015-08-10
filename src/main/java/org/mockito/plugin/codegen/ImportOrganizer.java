package org.mockito.plugin.codegen;

import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiImportStaticStatement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.ProjectScope;

/**
 * Created by przemek on 8/9/15.
 */
public class ImportOrganizer {

    private final JavaPsiFacade javaPsiFacade;
    private final GlobalSearchScope projectSearchScope;

    public ImportOrganizer(JavaPsiFacade javaPsiFacade) {
        this.javaPsiFacade = javaPsiFacade;
        this.projectSearchScope = ProjectScope.getAllScope(javaPsiFacade.getProject());
    }

    protected void addImport(PsiJavaFile psiJavaFile, String className) {
        PsiClass clazz = javaPsiFacade.findClass(className, projectSearchScope);
        if (clazz != null) {
            psiJavaFile.importClass(clazz);
        }
    }

    protected void addStaticImport(PsiJavaFile psiJavaFile, String className) {
        PsiClass psiClass = javaPsiFacade.findClass(className, projectSearchScope);
        PsiImportStaticStatement importStaticStatement = javaPsiFacade.getElementFactory().createImportStaticStatement(
                psiClass, "*");
        psiJavaFile.getImportList().add(importStaticStatement);
    }
}
