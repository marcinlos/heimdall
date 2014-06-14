package pl.edu.agh.heimdall.example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.aspectj.lang.JoinPoint;

import pl.edu.agh.heimdall.engine.BaseManeuver;
import pl.edu.agh.heimdall.engine.Maneuver;
import pl.edu.agh.heimdall.engine.SpyIntervention;
import pl.edu.agh.heimdall.statistics.Statistics;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.collect.Collections2;

public class NullParameterScout extends LogThrowCustomMethodBaseScout {

	private String getMethodName(JoinPoint joinPoint) {
		return joinPoint.getSignature().getName();
	}

	@Override
	public boolean isLogInterventionType(JoinPoint joinPoint,
			Statistics statistics) {
		return getMethodName(joinPoint).startsWith("pLog");
	}

	@Override
	public boolean isThrowInterventionType(JoinPoint joinPoint,
			Statistics statistics) {
		return getMethodName(joinPoint).startsWith("pException");
	}

	@Override
	public boolean isCustomInterventionType(JoinPoint joinPoint,
			Statistics statistics) {
		return getMethodName(joinPoint).startsWith("pInject");
	}

	@Override
	protected RuntimeException createException(JoinPoint joinPoint) {
		throw new IllegalStateException(
				"Cannot be invoked cause null argument is passed");
	}

	@Override
	protected Optional<Maneuver> defineCustomManeuver(JoinPoint joinPoint,
			Statistics statistics) {
		return Optional.<Maneuver> of(new BaseManeuver() {
			public Optional<SpyIntervention> preOperationPhase(
					final JoinPoint joinPoint) {
				writeMethodCommunicateOfSpy(joinPoint,
						"changing null strings to \"[null]\" string!");

				Object[] args = joinPoint.getArgs();
				final Collection<Object> completedArguments = Collections2
						.transform(Arrays.asList(args),
								new Function<Object, Object>() {

									@Override
									public Object apply(Object arg0) {
										if (arg0 == null) {
											arg0 = "[null]";
											try {
												String.class.cast(arg0);
											} catch (ClassCastException ex) {
												// arg0 is not
												// string subclass
												arg0 = null;
											}
										}
										return arg0;
									}
								});
				if (completedArguments.contains(null)) {
					throw new IllegalStateException(
							"Cannot be injected properly, cause not all null's are strings");
				}
				return Optional.<SpyIntervention> of(new SpyIntervention() {

					@Override
					public Object impersonateEnemy(Object enemy) {
						// workaround!
						Object toReturn = null;
						try {
							Method method = enemy.getClass().getMethod(
									joinPoint.getSignature().getName(),
									String.class, String.class);
							method.setAccessible(true);
							toReturn = method.invoke(enemy,
									completedArguments.toArray());
							method.setAccessible(false);
						} catch (NoSuchMethodException | SecurityException
								| IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException e1) {
							e1.printStackTrace();
						}
						return toReturn;

					}
				});
			};
		});

	}

	@Override
	protected String getLogCommunicateMessage() {
		return "is going to be called with null argument";
	}

	@Override
	protected String getThrowCommunicateMessage() {
		return "Cannot be invoked cause null argument is passed";
	}

	@Override
	protected boolean isInScopeOfInterest(JoinPoint joinPoint,
			Statistics statistics) {
		List<Object> args = Arrays.asList(joinPoint.getArgs());
		return args.contains(null);
	}
}
