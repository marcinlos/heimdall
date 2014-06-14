package pl.edu.agh.heimdall.example;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;

import pl.edu.agh.heimdall.statistics.Statistics;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public abstract class BasicMethodsScout extends LogThrowCustomMethodBaseScout {

    protected static final int MAX_INTERVENTIONS = 3;
    protected int interventionsCounter = -1;

    protected abstract String observedMethod();

    @Override
    public boolean isLogInterventionType(JoinPoint joinPoint,
            Statistics statistics) {
        return interventionsCounter % MAX_INTERVENTIONS == 0;
    }

    @Override
    public boolean isThrowInterventionType(JoinPoint joinPoint,
            Statistics statistics) {
        return interventionsCounter % MAX_INTERVENTIONS == 1;
    }

    @Override
    public boolean isCustomInterventionType(JoinPoint joinPoint,
            Statistics statistics) {
        return interventionsCounter % MAX_INTERVENTIONS == 2;
    }
    
    private boolean isObservedMethodCall(JoinPoint joinPoint) {
        return getMethodName(joinPoint).equals(observedMethod());
    }
    
    @Override
    protected boolean isInScopeOfInterest(JoinPoint joinPoint,
            Statistics statistics) {
        Object target = joinPoint.getTarget();
        
        if (target != null && isObservedMethodCall(joinPoint)) {
            try {
                target.getClass().getDeclaredMethod(observedMethod());
            } catch (NoSuchMethodException e) {
                ++interventionsCounter;
                return true;
            } catch (SecurityException e) {
                e.printStackTrace(System.err);
            }
        }
        return false;
    }
    
    protected Object getFieldValue(Field f, Object o) throws IllegalAccessException {
        boolean accessibility = f.isAccessible();
        f.setAccessible(true);
        Object val = f.get(o);
        f.setAccessible(accessibility);
        return val;
    }
    
    protected Object[] getFieldValues(Object o) throws IllegalAccessException {
        List<Field> fields = getAllNonStaticFields(o.getClass());
        Object[] vals = new Object[fields.size()];
        
        int i = 0;
        for (Field field : fields) {
            vals[i ++] = getFieldValue(field, o);
        }
        return vals;
    }
    
    protected ArrayList<Field> getAllNonStaticFields(final Class<?> clazz) {
    	Class<?> clazzPointer = clazz;
    	ArrayList<Field> fields = new ArrayList<>();
    	while (clazzPointer != null) {
    		fields.addAll(Arrays.asList(clazzPointer.getDeclaredFields()));
    		clazzPointer = clazzPointer.getSuperclass();
    	}
    	return new ArrayList<Field>(Collections2.filter(fields,
    			new Predicate<Field>() {
    				@Override
    				public boolean apply(Field arg0) {
    					return !Modifier.isStatic(arg0.getModifiers());
    				}
    			}));
    }

}