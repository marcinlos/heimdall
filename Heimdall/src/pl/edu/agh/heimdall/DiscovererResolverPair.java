package pl.edu.agh.heimdall;

public class DiscovererResolverPair {
	private ViolationResolver resolver;
	private ViolationDiscoverer discoverer;
	
	public static DiscovererResolverPair create(
			ViolationResolver resolver, ViolationDiscoverer discoverer) {
		return new DiscovererResolverPair(resolver, discoverer);
	}
	
	private DiscovererResolverPair(ViolationResolver resolver,
			ViolationDiscoverer discoverer) {
		this.resolver = resolver;
		this.discoverer = discoverer;
	}
	
	public ViolationResolver getResolver() {
		return resolver;
	}
	public ViolationDiscoverer getDiscoverer() {
		return discoverer;
	}
	
}
