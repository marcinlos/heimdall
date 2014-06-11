package pl.edu.agh.heimdall;

import org.aspectj.lang.JoinPoint;

public class NullViolationDiscoverer implements ViolationDiscoverer{

	@Override
	public boolean validate(JoinPoint joinPoint, Statistics statistics) {
		for(Object o :joinPoint.getArgs()){
			if(o==null){
				return false;
			}
		}
		return true;
	}

}
