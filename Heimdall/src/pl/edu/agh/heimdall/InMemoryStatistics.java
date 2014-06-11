package pl.edu.agh.heimdall;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import com.google.common.base.Preconditions;

public class InMemoryStatistics implements Statistics{
	
	private static final Object STATIC_REPRESENTANT = new Object();

	WeakHashMap<Object, List<MethodInvocation>> methodsInvocation = new WeakHashMap<>(); 
	
	@Override
	public void addInvocationOf(Object o, String fullyQualifiedMethodName){
		Preconditions.checkNotNull(o);
		Preconditions.checkNotNull(fullyQualifiedMethodName);
		List<MethodInvocation> invocationsForObject = methodsInvocation.get(o);
		if(invocationsForObject==null){
			invocationsForObject = new ArrayList<MethodInvocation>();
			methodsInvocation.put(o, invocationsForObject);
		}
		int oldInvocationsValue = timesOfMethodOnObjectInvokation(o, fullyQualifiedMethodName);
		invocationsForObject.add(MethodInvocation.create(fullyQualifiedMethodName, oldInvocationsValue+1));
	}
	
	@Override
	public void addStaticIvocation(String fullyQualifiedMethodName){
		addInvocationOf(STATIC_REPRESENTANT, fullyQualifiedMethodName);
	}
	
	@Override
	public boolean wasMethodOnObjectInvokedOnce(Object o, String methodName) {
		return timesOfMethodOnObjectInvokation(o, methodName)==1;
	}

	@Override
	public int timesOfMethodOnObjectInvokation(Object o, String methodName) {
		List<MethodInvocation> methodInvocations = methodsInvocation.get(o);
		if(methodInvocations==null){
			return 0;
		}
		for(MethodInvocation invocation: methodInvocations){
			if(invocation.getFullyQualifiedName().equals(methodName)){
				return invocation.getInvocationsNumber();
			}
		}
		return 0;
	}

	@Override
	public boolean wasStaticMethodInvokedOnce(String methodName) {
		return timesOfStaticMethodInvokation(methodName)==1;
	}

	@Override
	public int timesOfStaticMethodInvokation(String methodName) {
		return timesOfMethodOnObjectInvokation(STATIC_REPRESENTANT, methodName);
	}

}
