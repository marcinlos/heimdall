package pl.edu.agh.heimdall.engine;

import org.aspectj.lang.JoinPoint;

import com.google.common.base.Optional;

public abstract class DoNothingManeuver implements Maneuver{

	@Override
	public Optional<SpyIntervention> preOperationPhase(JoinPoint joinPoint) {
		return Optional.<SpyIntervention>absent();
	}

	@Override
	public void postOperationPhase(JoinPoint joinPoint) {
	}

}
