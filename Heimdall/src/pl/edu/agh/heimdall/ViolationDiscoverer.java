package pl.edu.agh.heimdall;

import org.aspectj.lang.JoinPoint;

public interface ViolationDiscoverer {
	boolean validate(JoinPoint joinPoint);
}
