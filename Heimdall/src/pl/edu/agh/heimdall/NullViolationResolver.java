package pl.edu.agh.heimdall;

import org.aspectj.lang.JoinPoint;

public class NullViolationResolver extends StandardViolationResolver{

	@Override
	protected void rescue(JoinPoint joinPoint) {
		// TODO Auto-generated method stub	
	}

	@Override
	protected void finishHim(JoinPoint joinPoint) {
		throw new IllegalArgumentException("BLEEEEE");
	}

	@Override
	protected void informICEPerson(JoinPoint joinPoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Decision makeDecision(JoinPoint joinPoint) {
		return StandardViolationResolver.Decision.FINISH;
	}

}
