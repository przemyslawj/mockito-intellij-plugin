package org.mockito.plugin.codegen;

import com.intellij.psi.PsiJavaFile;

/**
 * Inserts code for static imports for Mockito.
 *
 * Created by przemek on 8/10/15.
 */
public class StaticImportsInjector implements CodeInjector {

    private final PsiJavaFile psiJavaFile;
    private final ImportOrganizer importOrganizer;

    public StaticImportsInjector(PsiJavaFile psiJavaFile, ImportOrganizer importOrganizer) {
        this.psiJavaFile = psiJavaFile;
        this.importOrganizer = importOrganizer;
    }

    @Override
    public void inject() {
        importOrganizer.addStaticImport(psiJavaFile, "org.mockito.Mockito");
    }
}
