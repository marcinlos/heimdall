package pl.edu.agh.heimdall.behaviors;

import org.aspectj.lang.JoinPoint;

public abstract class StandardViolationResolver implements ViolationResolver {
	public static enum Decision {
		RESCUE, FINISH, INFORM;
	}

	protected abstract void methodCallRescue(JoinPoint joinPoint);

	protected abstract void methodCallFinishHim(JoinPoint joinPoint);

	protected abstract void methodCallInformICEPerson(JoinPoint joinPoint);
	
	protected abstract void getFieldRescue(JoinPoint joinPoint);

	protected abstract void getFieldFinishHim(JoinPoint joinPoint);

	protected abstract void getFieldInformICEPerson(JoinPoint joinPoint);

	protected abstract Decision makeMethodCallDecision(JoinPoint joinPoint);
	protected abstract Decision makeGetFieldDecision(JoinPoint joinPoint);

	@Override
	public void resolveMethodCall(JoinPoint joinPoint) {
		switch (makeMethodCallDecision(joinPoint)) {
		case RESCUE:
			methodCallRescue(joinPoint);
			break;
		case FINISH:
			methodCallFinishHim(joinPoint);
			break;
		case INFORM:
			methodCallInformICEPerson(joinPoint);
			break;
		}
	}
	
	@Override
	public void resolveGetField(JoinPoint joinPoint) {
		switch (makeGetFieldDecision(joinPoint)) {
		case RESCUE:
			getFieldRescue(joinPoint);
			break;
		case FINISH:
			getFieldFinishHim(joinPoint);
			break;
		case INFORM:
			getFieldInformICEPerson(joinPoint);
			break;
		}
	}

}
