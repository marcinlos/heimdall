package pl.edu.agh.heimdall.annotations.trace;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface KeyListenerTraced {
Events[] events();
	
	public enum Events{
		KEY_PRESSED, KEY_RELEASED, KEY_TYPED;
	}
}
