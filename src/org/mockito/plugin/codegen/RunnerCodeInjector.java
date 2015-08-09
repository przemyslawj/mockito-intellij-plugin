package org.mockito.plugin.codegen;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

/**
 * Created by przemek on 8/8/15.
 */
public class RunnerCodeInjector implements CodeInjector {

    public static final String MOCKITO_RUNNER_QUALIFIED_CLASS_NAME = "org.mockito.runners.MockitoJUnitRunner";
    public static final String RUN_WITH_SHORT_CLASS_NAME = "RunWith";
    public static final String RUN_WITH_QUALIFIED_CLASS_NAME = "org.junit.runner." + RUN_WITH_SHORT_CLASS_NAME;

    public void insert(PsiJavaFile psiJavaFile) {
        Project project = psiJavaFile.getProject();
        ImportOrganizer importOrganizer = new ImportOrganizer(JavaPsiFacade.getInstance(project));

        PsiClass psiClass = chooseTestClass(psiJavaFile.getClasses());
        PsiModifierList modifierList = psiClass.getModifierList();
        if (!containsRunnerAnnotation(modifierList)) {
            modifierList.addAnnotation("RunWith(MockitoJUnitRunner.class)");
            importOrganizer.addImport(psiJavaFile, MOCKITO_RUNNER_QUALIFIED_CLASS_NAME);
            importOrganizer.addImport(psiJavaFile, RUN_WITH_QUALIFIED_CLASS_NAME);
        }
    }

    private boolean containsRunnerAnnotation(PsiModifierList modifierList) {
        for (PsiAnnotation psiAnnotation : modifierList.getAnnotations()) {
            if (psiAnnotation.getQualifiedName().endsWith(RUN_WITH_SHORT_CLASS_NAME)) {
                return true;
            }
        }
        return false;
    }

    private PsiClass chooseTestClass(PsiClass[] classes) {
        //TODO(przemek) fix for multiple classes, should return the outer class
        return classes[0];
    }
}
