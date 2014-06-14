package pl.edu.agh.heimdall.example;

import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;

import pl.edu.agh.heimdall.engine.BaseManeuver;
import pl.edu.agh.heimdall.engine.Maneuver;
import pl.edu.agh.heimdall.engine.SpyIntervention;
import pl.edu.agh.heimdall.statistics.Statistics;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

public class ToStringScout extends LogThrowCustomMethodBaseScout {

	// just to test all interventions
	private static final int MAX_INTERVENTIONS = 3;
	private int interventionsCounter = -1;

	@Override
	public boolean isLogInterventionType(JoinPoint joinPoint,
			Statistics statistics) {
		return interventionsCounter % MAX_INTERVENTIONS == 0;
	}

	@Override
	public boolean isThrowInterventionType(JoinPoint joinPoint,
			Statistics statistics) {
		return interventionsCounter % MAX_INTERVENTIONS == 1;
	}

	@Override
	public boolean isCustomInterventionType(JoinPoint joinPoint,
			Statistics statistics) {
		return interventionsCounter % MAX_INTERVENTIONS == 2;
	}

	@Override
	protected boolean isInScopeOfInterest(JoinPoint joinPoint,
			Statistics statistics) {
		Object target = joinPoint.getTarget();
		String toStringName = "toString";
		boolean isToStringInvocation = getMethodName(joinPoint).equals(
				toStringName);
		if (target == null || !isToStringInvocation) {
			return false;
		}
		try {
			target.getClass().getDeclaredMethod(toStringName);
		} catch (NoSuchMethodException e) {
			++interventionsCounter;
			return true;
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	protected RuntimeException createException(JoinPoint joinPoint) {
		throw new IllegalStateException("To string wasn't overloaded!");
	}

	@Override
	protected Optional<Maneuver> defineCustomManeuver(JoinPoint joinPoint,
			Statistics statistics) {
		return Optional.<Maneuver> of(new BaseManeuver() {
			@Override
			public Optional<SpyIntervention> preOperationPhase(
					JoinPoint joinPoint) {
				writeMethodCommunicateOfSpy(joinPoint,
						"going to mimic toString behavior");
				return Optional.<SpyIntervention> of(new SpyIntervention() {

					@Override
					public Object impersonateEnemy(Object enemy) {
						StringBuilder builder = new StringBuilder();
						builder.append(String.format("Object (original)toString: %s\n",
								enemy.toString()));
						builder.append("{\n");
						try {
							for (Field f : getAllNonStaticFields(enemy.getClass())) {
								boolean oldAccesibility = f.isAccessible();
								f.setAccessible(true);
								builder.append(String.format("%s: %s\n",
										f.getName(), f.get(enemy)));
								f.setAccessible(oldAccesibility);
							}
						} catch (IllegalArgumentException
								| IllegalAccessException e) {
							e.printStackTrace();
						}
						builder.append("}");
						return builder.toString();
					}
				});
			}
		});
	}

	@Override
	protected String getLogCommunicateMessage() {
		return "is going to be called while it wasn't overloaded";
	}

	@Override
	protected String getThrowCommunicateMessage() {
		return "cannot be invoked if it wasn't overloaded";
	}

	private ArrayList<Field> getAllNonStaticFields(final Class<?> clazz) {
		Class<?> clazzPointer = clazz;
		ArrayList<Field> fields = new ArrayList<>();
		while (clazzPointer != null) {
			fields.addAll(Arrays.asList(clazzPointer.getDeclaredFields()));
			clazzPointer = clazzPointer.getSuperclass();
		}
		return new ArrayList<Field>(Collections2.filter(fields,
				new Predicate<Field>() {
					@Override
					public boolean apply(Field arg0) {
						return !Modifier.isStatic(arg0.getModifiers());
					}
				}));
	}

}
