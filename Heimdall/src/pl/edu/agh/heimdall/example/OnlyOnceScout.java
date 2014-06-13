package pl.edu.agh.heimdall.example;

import java.util.HashMap;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;

import pl.edu.agh.heimdall.engine.BaseManeuver;
import pl.edu.agh.heimdall.engine.BaseScout;
import pl.edu.agh.heimdall.engine.Maneuver;
import pl.edu.agh.heimdall.engine.SpyIntervention;
import pl.edu.agh.heimdall.statistics.Statistics;
import rangarok.Main;

import com.google.common.base.Optional;

public class OnlyOnceScout extends BaseScout {

	@SuppressWarnings("serial")
	private static final HashMap<InterventionType, Optional<Maneuver>> maneuversHashMap = new HashMap<OnlyOnceScout.InterventionType, Optional<Maneuver>>() {
		{
			put(InterventionType.LOG,
					Optional.<Maneuver> of(new BaseManeuver() {
						public Optional<SpyIntervention> preOperationPhase(
								JoinPoint joinPoint) {
							writeMethodCommunicateOfSpy(joinPoint,
									"going to be called second time!");
							return Optional.absent();
						};
					}));
			put(InterventionType.THROW,
					Optional.<Maneuver> of(new BaseManeuver() {
						public Optional<SpyIntervention> preOperationPhase(
								JoinPoint joinPoint) {
							throw new IllegalStateException(
									"Cannot invoke that method on the same object second time!");
						};
					}));
			put(InterventionType.ADDITIONAL_WORK,
					Optional.<Maneuver> of(new BaseManeuver() {
						public Optional<SpyIntervention> preOperationPhase(
								JoinPoint joinPoint) {
							writeMethodCommunicateOfSpy(joinPoint,
									"pre operation phase!");
							Main main = (Main) joinPoint.getTarget();
							main.clean();
							return Optional.absent();
						};

						public void postOperationPhase(JoinPoint joinPoint) {
							writeMethodCommunicateOfSpy(joinPoint,
									"post operation phase!");
							Main main = (Main) joinPoint.getTarget();
							main.checkState();
						};
					}));
			put(InterventionType.AVOID,
					Optional.<Maneuver> of(new BaseManeuver() {
						public Optional<SpyIntervention> preOperationPhase(
								JoinPoint joinPoint) {
							writeMethodCommunicateOfSpy(joinPoint,
									"avoiding operation!");
							return Optional
									.<SpyIntervention> of(new SpyIntervention() {
										@Override
										public Object impersonateEnemy(
												Object enemy) {
											return null;
										}
									});
						};
					}));
		}
	};

	@Override
	public Optional<Maneuver> determineManeuverForMethodCall(
			JoinPoint joinPoint, Statistics statistics) {
		Object target = joinPoint.getTarget();
		if (target!=null && !Main.class.isAssignableFrom(target.getClass())) {
			return Optional.absent();
		}
		Signature signature = joinPoint.getSignature();
		int invocations = statistics.timesOfMethodOnObjectInvokation(
				target, signature.toString());
		Optional<Maneuver> possibleManeuver = Optional.absent();
		if (signature.getName().startsWith("onceLog")) {
			possibleManeuver = maneuversHashMap.get(InterventionType.LOG);
		} else if (signature.getName().startsWith("onceException")) {
			possibleManeuver = maneuversHashMap.get(InterventionType.THROW);
		} else if (signature.getName().startsWith("onceAvoid")) {
			possibleManeuver = maneuversHashMap.get(InterventionType.AVOID);
		} else if (signature.getName().startsWith("onceAdditional")) {
			possibleManeuver = maneuversHashMap
					.get(InterventionType.ADDITIONAL_WORK);
		}
		return invocations != 0 ? possibleManeuver : Optional
				.<Maneuver> absent();
	}

	private enum InterventionType {
		THROW, LOG, ADDITIONAL_WORK, AVOID;
	}
}
