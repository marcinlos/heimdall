package pl.edu.agh.heimdall;

import java.util.LinkedList;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class CatcherModule extends AbstractModule{

	@Override
	protected void configure() {
		LinkedList<DiscovererResolverPair> violatonPairs = new LinkedList<DiscovererResolverPair>();
		violatonPairs.add(DiscovererResolverPair.create(new NullViolationResolver(), new NullViolationDiscoverer()));
		bind(new TypeLiteral<LinkedList<DiscovererResolverPair>>() {}).toInstance(violatonPairs);
	}

}
