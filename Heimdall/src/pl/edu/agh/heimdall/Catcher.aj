package pl.edu.agh.heimdall;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

import javax.inject.Inject;

import pl.edu.agh.heimdall.behaviors.Maneuver;
import pl.edu.agh.heimdall.behaviors.Scout;
import pl.edu.agh.heimdall.configuration.CatcherModule;
import pl.edu.agh.heimdall.statistics.Statistics;

import com.google.common.base.Optional;

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

	private pointcut affected(): monitored() && !internals();

	private pointcut affectedMethods(): affected() && call(* *(..));

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

		boolean invokeOriginal = true;
		for (Maneuver maneuver : maneuvers) {
			boolean isGoingToInvokeOriginal = maneuver
					.preOperationPhase(thisJoinPoint);
			invokeOriginal &= isGoingToInvokeOriginal;
		}
		if (invokeOriginal) {
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
