package pl.edu.agh.heimdall.behaviors;

import org.aspectj.lang.JoinPoint;

public interface Maneuver {
//	void resolveMethodCall(JoinPoint joinPoint);
//	void resolveGetField(JoinPoint joinPoint);
	
	boolean preOperationPhase(JoinPoint joinPoint);
	void postOperationPhase(JoinPoint joinPoint);
	
}
