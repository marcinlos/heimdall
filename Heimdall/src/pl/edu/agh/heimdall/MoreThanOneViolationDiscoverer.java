package pl.edu.agh.heimdall;

import org.aspectj.lang.JoinPoint;

public class MoreThanOneViolationDiscoverer implements ViolationDiscoverer{

	@Override
	public boolean validate(JoinPoint joinPoint, Statistics statistics) {
		int invocationTimes = statistics.timesOfMethodOnObjectInvokation(joinPoint.getTarget(), joinPoint.getSignature().getName());
		return invocationTimes!=0;
	}

}
