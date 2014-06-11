package pl.edu.agh.heimdall;

public interface Statistics {
	boolean wasMethodOnObjectInvokedOnce(Object o, String methodName);
	int timesOfMethodOnObjectInvokation(Object o, String methodName);
	
	boolean wasStaticMethodInvokedOnce(String methodName);
	int timesOfStaticMethodInvokation(String methodName);
	
	void addStaticIvocation(String fullyQualifiedMethodName);
	void addInvocationOf(Object o, String fullyQualifiedMethodName);
}
