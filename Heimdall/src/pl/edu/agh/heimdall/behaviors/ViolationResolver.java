package pl.edu.agh.heimdall.behaviors;

import org.aspectj.lang.JoinPoint;

public interface ViolationResolver {
	void resolveMethodCall(JoinPoint joinPoint);
	void resolveGetField(JoinPoint joinPoint);
	
}
