package pl.edu.agh.heimdall.behaviors;

import org.aspectj.lang.JoinPoint;

import pl.edu.agh.heimdall.statistics.Statistics;

public class MoreThanOneViolationDiscoverer implements ViolationDiscoverer{

	@Override
	public boolean validateMethodCall(JoinPoint joinPoint, Statistics statistics) {
		int invocationTimes = statistics.timesOfMethodOnObjectInvokation(joinPoint.getTarget(), joinPoint.getSignature().getName());
		return invocationTimes!=0;
	}

	@Override
	public boolean validateGetField(JoinPoint joinPoint, Statistics statistics) {
		// TODO Auto-generated method stub
		return false;
	}

}
