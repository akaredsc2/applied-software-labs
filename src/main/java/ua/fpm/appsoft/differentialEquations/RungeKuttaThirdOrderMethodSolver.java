package ua.fpm.appsoft.differentialEquations;

import org.apache.commons.math3.geometry.euclidean.oned.Interval;
import ua.fpm.appsoft.util.Evaluator;

import javax.script.ScriptException;
import java.util.Arrays;

public class RungeKuttaThirdOrderMethodSolver implements NumericDifferentialEquationSolver {

    private Evaluator evaluator;
    private final Interval interval;
    private final int intervalCount;
    private final double initArgument;
    private final double initFunction;

    public RungeKuttaThirdOrderMethodSolver(String function, Interval interval, int intervalCount,
                                            double initArgument, double initFunction) {
        this.evaluator = new Evaluator(function);
        this.interval = interval;
        this.intervalCount = intervalCount;
        this.initArgument = initArgument;
        this.initFunction = initFunction;
    }

    @Override
    public Double[] solve() throws ScriptException {
        final double step = interval.getSize() / intervalCount;
        Pair currentPair = new Pair(initArgument, initFunction);
        Pair[] pairs = new Pair[intervalCount + 1];

        for (int i = 0; i <= intervalCount; i++) {
            try {
                pairs[i] = currentPair.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

            double kZero = step * evaluator.eval(currentPair.arg, currentPair.func);
            double kOne = step * evaluator.eval(currentPair.arg + step / 2, currentPair.func + kZero / 2);
            double kTwo = step * evaluator.eval(currentPair.arg + step, currentPair.func + 2 * kOne - kZero);

            currentPair.arg += step;
            currentPair.func += (kZero + 4 * kOne + kTwo) / 6;
        }

        Double[] result = Arrays.stream(pairs).map(Pair::getFunc).toArray(Double[]::new);
        if (Arrays.stream(result).filter(x -> Double.isNaN(x) || Double.isInfinite(x)).count() > 0) {
            throw new ArithmeticException("Not a real number result!");
        }

        return result;
    }

    private static class Pair implements Cloneable {

        double arg;
        double func;

        Pair(double arg, double func) {
            this.arg = arg;
            this.func = func;
        }

        Double getFunc() {
            return func;
        }

        @Override
        protected Pair clone() throws CloneNotSupportedException {
            return new Pair(arg, func);
        }

    }

}
