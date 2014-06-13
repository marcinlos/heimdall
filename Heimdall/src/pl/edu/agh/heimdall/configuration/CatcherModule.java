package pl.edu.agh.heimdall.configuration;

import java.util.LinkedList;

import pl.edu.agh.heimdall.engine.Scout;
import pl.edu.agh.heimdall.example.FieldInitializationScout;
import pl.edu.agh.heimdall.example.NullParameterScout;
import pl.edu.agh.heimdall.example.OnlyOnceScout;
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
		scouts.add(new NullParameterScout());
		scouts.add(new OnlyOnceScout());
		scouts.add(new FieldInitializationScout());
		bind(new TypeLiteral<LinkedList<Scout>>() {}).toInstance(scouts);
		bind(Statistics.class).to(InMemoryStatistics.class).asEagerSingleton();
	}
	
	public static final Injector rootInjector = Guice.createInjector(new CatcherModule());
	

}
