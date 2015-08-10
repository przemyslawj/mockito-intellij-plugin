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
    public static final String INJECT_MOCKS_ANNOTATION_QUALIFIED_NAME = "org.mockito.InjectMocks";
    public static final String MOCK_ANNOTATION_QUALIFIED_NAME = "org.mockito.Mock";
    public static final String MOCK_ANNOTATION_SHORT_NAME = "org.mockito.Mock";
    public static final String UNDER_TEST_FIELD_NAME = "underTest";

    @Override
    public void insert(PsiJavaFile psiJavaFile) {
        Project project = psiJavaFile.getProject();
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
        ImportOrganizer importOrganizer = new ImportOrganizer(javaPsiFacade);
        String underTestQualifiedClassName = getUnderTestQualifiedClassName(psiJavaFile);
        if (underTestQualifiedClassName == null) {
            return;
        }

        PsiClass psiClass = psiJavaFile.getClasses()[0];
        Set<String> existingFieldTypeNames = getFieldTypeNames(psiClass);
        if (!existingFieldTypeNames.contains(underTestQualifiedClassName)) {
            insertUnderTestField(project, psiClass, underTestQualifiedClassName);
            importOrganizer.addImport(psiJavaFile, INJECT_MOCKS_ANNOTATION_QUALIFIED_NAME);
        }

        PsiClass underTestPsiClass = javaPsiFacade.findClass(
                underTestQualifiedClassName, GlobalSearchScope.allScope(project));
        if (underTestPsiClass == null) {
            return;
        }
        boolean addedMocks = false;
        for (PsiField psiField : underTestPsiClass.getFields()) {
            if (isNotPrimitive(psiField) &&
                    !existingFieldTypeNames.contains(psiField.getName())) {
                insertMockedField(project, psiClass, psiField.getType().getCanonicalText());
                addedMocks = true;
            }
        }
        if (addedMocks) {
            importOrganizer.addImport(psiJavaFile, MOCK_ANNOTATION_QUALIFIED_NAME);
        }
    }

    private boolean isNotPrimitive(PsiField psiField) {
        return !(psiField.getType() instanceof PsiPrimitiveType);
    }

    @NotNull
    private Set<String> getFieldTypeNames(PsiClass psiClass) {
        Set<String> existingFieldTypeNames = new HashSet<>();
        for (PsiField psiField : psiClass.getFields()) {
            existingFieldTypeNames.add(psiField.getType().getCanonicalText());
        }
        return existingFieldTypeNames;
    }

    private void insertUnderTestField(Project project, PsiClass psiClass, String fullyQualifiedTypeName) {
        insertNewField(project, psiClass, fullyQualifiedTypeName, UNDER_TEST_FIELD_NAME, INJECT_MOCKS_CLASS_NAME);
    }

    private void insertMockedField(Project project, PsiClass psiClass, String fullyQualifiedTypeName) {
        String newFieldName = toShortClassName(fullyQualifiedTypeName);
        newFieldName = Character.toLowerCase(newFieldName.charAt(0)) +
                newFieldName.substring(1, newFieldName.length());

        insertNewField(project, psiClass, fullyQualifiedTypeName, newFieldName, MOCK_ANNOTATION_SHORT_NAME);
    }

    private void insertNewField(Project project, PsiClass psiClass,
                                String newFieldTypeName, String newFieldName, String annotationClassName) {
        JavaPsiFacade javaPsiFacade = JavaPsiFacade.getInstance(project);
        PsiClassType newFieldType = PsiType.getTypeByName(newFieldTypeName,
                project, GlobalSearchScope.projectScope(project));
        PsiField underTestField = javaPsiFacade.getElementFactory().createField(
                newFieldName, newFieldType);
        underTestField.getModifierList().addAnnotation(annotationClassName);
        psiClass.add(underTestField);
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
