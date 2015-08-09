package org.mockito.plugin.codegen;

import com.intellij.psi.PsiJavaFile;

/**
 * Created by przemek on 8/9/15.
 */
public interface CodeInjector {

    public void insert(PsiJavaFile psiJavaFile);
}
