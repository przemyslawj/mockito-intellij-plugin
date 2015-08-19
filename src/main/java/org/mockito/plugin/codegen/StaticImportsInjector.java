package org.mockito.plugin.codegen;

import com.intellij.psi.PsiImportStaticStatement;
import com.intellij.psi.PsiJavaFile;

/**
 * Inserts code for static imports for Mockito.
 *
 * Created by przemek on 8/10/15.
 */
public class StaticImportsInjector implements CodeInjector {

    public static final String MOCKITO_FULLY_QUALIFIED_CLASS_NAME = "org.mockito.Mockito";
    public static final String GROUPED_MOCKITO_STATIC_IMPORT = MOCKITO_FULLY_QUALIFIED_CLASS_NAME + ".*";

    private final PsiJavaFile psiJavaFile;
    private final ImportOrganizer importOrganizer;

    public StaticImportsInjector(PsiJavaFile psiJavaFile, ImportOrganizer importOrganizer) {
        this.psiJavaFile = psiJavaFile;
        this.importOrganizer = importOrganizer;
    }

    @Override
    public void inject() {
        for (PsiImportStaticStatement staticImport : psiJavaFile.getImportList().getImportStaticStatements()) {
            if (staticImport.getText().contains(GROUPED_MOCKITO_STATIC_IMPORT)) {
                return;
            }
        }
        importOrganizer.addStaticImportForAllMethods(psiJavaFile, MOCKITO_FULLY_QUALIFIED_CLASS_NAME);
    }
}
