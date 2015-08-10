package org.mockito.plugin.codegen;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.util.PsiUtil;

/**
 * Created by przemek on 8/10/15.
 */
public class MockitoPluginUtils {

    /**
     * Returns unit test class declared in this file. Assumes that there is only one outer, non-abstract class in the
     * file.
     */
    public static PsiClass getUnitTestClass(PsiJavaFile psiJavaFile) {
        for (PsiClass psiClass : psiJavaFile.getClasses()) {
            if (!PsiUtil.isInnerClass(psiClass) && !PsiUtil.isAbstractClass(psiClass)) {
                return psiClass;
            }
        }
        throw new IllegalStateException("Could not find a unit test class in file: " + psiJavaFile.getName());
    }
}
