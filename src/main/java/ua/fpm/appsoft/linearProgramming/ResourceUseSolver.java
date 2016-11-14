package ua.fpm.appsoft.linearProgramming;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.SimplexSolver;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.List;

public class ResourceUseSolver {

    public static PointValuePair optimize(LinearObjectiveFunction function, List<LinearConstraint> constraints) {
        LinearConstraintSet constraintSet = new LinearConstraintSet(constraints);
        SimplexSolver solver = new SimplexSolver();
        return solver.optimize(function, constraintSet, GoalType.MAXIMIZE);
    }

}
