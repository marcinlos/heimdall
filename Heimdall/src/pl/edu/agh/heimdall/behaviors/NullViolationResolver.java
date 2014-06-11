package pl.edu.agh.heimdall.behaviors;

import org.aspectj.lang.JoinPoint;

public class NullViolationResolver extends EmptyViolationResolver{

	@Override
	protected void methodCallFinishHim(JoinPoint joinPoint) {
		throw new IllegalArgumentException("BLEEEEE");
	}

	@Override
	protected Decision makeMethodCallDecision(JoinPoint joinPoint) {
		return StandardViolationResolver.Decision.FINISH;
	}

	@Override
	protected Decision makeGetFieldDecision(JoinPoint joinPoint) {
		// TODO Auto-generated method stub
		return null;
	}

}
