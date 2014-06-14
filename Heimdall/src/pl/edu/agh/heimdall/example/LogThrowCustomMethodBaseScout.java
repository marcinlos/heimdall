package pl.edu.agh.heimdall.example;

import org.aspectj.lang.JoinPoint;

import com.google.common.base.Optional;
import com.google.common.util.concurrent.UncheckedExecutionException;

import pl.edu.agh.heimdall.engine.BaseManeuver;
import pl.edu.agh.heimdall.engine.BaseScout;
import pl.edu.agh.heimdall.engine.Maneuver;
import pl.edu.agh.heimdall.engine.SpyIntervention;
import pl.edu.agh.heimdall.statistics.Statistics;

public abstract class LogThrowCustomMethodBaseScout extends BaseScout {

	public static enum InterventionType {
		LOG, THROW, CUSTOM;
	}

	public abstract boolean isLogInterventionType(JoinPoint joinPoint,
			Statistics statistics);

	public abstract boolean isThrowInterventionType(JoinPoint joinPoint,
			Statistics statistics);

	public abstract boolean isCustomInterventionType(JoinPoint joinPoint,
			Statistics statistics);

	@Override
	public Optional<Maneuver> determineManeuverForMethodCall(
			JoinPoint joinPoint, Statistics statistics) {
		if (!isInScopeOfInterest(joinPoint, statistics)) {
			return Optional.absent();
		}
		Optional<Maneuver> toReturn = Optional.absent();
		if (isLogInterventionType(joinPoint, statistics)) {
			toReturn = defineLogManeuver();
		} else if (isThrowInterventionType(joinPoint, statistics)) {
			toReturn = defineThrowManeuver();
		} else if (isCustomInterventionType(joinPoint, statistics)) {
			toReturn = defineCustomManeuver(joinPoint, statistics);
		}
		return toReturn;
	}

	protected abstract boolean isInScopeOfInterest(JoinPoint joinPoint,
			Statistics statistics);

	private final Optional<Maneuver> defineThrowManeuver() {
		return Optional.<Maneuver> of(new BaseManeuver() {
			@Override
			public Optional<SpyIntervention> preOperationPhase(
					JoinPoint joinPoint) {
				writeMethodCommunicateOfSpy(joinPoint,
						getThrowCommunicateMessage());
				throw createException(joinPoint);
			}
		});
	}

	private final Optional<Maneuver> defineLogManeuver() {
		return Optional.<Maneuver> of(new BaseManeuver() {
			@Override
			public Optional<SpyIntervention> preOperationPhase(
					JoinPoint joinPoint) {
				writeMethodCommunicateOfSpy(joinPoint,
						getLogCommunicateMessage());
				return Optional.absent();
			}
		});
	}

	protected abstract RuntimeException createException(JoinPoint joinPoint);

	protected abstract Optional<Maneuver> defineCustomManeuver(
			JoinPoint joinPoint, Statistics statistics);

	protected abstract String getLogCommunicateMessage();

	protected abstract String getThrowCommunicateMessage();

	protected String getMethodName(JoinPoint joinPoint) {
		return joinPoint.getSignature().getName();
	}

}
