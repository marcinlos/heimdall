package pl.edu.agh.heimdall.example;

import java.util.Objects;

import org.aspectj.lang.JoinPoint;

import pl.edu.agh.heimdall.engine.BaseManeuver;
import pl.edu.agh.heimdall.engine.Maneuver;
import pl.edu.agh.heimdall.engine.SpyIntervention;
import pl.edu.agh.heimdall.statistics.Statistics;

import com.google.common.base.Optional;

public class HashScout extends BasicMethodsScout {

    @Override
    protected RuntimeException createException(JoinPoint joinPoint) {
        throw new IllegalStateException("hashCode() is not overloaded");
    }

    @Override
    protected Optional<Maneuver> defineCustomManeuver(JoinPoint joinPoint,
            Statistics statistics) {
        return Optional.<Maneuver> of(new BaseManeuver() {
            @Override
            public Optional<SpyIntervention> preOperationPhase(
                    JoinPoint joinPoint) {
                writeMethodCommunicateOfSpy(joinPoint,
                        "going to mimic hashCode behavior");
                return Optional.<SpyIntervention> of(new SpyIntervention() {

                    @Override
                    public Object impersonateEnemy(Object enemy) {
                        try {
                            return Objects.hash(getFieldValues(enemy));
                        } catch (IllegalAccessException e) {
                            e.printStackTrace(System.err);
                            return null;
                        }
                    }
                });
            }
        });
    }
    

    @Override
    protected String getLogCommunicateMessage() {
        return "is going to be called while it wasn't overloaded";
    }

    @Override
    protected String getThrowCommunicateMessage() {
        return "cannot be invoked if it wasn't overloaded";
    }

    @Override
    protected String observedMethod() {
        return "hashCode";
    }


}
