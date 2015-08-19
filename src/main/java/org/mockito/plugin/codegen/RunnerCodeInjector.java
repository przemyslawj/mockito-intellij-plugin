package org.mockito.plugin.codegen;

import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiModifierList;

/**
 * Inserts annotation MockitoJUnitRunner.class annotation for the test.
 *
 * Created by przemek on 8/8/15.
 */
public class RunnerCodeInjector implements CodeInjector {

    public static final String MOCKITO_RUNNER_QUALIFIED_CLASS_NAME = "org.mockito.runners.MockitoJUnitRunner";
    public static final String RUN_WITH_SHORT_CLASS_NAME = "RunWith";
    public static final String RUN_WITH_QUALIFIED_CLASS_NAME = "org.junit.runner." + RUN_WITH_SHORT_CLASS_NAME;

    private final PsiJavaFile psiJavaFile;
    private final ImportOrganizer importOrganizer;

    public RunnerCodeInjector(PsiJavaFile psiJavaFile, ImportOrganizer importOrganizer) {
        this.psiJavaFile = psiJavaFile;
        this.importOrganizer = importOrganizer;
    }

    public void inject() {
        PsiClass psiClass = MockitoPluginUtils.getUnitTestClass(psiJavaFile);
        PsiModifierList modifierList = psiClass.getModifierList();
        if (!containsRunnerAnnotation(modifierList)) {
            modifierList.addAnnotation("RunWith(MockitoJUnitRunner.class)");
            importOrganizer.addClassImport(psiJavaFile, MOCKITO_RUNNER_QUALIFIED_CLASS_NAME);
            importOrganizer.addClassImport(psiJavaFile, RUN_WITH_QUALIFIED_CLASS_NAME);
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

}
