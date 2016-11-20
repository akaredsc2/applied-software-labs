package ua.fpm.appsoft.transportation;

import java.util.Arrays;

import static java.util.Arrays.stream;

public class MinimalElementTransportationSolver implements TransportationProblemSolver {

    private double[][] costsPerUnit;
    private double[] supplies;
    private double[] demands;

    public MinimalElementTransportationSolver(double[][] costsPerUnit, double[] supplies, double[] demands) {
        this.supplies = supplies;
        this.demands = demands;

        double totalSupplies = stream(supplies).sum();
        double totalDemands = stream(demands).sum();
        if (totalSupplies == totalDemands) {
            this.costsPerUnit = costsPerUnit;
        } else if (totalSupplies > totalDemands) {
            //todo handle
        } else {
            //todo handle
        }

    }

    @Override
    public double[][] getInitialFeasibleSolution() {
        double[] innerDemands = demands.clone();
        double[] innerSupplies = supplies.clone();

        double[][] feasiblePlan = new double[supplies.length][demands.length];
        boolean[][] filledElements = new boolean[supplies.length][demands.length];

        int filledElementsCount = 0;
        do {
            int iMin = 0;
            int jMin = 0;
            double min = Double.POSITIVE_INFINITY;

            for (int i = 0; i < feasiblePlan.length; i++) {
                for (int j = 0; j < feasiblePlan[i].length; j++) {
                    if (!filledElements[i][j] && costsPerUnit[i][j] < min) {
                        min = costsPerUnit[i][j];
                        iMin = i;
                        jMin = j;
                    }
                }
            }

            //todo refactor
            if (innerDemands[jMin] < innerSupplies[iMin]) {
                feasiblePlan[iMin][jMin] = innerDemands[jMin];
                innerSupplies[iMin] -= innerDemands[jMin];
                for (int i = 0; i < feasiblePlan.length; i++) {
                    if (!filledElements[i][jMin]) {
                        filledElementsCount += 1;
                    }
                    filledElements[i][jMin] = true;
                }
            } else {
                feasiblePlan[iMin][jMin] = innerSupplies[iMin];
                innerDemands[jMin] -= innerSupplies[iMin];
                for (int j = 0; j < feasiblePlan[iMin].length; j++) {
                    if (!filledElements[iMin][j]) {
                        filledElementsCount += 1;
                    }
                    filledElements[iMin][j] = true;
                }
            }

        } while (filledElementsCount < supplies.length * demands.length);

        return feasiblePlan;
    }

    @Override
    public double[][] calculatePotentials(double[][] solution) {
        double[][] potentials = new double[supplies.length][demands.length];
        double[] supplierPotentials = new double[supplies.length];
        Boolean[] isFilledSupplierPotentials = new Boolean[supplies.length];
        Arrays.fill(isFilledSupplierPotentials, false);

        double[] demandsPotentials = new double[demands.length];
        Boolean[] isFilledDemandsPotentials = new Boolean[demands.length];
        Arrays.fill(isFilledDemandsPotentials, false);

        supplierPotentials[0] = 0;
        isFilledSupplierPotentials[0] = true;
        do {
            for (int i = 0; i < supplierPotentials.length; i++) {
                for (int j = 0; j < demandsPotentials.length; j++) {
                    if (solution[i][j] != 0) {
                        if (isFilledSupplierPotentials[i] && !isFilledDemandsPotentials[j]) {
                            demandsPotentials[j] = costsPerUnit[i][j] - supplierPotentials[i];
                            isFilledDemandsPotentials[j] = true;
                        }
                        if (!isFilledSupplierPotentials[i] && isFilledDemandsPotentials[j]) {
                            supplierPotentials[i] = costsPerUnit[i][j] - demandsPotentials[j];
                            isFilledSupplierPotentials[i] = true;
                        }
                    }
                }
            }
        } while (Arrays.stream(isFilledSupplierPotentials).filter(x -> !x).count() > 0 ||
                Arrays.stream(isFilledDemandsPotentials).filter(x -> !x).count() > 0);

        for (int i = 0; i < potentials.length; i++) {
            for (int j = 0; j < potentials[i].length; j++) {
                potentials[i][j] = costsPerUnit[i][j] - (supplierPotentials[i] + demandsPotentials[j]);
            }
        }

        System.out.println(Arrays.deepToString(potentials));

        return potentials;
    }

    @Override
    public double[][] getOptimalSolution() {
        double[][] solution = this.getInitialFeasibleSolution();
        double[][] potential = calculatePotentials(solution);

        return new double[0][];
    }

    @Override
    public double getTotalCost(double[][] solution) {
        double result = 0;
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                result += solution[i][j] * costsPerUnit[i][j];
            }
        }
        return result;
    }

}
