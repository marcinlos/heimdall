package pl.edu.agh.heimdall.behaviors;

import org.aspectj.lang.JoinPoint;

import pl.edu.agh.heimdall.statistics.Statistics;

public class NullViolationDiscoverer implements ViolationDiscoverer{

	@Override
	public boolean validateMethodCall(JoinPoint joinPoint, Statistics statistics) {
		for(Object o :joinPoint.getArgs()){
			if(o==null){
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean validateGetField(JoinPoint joinPoint, Statistics statistics) {
		// TODO Auto-generated method stub
		return false;
	}

}
