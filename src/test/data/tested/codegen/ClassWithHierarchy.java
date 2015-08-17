import java.util.Random;

class ClassWithHierarchy extends B {

}

class B extends A {
    private Random random;
}

class A {
    private Matcher matcher;
}