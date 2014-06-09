package pl.edu.agh.heimdall;

import java.util.LinkedList;

import javax.inject.Inject;

import com.google.inject.Guice;


public abstract privileged aspect Catcher {
	
	@Inject
	private LinkedList<DiscovererResolverPair> violationPairs;
	
	public Catcher() {
		System.out.println("Constructing");
		Guice.createInjector(new CatcherModule()).injectMembers(this);
		System.out.println("After Constructing");
	}

	private pointcut internals(): within(pl.edu.agh.heimdall..*);

	abstract pointcut monitored();

	private pointcut affected(): monitored() && !internals();

	private pointcut affectedMethods(): affected() && call(* *(..));

	before(): affectedMethods(){
		for(DiscovererResolverPair violationPair: violationPairs){
			if(violationPair.getDiscoverer().validate(thisJoinPoint)){
				violationPair.getResolver().resolve(thisJoinPoint);
			}
		}
	}
}
