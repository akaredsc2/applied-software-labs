package ua.fpm.appsoft.transportation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MinimalElementTransportationSolverTest {

    private MinimalElementTransportationSolver solver;

    @Test
    public void testNonDegenerateSolution() throws Exception {
        double[][] costsPerUnit = new double[][]{
                {7, 8, 1, 2},
                {4, 5, 9, 8},
                {9, 2, 3, 6}
        };
        double[] supplies = new double[]{200, 180, 190};
        double[] demands = new double[]{150, 130, 150, 140};
        solver = new MinimalElementTransportationSolver(costsPerUnit, supplies, demands);
        assertEquals(1560, solver.getTotalCost(solver.getOptimalSolution()), 0.001);
    }

    @Test(timeout = 1000)
    public void testTask() throws Exception {
        double[][] costsPerUnit = new double[][]{
                {5, 3, 24, 10, 25},
                {30, 2, 22, 16, 7},
                {30, 24, 27, 29, 10},
                {15, 17, 21, 2, 3}
        };
        double[] supplies = new double[]{24, 15, 16, 24};
        double[] demands = new double[]{12, 13, 14, 31, 9};
        solver = new MinimalElementTransportationSolver(costsPerUnit, supplies, demands);
        assertEquals(642, solver.getTotalCost(solver.getOptimalSolution()), 0.001);

    }

    @Test(timeout = 1000)
    public void testDegenerateSolution() throws Exception {
        double[][] costsPerUnit = new double[][]{
                {7, 4, 8, 3, 6},
                {5, 5, 4, 3, 8},
                {5, 6, 5, 8, 6}
        };
        double[] supplies = new double[]{70, 80, 90};
        double[] demands = new double[]{30, 30, 60, 90, 30};
        solver = new MinimalElementTransportationSolver(costsPerUnit, supplies, demands);
        assertEquals(990, solver.getTotalCost(solver.getOptimalSolution()), 0.001);
    }

    @Test(timeout = 1000)
    public void testAmountBiggerThanDemands() throws Exception {
        double[][] costsPerUnit = new double[][]{
                {2, 3, 4},
                {1, 2, 3},
                {4, 1, 2},
                {3, 1, 1}
        };
        double[] supplies = new double[]{20, 30, 40, 20};
        double[] demands = new double[]{40, 40, 20};
        solver = new MinimalElementTransportationSolver(costsPerUnit, supplies, demands);
        assertEquals(110, solver.getTotalCost(solver.getOptimalSolution()), 0.001);

    }

    @Test(timeout = 1000)
    public void testDemandsBiggerThanAmount() throws Exception {
        double[][] costsPerUnit = new double[][]{
                {4, 9, 2, 5},
                {12, 11, 3, 4}
        };
        double[] supplies = new double[]{120, 100};
        double[] demands = new double[]{40, 180, 80, 60};
        solver = new MinimalElementTransportationSolver(costsPerUnit, supplies, demands);
        assertEquals(960, solver.getTotalCost(solver.getOptimalSolution()), 0.001);
    }

}