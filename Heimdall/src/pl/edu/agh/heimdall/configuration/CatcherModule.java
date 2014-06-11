package pl.edu.agh.heimdall.configuration;

import java.util.LinkedList;

import pl.edu.agh.heimdall.behaviors.NotNullScout;
import pl.edu.agh.heimdall.behaviors.OnlyOnceScout;
import pl.edu.agh.heimdall.behaviors.Scout;
import pl.edu.agh.heimdall.statistics.InMemoryStatistics;
import pl.edu.agh.heimdall.statistics.Statistics;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;

public class CatcherModule extends AbstractModule{
	
	private CatcherModule() {
	}

	@Override
	protected void configure() {
		LinkedList<Scout> scouts = new LinkedList<Scout>();
		scouts.add(new NotNullScout());
		scouts.add(new OnlyOnceScout());
		bind(new TypeLiteral<LinkedList<Scout>>() {}).toInstance(scouts);
		bind(Statistics.class).to(InMemoryStatistics.class).asEagerSingleton();
	}
	
	public static final Injector rootInjector = Guice.createInjector(new CatcherModule());
	

}
