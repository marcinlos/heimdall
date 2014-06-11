package pl.edu.agh.heimdall.engine;

import org.aspectj.lang.JoinPoint;

import com.google.common.base.Optional;

public interface Maneuver {
	
	Optional<SpyIntervention> preOperationPhase(JoinPoint joinPoint);
	void postOperationPhase(JoinPoint joinPoint);
	
}
