package pl.edu.agh.heimdall.example;

import java.lang.reflect.Field;

import org.aspectj.lang.JoinPoint;

import pl.edu.agh.heimdall.engine.BaseManeuver;
import pl.edu.agh.heimdall.engine.Maneuver;
import pl.edu.agh.heimdall.engine.SpyIntervention;
import pl.edu.agh.heimdall.statistics.Statistics;

import com.google.common.base.Optional;

public class ToStringScout extends BasicMethodsScout {

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
								builder.append(String.format("%s: %s\n",
										f.getName(), getFieldValue(f, enemy)));
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

    @Override
    protected String observedMethod() {
        return "toString";
    }

}
