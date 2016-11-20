package ua.fpm.appsoft.transportation;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MinimalElementTransportationSolverTest {

    private MinimalElementTransportationSolver solver;

    @Before
    public void setUp() throws Exception {
        double[][] costsPerUnit = new double[][]{
                {7, 8, 1, 2},
                {4, 5, 9, 8},
                {9, 2, 3, 6}
        };
        double[] supplies = new double[]{200, 180, 190};
        double[] demands = new double[]{150, 130, 150, 140};
        solver = new MinimalElementTransportationSolver(costsPerUnit, supplies, demands);
    }

    @Test
    public void getInitialFeasibleSolution() throws Exception {
//        System.out.println(Arrays.deepToString(solver.getInitialFeasibleSolution()));
    }

    @Test
    public void isFeasibleSolution() throws Exception {
        solver.calculatePotentials(solver.getInitialFeasibleSolution());
    }

    @Test
    public void getOptimalSolution() throws Exception {
        assertEquals(1560, solver.getTotalCost(solver.getOptimalSolution()), 0.001);

        double[][] costs = new double[][]{
                {7, 4, 8, 3, 6},
                {5, 5, 4, 3, 8},
                {5, 6, 5, 8, 6}
        };
        double[] supplies = new double[]{70, 80, 90};
        double[] demands = new double[]{30, 30, 60, 90, 30};
        solver = new MinimalElementTransportationSolver(costs, supplies, demands);
        System.out.println(Arrays.deepToString(solver.getOptimalSolution()));
    }

    @Test
    public void getTotalCost() throws Exception {
        assertEquals(1710, solver.getTotalCost(solver.getInitialFeasibleSolution()), 0.001);
    }

}