package pl.edu.agh.heimdall.annotations.trace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MouseMotionListenerTraced {
Events[] events();
	
	public enum Events{
		MOUSE_DRAGGED, MOUSE_MOVED;
	}
}
