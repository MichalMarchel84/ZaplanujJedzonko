package pl.coderslab.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityFactory<T> {

    private final Class<T> TYPE;
    private final Constructor<T> CONSTRUCTOR;
    private final Method[] SETTERS;

    public EntityFactory(Class<T> type) throws NoSuchMethodException {

        this.TYPE = type;
        this.CONSTRUCTOR = TYPE.getConstructor();

        Field[] fields = TYPE.getDeclaredFields();
        SETTERS = new Method[fields.length];
        for (int i = 0; i < fields.length; i++) {
            SETTERS[i] = TYPE.getMethod(Conversions.fieldToSetter(fields[i].getName()), fields[i].getType());
        }
    }

    public T getEntity(ResultSet set) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {

        T instance = null;
        if(set.next()) instance = build(set);
        return instance;
    }

    public List<T> getAsList(ResultSet set) throws SQLException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<T> list = new ArrayList<>();
        while (set.next()){
            list.add(build(set));
        }
        return list;
    }

    private T build(ResultSet set) throws InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {

        T instance = CONSTRUCTOR.newInstance();
        for (int i = 0; i < SETTERS.length; i++) {
            SETTERS[i].invoke(instance, set.getObject(i + 1, SETTERS[i].getParameterTypes()[0]));
        }
        return instance;
    }


}
