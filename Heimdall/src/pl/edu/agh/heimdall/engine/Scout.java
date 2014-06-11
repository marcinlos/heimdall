package pl.edu.agh.heimdall.engine;

import org.aspectj.lang.JoinPoint;

import pl.edu.agh.heimdall.statistics.Statistics;

import com.google.common.base.Optional;

public interface Scout {
	Optional<Maneuver> determineManeuverForMethodCall(JoinPoint joinPoint, Statistics statistics);
	Optional<Maneuver> determineManeuverForGetField(JoinPoint joinPoint, Statistics statistics);
}
