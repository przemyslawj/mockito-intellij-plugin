package org.mockito.plugin.codegen;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by przemek on 8/9/15.
 */
public class FieldsCodeInjector implements CodeInjector {

    public static final String TEST_CLASS_NAME_SUFFIX = "Test";
    public static final String INJECT_MOCKS_CLASS_NAME = "InjectMocks";
    public static final String INJECT_MOCKS_QUALIFIED_NAME = "org.mockito.InjectMocks";
    public static final String UNDER_TEST_FIELD_NAME = "underTest";

    @Override
    public void insert(PsiJavaFile psiJavaFile) {
        ImportOrganizer importOrganizer = new ImportOrganizer(JavaPsiFacade.getInstance(psiJavaFile.getProject()));
        String underTestQualifiedClassName = getUnderTestQualifiedClassName(psiJavaFile);
        if (underTestQualifiedClassName == null) {
            return;
        }

        PsiClass psiClass = psiJavaFile.getClasses()[0];
        Set<String> existingFieldTypeNames = new HashSet<>();
        for (PsiField psiField : psiClass.getFields()) {
            existingFieldTypeNames.add(psiField.getType().getCanonicalText());
        }
        if (!existingFieldTypeNames.contains(underTestQualifiedClassName)) {
            insertUnderTestField(psiJavaFile, underTestQualifiedClassName, psiClass, importOrganizer);
        }

    }

    private void insertUnderTestField(PsiJavaFile psiJavaFile, String underTestClassName, PsiClass psiClass,
                                      ImportOrganizer importOrganizer) {
        Project project = psiJavaFile.getProject();
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
        PsiClassType underTestType = PsiType.getTypeByName(toShortClassName(underTestClassName),
                project, GlobalSearchScope.projectScope(project));
        PsiField underTestField = javaPsiFacade.getElementFactory().createField(
                UNDER_TEST_FIELD_NAME, underTestType);
        underTestField.getModifierList().addAnnotation(INJECT_MOCKS_CLASS_NAME);
        psiClass.add(underTestField);
        importOrganizer.addImport(psiJavaFile, INJECT_MOCKS_QUALIFIED_NAME);
    }

    @NotNull
    private String toShortClassName(String qualifiedClassName) {
        return qualifiedClassName.substring(qualifiedClassName.lastIndexOf('.') + 1, qualifiedClassName.length());
    }

    @NotNull
    private String getUnderTestQualifiedClassName(PsiJavaFile psiJavaFile) {
        String testClassName = psiJavaFile.getClasses()[0].getQualifiedName();
        if (!testClassName.endsWith(TEST_CLASS_NAME_SUFFIX)) {
            return null;
        }
        return testClassName.substring(0, testClassName.length() - TEST_CLASS_NAME_SUFFIX.length());
    }
}
