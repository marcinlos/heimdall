package pl.edu.agh.heimdall;

import org.aspectj.lang.JoinPoint;

public class MoreThanOneViolationResolver extends StandardViolationResolver{

	@Override
	protected void rescue(JoinPoint joinPoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void finishHim(JoinPoint joinPoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void informICEPerson(JoinPoint joinPoint) {
		System.out.println("WARNING: Method is to be called second or more time (" + joinPoint.getSignature().getName() + ")");
	}

	@Override
	protected Decision makeDecision(JoinPoint joinPoint) {
		return StandardViolationResolver.Decision.INFORM;
	}

}
