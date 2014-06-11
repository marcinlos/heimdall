package pl.edu.agh.heimdall;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

import javax.inject.Inject;

import pl.edu.agh.heimdall.configuration.CatcherModule;
import pl.edu.agh.heimdall.engine.Maneuver;
import pl.edu.agh.heimdall.engine.Scout;
import pl.edu.agh.heimdall.engine.SpyIntervention;
import pl.edu.agh.heimdall.statistics.Statistics;

import com.google.common.base.Optional;
import com.google.common.reflect.Reflection;

public abstract privileged aspect Catcher {

	@Inject
	private LinkedList<Scout> scouts;

	@Inject
	private Statistics statistics;

	public Catcher() {
		CatcherModule.rootInjector.injectMembers(this);
	}

	private pointcut internals(): within(pl.edu.agh.heimdall..*);

	abstract pointcut monitored();

	abstract pointcut monitoredMethodCalls();
	
	abstract pointcut monitoredGetFields();

	private pointcut affected(): monitored() && !internals();

	private pointcut affectedMethods(): affected() && monitoredMethodCalls();

	private pointcut affectedGet(Object o): affected() && monitoredGetFields() && this(o);
	
	before(Object o): affectedGet(o){
		System.out.println("This is: " + o);
		
		String fieldName = thisJoinPoint.getSignature().getName();
		
		System.out.println(fieldName);
//		try {
//			Field affectedField = o.getClass().getField(fieldName);
//			affectedField.setAccessible(true);
//			affectedField.set(o, "luuzik");
//			affectedField.setAccessible(false);
//		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println("-----");
	}

	Object around(): affectedMethods(){
		Object toReturn = null;
		Deque<Maneuver> maneuvers = new ArrayDeque<Maneuver>();
		for (Scout scout : scouts) {
			Optional<Maneuver> possibleManuever = scout
					.determineManeuverForMethodCall(thisJoinPoint, statistics);
			if (possibleManuever.isPresent()) {
				maneuvers.add(possibleManuever.get());
			}
		}

		Optional<SpyIntervention> neededSpyIntervention = Optional.absent();
		// only the first spy intervention works - potentially dangerous
		for (Maneuver maneuver : maneuvers) {
			if (!neededSpyIntervention.isPresent()) {
				neededSpyIntervention = maneuver
						.preOperationPhase(thisJoinPoint);
			}
		}
		if (neededSpyIntervention.isPresent()) {
			toReturn = neededSpyIntervention.get().impersonateEnemy(
					thisJoinPoint.getTarget());
		} else {
			toReturn = proceed();
		}

		while (!maneuvers.isEmpty()) {
			Maneuver lastManeuver = maneuvers.pollLast();
			lastManeuver.postOperationPhase(thisJoinPoint);
		}
		statistics.addInvocationOf(thisJoinPoint.getTarget(), thisJoinPoint
				.getSignature().getName());
		return toReturn;
	}
}
