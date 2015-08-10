import java.util.Random;

class ClassWithFieldsAndMethods {

    private int maxInt = 2;
    private Random random;

    public void foo() {
        System.out.println(random.nextInt(maxInt));
    }

}