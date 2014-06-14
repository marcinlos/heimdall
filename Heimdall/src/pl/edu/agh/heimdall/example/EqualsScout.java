package pl.edu.agh.heimdall.example;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;

import pl.edu.agh.heimdall.engine.BaseManeuver;
import pl.edu.agh.heimdall.engine.Maneuver;
import pl.edu.agh.heimdall.engine.SpyIntervention;
import pl.edu.agh.heimdall.statistics.Statistics;

import com.google.common.base.Optional;

public class EqualsScout extends BasicMethodsScout {

    @Override
    protected RuntimeException createException(JoinPoint joinPoint) {
        throw new IllegalStateException("equals() is not overloaded");
    }

    @Override
    protected Optional<Maneuver> defineCustomManeuver(JoinPoint joinPoint,
            Statistics statistics) {
        return Optional.<Maneuver> of(new BaseManeuver() {
            @Override
            public Optional<SpyIntervention> preOperationPhase(
                    final JoinPoint joinPoint) {
                writeMethodCommunicateOfSpy(joinPoint,
                        "going to mimic equals behavior");
                return Optional.<SpyIntervention> of(new SpyIntervention() {

                    @Override
                    public Object impersonateEnemy(Object enemy) {
                        try {
                            Object other = joinPoint.getArgs()[0];
                            if (other == null || !enemy.getClass()
                                    .isAssignableFrom(other.getClass())) {
                                return false;
                            }
                            Object[] f1 = getFieldValues(enemy);
                            Object[] f2 = getFieldValues(other);
                            return Arrays.equals(f1, f2);
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
        return "equals";
    }


}
