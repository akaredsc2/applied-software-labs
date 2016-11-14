package ua.fpm.appsoft.linearProgramming;

import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ResourceUseSolverTest {

    @Test
    public void test3Variables3Constraints() throws Exception {
        LinearObjectiveFunction function = new LinearObjectiveFunction(new double[]{2, 2, 1}, 0);
        Set<LinearConstraint> constraints = new HashSet<>();
        constraints.add(new LinearConstraint(new double[]{1, 1, 0}, Relationship.GEQ, 1));
        constraints.add(new LinearConstraint(new double[]{1, 0, 1}, Relationship.GEQ, 1));
        constraints.add(new LinearConstraint(new double[]{0, 1, 0}, Relationship.GEQ, 1));
        LinearConstraintSet constraintSet = new LinearConstraintSet(constraints);

        SimplexSolver solver = new SimplexSolver();
        PointValuePair solution = solver.optimize(function, constraintSet, GoalType.MINIMIZE);

        Assert.assertEquals(0.0, solution.getPoint()[0], .0000001);
        Assert.assertEquals(1.0, solution.getPoint()[1], .0000001);
        Assert.assertEquals(1.0, solution.getPoint()[2], .0000001);
        Assert.assertEquals(3.0, solution.getValue(), .0000001);
    }

    @Test
    public void test2Variables4Constraints() throws Exception {
        LinearObjectiveFunction function = new LinearObjectiveFunction(new double[] {7, 5}, 0);
        Set<LinearConstraint> constraints = new HashSet<>();
        constraints.add(new LinearConstraint(new double[] {2, 3}, Relationship.LEQ, 19));
        constraints.add(new LinearConstraint(new double[] {2, 1}, Relationship.LEQ, 13));
        constraints.add(new LinearConstraint(new double[] {0, 3}, Relationship.LEQ, 15));
        constraints.add(new LinearConstraint(new double[] {3, 0}, Relationship.LEQ, 18));
        LinearConstraintSet constraintSet = new LinearConstraintSet(constraints);

        SimplexSolver solver = new SimplexSolver();
        PointValuePair solution = solver.optimize(function, constraintSet, GoalType.MAXIMIZE);
        System.out.println(Arrays.toString(solution.getPoint()));
        System.out.println(solution.getValue());
    }

}