package pl.edu.agh.heimdall;

import org.aspectj.lang.JoinPoint;

public abstract class StandardViolationResolver implements ViolationResolver {
	public static enum Decision {
		RESCUE, FINISH, INFORM;
	}

	protected abstract void rescue(JoinPoint joinPoint);

	protected abstract void finishHim(JoinPoint joinPoint);

	protected abstract void informICEPerson(JoinPoint joinPoint);

	protected abstract Decision makeDecision(JoinPoint joinPoint);

	@Override
	public void resolve(JoinPoint joinPoint) {
		switch (makeDecision(joinPoint)) {
		case RESCUE:
			rescue(joinPoint);
			break;
		case FINISH:
			finishHim(joinPoint);
			break;
		case INFORM:
			informICEPerson(joinPoint);
			break;
		}
	}

}
