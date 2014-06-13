package pl.edu.agh.heimdall;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

import javax.inject.Inject;

import org.aspectj.lang.JoinPoint;

import com.google.common.base.Optional;

import pl.edu.agh.heimdall.configuration.CatcherModule;
import pl.edu.agh.heimdall.engine.Maneuver;
import pl.edu.agh.heimdall.engine.Scout;
import pl.edu.agh.heimdall.engine.SpyIntervention;
import pl.edu.agh.heimdall.statistics.Statistics;

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

	private pointcut affectedGet(): affected() && monitoredGetFields();

	Object around(): affectedGet(){

		Optional<Object> optionalToReturn = null;
		Deque<Maneuver> maneuvers = determineManeuvers(thisJoinPoint);

		Optional<SpyIntervention> neededSpyIntervention = determineSpyIntervention(
				maneuvers, thisJoinPoint);
		
		//append spy can but nut must change field value
		optionalToReturn = appendSpyIntervention(neededSpyIntervention,
				thisJoinPoint);
		
		if(!optionalToReturn.isPresent()){
			optionalToReturn = Optional.fromNullable(proceed());
		}

		firePostOperationPhase(maneuvers, thisJoinPoint);

		return optionalToReturn.orNull();
	}
	
	Object around(): affectedMethods(){

		Optional<Object> optionalToReturn = null;
		Deque<Maneuver> maneuvers = determineManeuvers(thisJoinPoint);

		Optional<SpyIntervention> neededSpyIntervention = determineSpyIntervention(
				maneuvers, thisJoinPoint);

		optionalToReturn = appendSpyIntervention(neededSpyIntervention,
				thisJoinPoint);

		if(!optionalToReturn.isPresent()){
			optionalToReturn = Optional.fromNullable(proceed());
		}

		firePostOperationPhase(maneuvers, thisJoinPoint);

		statistics.addInvocationOf(thisJoinPoint.getTarget(), thisJoinPoint
				.getSignature().getName());

		return optionalToReturn.orNull();
	}
	

	private Deque<Maneuver> determineManeuvers(JoinPoint joinPoint) {
		Deque<Maneuver> maneuvers = new ArrayDeque<Maneuver>();
		for (Scout scout : scouts) {
			Optional<Maneuver> possibleManuever;
			if (joinPoint.getKind().equals("field-get")) {
				Field field = findModifiedField(joinPoint);
				field.setAccessible(true);
				possibleManuever = scout.determineManeuverForGetField(
						joinPoint, statistics, field, joinPoint.getTarget());
				field.setAccessible(false);
			} else {
				possibleManuever = scout.determineManeuverForMethodCall(
						joinPoint, statistics);
			}
			if (possibleManuever.isPresent()) {
				maneuvers.add(possibleManuever.get());
			}
		}
		return maneuvers;
	}

	private Field findModifiedField(JoinPoint joinPoint) throws Error {
		String fieldName = joinPoint.getSignature().getName();
		Optional<Field> optionalField = findDeclaredField(joinPoint.getTarget(), fieldName);
		if (!optionalField.isPresent()) {
			throw new Error("This should not happen!");
		}
		Field field = optionalField.get();
		return field;
	}

	

	private Optional<Object> appendSpyIntervention(
			Optional<SpyIntervention> neededSpyIntervention, JoinPoint joinPoint) {
		Object toReturn = null;
		if (neededSpyIntervention.isPresent()) {
			toReturn = neededSpyIntervention.get().impersonateEnemy(
					joinPoint.getTarget());
		}
		return Optional.fromNullable(toReturn);
	}

	private void firePostOperationPhase(Deque<Maneuver> maneuvers,
			JoinPoint jointPoint) {
		while (!maneuvers.isEmpty()) {
			Maneuver lastManeuver = maneuvers.pollLast();
			lastManeuver.postOperationPhase(jointPoint);
		}
	}

	private Optional<SpyIntervention> determineSpyIntervention(
			Deque<Maneuver> maneuvers, JoinPoint joinPoint) {
		Optional<SpyIntervention> neededSpyIntervention = Optional.absent();
		// only the first spy intervention works - potentially dangerous
		for (Maneuver maneuver : maneuvers) {
			if (!neededSpyIntervention.isPresent()) {
				neededSpyIntervention = maneuver.preOperationPhase(joinPoint);
			}
		}
		return neededSpyIntervention;
	}

	private Optional<Field> findDeclaredField(Object o, String fieldName) {
		Class<?> clazz = o.getClass();
		Field toReturnField = null;
		while (clazz != null && toReturnField == null) {
			try {
				toReturnField = clazz.getDeclaredField(fieldName);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				//ntd
			}
			clazz = clazz.getSuperclass();
		}
		return Optional.fromNullable(toReturnField);
	}

}
