package lab1_dependencyInjection;

import org.reflections.Reflections;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;

import java.lang.reflect.Field;
import java.util.*;

public class MyInjector {

    static Map<Class, Object> container = new HashMap<>();

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, BeanNotFoundException {

        Reflections reflections = new Reflections("lab1_dependencyInjection", new SubTypesScanner(), new TypeAnnotationsScanner(), new FieldAnnotationsScanner());

        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(MyBean.class);
        Set<Field> fields = reflections.getFieldsAnnotatedWith(MyAutowired.class);

        for (Class<?> claz : annotatedClasses) {

                Object c = claz.newInstance();
                container.put(claz, c);
        }

        for(Field field: fields){

            Object className = field.getDeclaringClass();
            Object fieldType = field.getType();

            Object classObject = container.get(className);
            Object fieldObject = container.get(fieldType);

            field.setAccessible(true);
            field.set(classObject, fieldObject);
            System.out.println();
        }

        for(Map.Entry<Class,Object> map: container.entrySet()){
            System.out.println(map.getValue());
        }
        MyInjector my  = new MyInjector();

        my.getBean(new AClass());
    }

    public Object getBean(AClass clazz) throws BeanNotFoundException {

        if(container.containsKey(clazz)){
            return container.get(clazz);
        }else {
            throw new BeanNotFoundException("The class is not in our container");
        }
    }
}
