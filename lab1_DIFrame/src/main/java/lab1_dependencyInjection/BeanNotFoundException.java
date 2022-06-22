package lab1_dependencyInjection;

public class BeanNotFoundException extends Exception{

    public BeanNotFoundException(String e) {
        super(e);
    }
}
