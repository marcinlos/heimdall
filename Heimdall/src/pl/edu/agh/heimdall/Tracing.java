package pl.edu.agh.heimdall;

public class Tracing {

    private static final ThreadLocal<Tracer> tracers = new ThreadLocal<Tracer>() {
        @Override
        protected Tracer initialValue() {
            return new Tracer();
        }
    };

    public static Tracer getTracer() {
        return tracers.get();
    }

}
