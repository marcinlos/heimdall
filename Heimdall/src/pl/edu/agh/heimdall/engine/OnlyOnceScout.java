package pl.edu.agh.heimdall.engine;

import org.aspectj.lang.JoinPoint;

import pl.edu.agh.heimdall.statistics.Statistics;

import com.google.common.base.Optional;

public class OnlyOnceScout extends BaseScout {
	@Override
	public Optional<Maneuver> determineManeuverForMethodCall(
			JoinPoint joinPoint, Statistics statistics) {
		int invocationTimes = statistics.timesOfMethodOnObjectInvokation(
				joinPoint.getTarget(), joinPoint.getSignature().getName());
		return invocationTimes != 0 ? Optional
				.<Maneuver> of(new BaseManeuver() {

					@Override
					public Optional<SpyIntervention> preOperationPhase(
							JoinPoint joinPoint) {
						System.out
								.println("WARNING: Method is to be called second or more time ("
										+ joinPoint.getSignature() + ")");
						return super.preOperationPhase(joinPoint);
					}
				})
				: super.determineManeuverForMethodCall(joinPoint, statistics);
	}
}
