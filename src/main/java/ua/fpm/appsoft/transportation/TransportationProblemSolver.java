package ua.fpm.appsoft.transportation;

/**
 * There is a type of linear programming problem that may be solved using a
 * simplified version of the simplex technique called transportation method.
 * Because of its major application in solving problems involving several product
 * sources and several destinations of products, this type of problem is frequently
 * called the transportation problem.
 *
 * <p>Let us assume there are m sources supplying n destinations. Source capacities,
 * destinations requirements and costs of material shipping from each source to each destination
 * are given constantly.
 *
 * <p>The two common objectives of such problems are either
 * (1) minimize the cost of shipping m units to n destinations or
 * (2) maximize the profit of shipping m units to n destinations.
 */
public interface TransportationProblemSolver {

    /**
     * returns feasible solution satisfying supply and demand constraints
     * @return initial feasible solution satisfying supply and demand constraints
     */
    double[][] getInitialFeasibleSolution();

    /**
     * returns optimal solution satisfying supply and demand constraints
     * @return optimal feasible solution satisfying supply and demand constraints
     */
    double[][] getOptimalSolution();

    /**
     * returns total cost of transportation based on given solution
     * @param solution one of solutions of transportation problem
     * @return total cost of transportation
     */
    double getTotalCost(double[][] solution);

}
