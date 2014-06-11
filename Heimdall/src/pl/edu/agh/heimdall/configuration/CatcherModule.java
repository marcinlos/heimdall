package pl.edu.agh.heimdall.configuration;

import java.util.LinkedList;

import pl.edu.agh.heimdall.behaviors.DiscovererResolverPair;
import pl.edu.agh.heimdall.behaviors.MoreThanOneViolationDiscoverer;
import pl.edu.agh.heimdall.behaviors.MoreThanOneViolationResolver;
import pl.edu.agh.heimdall.statistics.InMemoryStatistics;
import pl.edu.agh.heimdall.statistics.Statistics;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class CatcherModule extends AbstractModule{

	@Override
	protected void configure() {
		LinkedList<DiscovererResolverPair> violatonPairs = new LinkedList<DiscovererResolverPair>();
		//violatonPairs.add(DiscovererResolverPair.create(new NullViolationResolver(), new NullViolationDiscoverer()));
		violatonPairs.add(DiscovererResolverPair.create(new MoreThanOneViolationResolver(), new MoreThanOneViolationDiscoverer()));
		bind(new TypeLiteral<LinkedList<DiscovererResolverPair>>() {}).toInstance(violatonPairs);
		bind(Statistics.class).to(InMemoryStatistics.class).asEagerSingleton();
	}

}
