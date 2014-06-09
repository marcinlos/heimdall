package pl.edu.agh.heimdall;

import org.aspectj.lang.JoinPoint;

public interface ViolationResolver {
	void resolve(JoinPoint joinPoint);
}
