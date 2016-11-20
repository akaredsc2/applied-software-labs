package ua.fpm.appsoft.transportation;

public interface TransportationProblemSolver {

    double[][] getInitialFeasibleSolution();

    double[][] calculatePotentials(double[][] solution);

    double[][] getOptimalSolution();

    double getTotalCost(double[][] solution);

}
