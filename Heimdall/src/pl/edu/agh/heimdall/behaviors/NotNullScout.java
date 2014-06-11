package pl.edu.agh.heimdall.behaviors;

import org.aspectj.lang.JoinPoint;

import pl.edu.agh.heimdall.statistics.Statistics;

import com.google.common.base.Optional;

public class NotNullScout extends CowardScout{

	@Override
	public Optional<Maneuver> determineManeuverForMethodCall(
			JoinPoint joinPoint, Statistics statistics) {
		for(Object o :joinPoint.getArgs()){
			if(o==null){
				return Optional.<Maneuver>of(new Maneuver() {
					
					@Override
					public boolean preOperationPhase(JoinPoint joinPoint) {
						System.out.println(String.format("Null going to be as argument of %s!!!", joinPoint.getSignature()));
						return true;
					}
					
					@Override
					public void postOperationPhase(JoinPoint joinPoint) {
						System.out.println("I said!!!");
					}
				});
			}
		}
		return Optional.<Maneuver>absent();
	}


}
