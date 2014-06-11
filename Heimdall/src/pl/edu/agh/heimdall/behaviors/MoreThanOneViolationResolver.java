package pl.edu.agh.heimdall.behaviors;

import org.aspectj.lang.JoinPoint;

public class MoreThanOneViolationResolver extends EmptyViolationResolver{

	@Override
	protected void methodCallInformICEPerson(JoinPoint joinPoint) {
		System.out.println("WARNING: Method is to be called second or more time (" + joinPoint.getSignature().getName() + ")");
	}


	@Override
	protected Decision makeMethodCallDecision(JoinPoint joinPoint) {
		return StandardViolationResolver.Decision.INFORM;
	}

	@Override
	protected Decision makeGetFieldDecision(JoinPoint joinPoint) {
		// TODO Auto-generated method stub
		return null;
	}

}
