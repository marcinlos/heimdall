package pl.edu.agh.heimdall.engine;

import java.lang.reflect.Field;

import org.aspectj.lang.JoinPoint;

import pl.edu.agh.heimdall.statistics.Statistics;

import com.google.common.base.Optional;

public class NotNullScout extends CowardScout {

	@Override
	public Optional<Maneuver> determineManeuverForMethodCall(
			JoinPoint joinPoint, Statistics statistics) {
		for (Object o : joinPoint.getArgs()) {
			if (o == null) {
				return Optional.<Maneuver> of(new DoNothingManeuver() {
					@Override
					public Optional<SpyIntervention> preOperationPhase(
							JoinPoint joinPoint) {
						System.out.println(String.format(
								"Null going to be as argument of %s!!!",
								joinPoint.getSignature()));
						return Optional
								.<SpyIntervention> of(new SpyIntervention() {
									@Override
									public Object impersonateEnemy(Object enemy) {
										System.out.println("Mimic method");
										return null;
									}
								});
					}
				});
			}
		}
		return Optional.<Maneuver> absent();
	}

	@Override
	public Optional<Maneuver> determineManeuverForGetField(JoinPoint joinPoint,
			Statistics statistics, final Field fieldBeingGot) {
		if (String.class.isAssignableFrom(fieldBeingGot.getType())) {
			return Optional.<Maneuver> of(new DoNothingManeuver() {
				@Override
				public Optional<SpyIntervention> preOperationPhase(
						JoinPoint joinPoint) {
					System.out.println(String.format(
							"String going to be as %s field!!!",
							fieldBeingGot.getName()));
					return Optional.<SpyIntervention> of(new SpyIntervention() {
						@Override
						public Object impersonateEnemy(Object enemy) {
							return "VERY UGLY WORD";
						}
					});
				}
			});
		}
		return Optional.<Maneuver> absent();
	}
}