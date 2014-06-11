package pl.edu.agh.heimdall;

import java.util.LinkedList;

import javax.inject.Inject;

import org.aspectj.lang.JoinPoint;

import pl.edu.agh.heimdall.behaviors.DiscovererResolverPair;
import pl.edu.agh.heimdall.configuration.CatcherModule;
import pl.edu.agh.heimdall.statistics.Statistics;

import com.google.inject.Guice;


public abstract privileged aspect Catcher {
	
	@Inject
	private LinkedList<DiscovererResolverPair> violationPairs;
	
	@Inject
	private Statistics statistics;
	
	public Catcher() {
		Guice.createInjector(new CatcherModule()).injectMembers(this);
	}

	private pointcut internals(): within(pl.edu.agh.heimdall..*);

	abstract pointcut monitored();

	private pointcut affected(): monitored() && !internals();

	private pointcut affectedMethods(): affected() && call(* *(..));

	before(): affectedMethods(){
		for(DiscovererResolverPair violationPair: violationPairs){
			if(violationPair.getDiscoverer().validateMethodCall(thisJoinPoint, statistics)){
				violationPair.getResolver().resolveMethodCall(thisJoinPoint);
			}
		}
		statistics.addInvocationOf(thisJoinPoint.getTarget(), thisJoinPoint.getSignature().getName());
	}
}
