package pl.edu.agh.heimdall.example;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aspectj.lang.JoinPoint;

import pl.edu.agh.heimdall.engine.BaseScout;
import pl.edu.agh.heimdall.engine.BaseManeuver;
import pl.edu.agh.heimdall.engine.Maneuver;
import pl.edu.agh.heimdall.engine.SpyIntervention;
import pl.edu.agh.heimdall.statistics.Statistics;

import com.google.common.base.Optional;

public class FieldInitializationScout extends BaseScout {

	@Override
	public Optional<Maneuver> determineManeuverForGetField(JoinPoint joinPoint,
			Statistics statistics, final Field fieldBeingGot, Object fieldOwner) {
		try {
			if (fieldBeingGot.get(fieldOwner) != null) {
				return Optional.absent();
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new Error("This shouldn't happen");
		}
		if (String.class.isAssignableFrom(fieldBeingGot.getType())) {
			return Optional.<Maneuver> of(ManeuversFactory
					.getManeuverForInterventionType(
							InterventionType.STRING_INJECT, fieldBeingGot));
		} else if (List.class.isAssignableFrom(fieldBeingGot.getType())) {
			return Optional.<Maneuver> of(ManeuversFactory
					.getManeuverForInterventionType(
							InterventionType.LIST_INJECT, fieldBeingGot));
		} else if (Date.class.isAssignableFrom(fieldBeingGot.getType())) {
			return Optional.<Maneuver> of(ManeuversFactory
					.getManeuverForInterventionType(
							InterventionType.DATE_THROW, fieldBeingGot));
		} else {
			return Optional.<Maneuver> of(ManeuversFactory
					.getManeuverForInterventionType(InterventionType.NULL_LOG,
							fieldBeingGot));
		}

	}

	private abstract static class FieldAwareManeuver extends BaseManeuver {
		private Field field;

		public FieldAwareManeuver(Field field) {
			this.field = field;
		}

		public Field getField() {
			return field;
		}

		protected String crateCommunicateOfSpy(JoinPoint joinPoint,
				String customPart) {
			String targetClassName = joinPoint.getTarget().getClass().getName();
			String fieldName = getField().getName();
			String message = String.format("Got field %s.%s - %s",
					targetClassName, fieldName, customPart);
			return message;
		}

	}

	private static class ManeuversFactory {
		public static Maneuver getManeuverForInterventionType(
				InterventionType type, Field field) {
			switch (type) {
			case NULL_LOG:
				return new FieldAwareManeuver(field) {
					@Override
					public Optional<SpyIntervention> preOperationPhase(
							JoinPoint joinPoint) {
						String message = crateCommunicateOfSpy(joinPoint,
								"has null value!");
						System.out.println(message);
						return Optional.absent();
					}

				};
			case DATE_THROW:
				return new FieldAwareManeuver(field) {
					@Override
					public Optional<SpyIntervention> preOperationPhase(
							JoinPoint joinPoint) {
						throw new IllegalArgumentException(
								"Date cannot be null!");
					}
				};
			case LIST_INJECT:
				return new FieldAwareManeuver(field) {
					@Override
					public Optional<SpyIntervention> preOperationPhase(
							JoinPoint joinPoint) {
						String logCommunicate = crateCommunicateOfSpy(
								joinPoint, "injecting empty list instead null");
						System.out.println(logCommunicate);
						return Optional
								.<SpyIntervention> of(new SpyIntervention() {
									@Override
									public Object impersonateEnemy(Object enemy) {
										return new ArrayList<Object>();
									}
								});
					}
				};
			case STRING_INJECT:
				return new FieldAwareManeuver(field) {
					@Override
					public Optional<SpyIntervention> preOperationPhase(
							JoinPoint joinPoint) {
						String logCommunicate = crateCommunicateOfSpy(
								joinPoint, "injecting empty string instead null");
						System.out.println(logCommunicate);
						return Optional
								.<SpyIntervention> of(new SpyIntervention() {
									@Override
									public Object impersonateEnemy(Object enemy) {
										return "";
									}
								});
					}
				};
			default:
				return null;
			}
		}
	}

	private enum InterventionType {
		NULL_LOG, STRING_INJECT, LIST_INJECT, DATE_THROW;
	}

}
