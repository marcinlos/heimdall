package pl.edu.agh.heimdall;

public abstract privileged aspect NullCatcher {
	
	private pointcut internals(): within(pl.edu.agh.heimdall..*);

	abstract pointcut monitored();

}
