package pl.edu.agh.heimdall.behaviors;

import org.aspectj.lang.JoinPoint;

public abstract class EmptyViolationResolver extends StandardViolationResolver{

	@Override
	protected void methodCallRescue(JoinPoint joinPoint) {
	}

	@Override
	protected void methodCallFinishHim(JoinPoint joinPoint) {
	}

	@Override
	protected void methodCallInformICEPerson(JoinPoint joinPoint) {
	}

	@Override
	protected void getFieldRescue(JoinPoint joinPoint) {
	}

	@Override
	protected void getFieldFinishHim(JoinPoint joinPoint) {
	}

	@Override
	protected void getFieldInformICEPerson(JoinPoint joinPoint) {
	}

}
