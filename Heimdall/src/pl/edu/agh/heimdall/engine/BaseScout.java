package pl.edu.agh.heimdall.engine;

import java.lang.reflect.Field;

import org.aspectj.lang.JoinPoint;

import pl.edu.agh.heimdall.statistics.Statistics;

import com.google.common.base.Optional;

public abstract class BaseScout implements Scout{

	protected static void writeMethodCommunicateOfSpy(JoinPoint joinPoint, String customPart) {
		String message = String.format("Method %s called - %s",
				joinPoint.getSignature(), customPart);
		System.out.println(message);
	}

	@Override
	public Optional<Maneuver> determineManeuverForMethodCall(
			JoinPoint joinPoint, Statistics statistics) {
		return Optional.<Maneuver>absent();
	}

	@Override
	public Optional<Maneuver> determineManeuverForGetField(JoinPoint joinPoint,
			Statistics statistics, Field fieldBeingGot, Object fieldOwner) {
		return Optional.<Maneuver>absent();
	}

}
