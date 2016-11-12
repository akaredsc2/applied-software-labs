package ua.fpm.appsoft.unconditionalOptimization;

import org.apache.commons.math3.linear.RealVector;
import ua.fpm.appsoft.util.Evaluator;

import javax.script.ScriptException;

import static java.lang.Math.min;

public class HookeJeevesOptimizer implements UnconditionalOptimizer {

    private final Evaluator evaluator;
    private final RealVector initPoint;
    private final double initIncrement;
    private final double precision;
    private final double acceleration;

    private RealVector optimalPoint;

    public HookeJeevesOptimizer(String function,
                                RealVector initPoint, double initIncrement,
                                double precision, double acceleration) {
        this.evaluator = new Evaluator(function);
        this.initPoint = initPoint;
        this.initIncrement = initIncrement;
        this.precision = precision;
        this.acceleration = acceleration;
    }

    @Override
    public double optimize() throws ScriptException {
        RealVector currPoint = initPoint.copy();
        double increment = initIncrement;
        RealVector nextPoint;

        do {
            nextPoint = doExploringSearch(currPoint, increment);
            if (evaluator.eval(nextPoint) < evaluator.eval(currPoint)) {
                RealVector modelPoint = doSearchByModel(currPoint, nextPoint);
                RealVector modelExploringPoint = doExploringSearch(modelPoint, increment);

                if (evaluator.eval(modelExploringPoint) < evaluator.eval(currPoint)) {
                    currPoint = nextPoint;
//                    nextPoint = modelExploringPoint;
                }
            } else {
                if (increment > precision) {
                    increment /= 2;
                }
            }

        } while (increment > precision);

        this.optimalPoint = currPoint;
        return evaluator.eval(currPoint);
    }

    public RealVector getOptimalPoint() {
        return optimalPoint.copy();
    }

    private RealVector doExploringSearch(RealVector point, double increment) throws ScriptException {
        RealVector result = point.copy();
        for (int i = 0; i < result.getDimension(); i++) {
            double currFunction = evaluator.eval(result);

            RealVector plusPoint = result.copy();
            plusPoint.addToEntry(i, increment);
            double plusFunction = evaluator.eval(plusPoint);

            RealVector minusPoint = result.copy();
            minusPoint.addToEntry(i, -increment);
            double minusFunction = evaluator.eval(minusPoint);

            if (plusFunction < currFunction) {
                result = plusPoint;
            }
            if (minusFunction < min(plusFunction, currFunction)) {
                result = minusPoint;
            }
        }
        return result;
    }

    private RealVector doSearchByModel(RealVector currPoint, RealVector nextPoint) {
        return nextPoint.add(
                nextPoint.subtract(currPoint).mapMultiply(acceleration));
    }

}
