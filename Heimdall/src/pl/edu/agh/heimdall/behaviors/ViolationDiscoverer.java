package pl.edu.agh.heimdall.behaviors;

import org.aspectj.lang.JoinPoint;

import pl.edu.agh.heimdall.statistics.Statistics;

public interface ViolationDiscoverer {
	boolean validateMethodCall(JoinPoint joinPoint, Statistics statistics);
	boolean validateGetField(JoinPoint joinPoint, Statistics statistics);
}
