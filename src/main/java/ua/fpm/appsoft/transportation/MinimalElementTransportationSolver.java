package ua.fpm.appsoft.transportation;

import java.util.ArrayDeque;
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
        int degeneracy = solutionDegeneracy(solution);
        if (degeneracy > 0) {
            addFictionalTransportation(solution, degeneracy);
        }
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

        return potentials;
    }

    private void addFictionalTransportation(double[][] solution, int amount) {
        for (int i = 0; i < amount; i++) {
            int supplyToChange;
            int demandToChange;
            do {
                supplyToChange = (int) (Math.random() % demands.length);
                demandToChange = (int) (Math.random() % demands.length);
            } while (solution[supplyToChange][demandToChange] != 0);
            demands[demandToChange] += 0.001;
            supplies[supplyToChange] += 0.001;
            solution[supplyToChange][demandToChange] += 0.001;
        }
    }

    @Override
    public double[][] getOptimalSolution() {
        double[][] solution = this.getInitialFeasibleSolution();
        double[][] potentials = calculatePotentials(solution);

        while (!isOptimal(potentials)) {
            boolean[][] isMarked = new boolean[supplies.length][demands.length];

            ArrayDeque<Point> cycle = getCycle(solution, isMarked, potentials);

            double amountToTransfer = getCalculateAmountToTransfer(cycle, solution);

            doTransfer(amountToTransfer, cycle, solution);

            potentials = calculatePotentials(solution);
        }
        return solution;
    }

    private int solutionDegeneracy(double[][] solution) {
        int nonZerosCount = 0;
        for (int i = 0; i < solution.length; i++) {
            for (int j = 0; j < solution[i].length; j++) {
                if (solution[i][j] > 0) {
                    nonZerosCount += 1;
                }
            }
        }
        return (demands.length + supplies.length - 1) - nonZerosCount;
    }

    private boolean isOptimal(double[][] potentials) {
        for (double[] potential : potentials) {
            for (double aPotential : potential) {
                if (aPotential < 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void doTransfer(double amountToTransfer, ArrayDeque<Point> cycle, double[][] solution) {
        ArrayDeque<Point> cycleClone = cycle.clone();
        for (int i = 0; i < cycle.size(); i += 2) {
            solution[cycleClone.peekFirst().i][cycleClone.peekFirst().j] += amountToTransfer;
            cycleClone.pollFirst();
            solution[cycleClone.peekFirst().i][cycleClone.peekFirst().j] -= amountToTransfer;
            cycleClone.pollFirst();
        }
    }

    private double getCalculateAmountToTransfer(ArrayDeque<Point> cycle, double[][] solution) {
        double min = Double.POSITIVE_INFINITY;
        ArrayDeque<Point> cycleClone = cycle.clone();
        for (int i = 0; i < cycle.size(); i += 2) {
            cycleClone.pollFirst();
            if (solution[cycleClone.peekFirst().i][cycleClone.peekFirst().j] < min) {
                min = solution[cycleClone.peekFirst().i][cycleClone.peekFirst().j];
            }
            cycleClone.pollFirst();
        }
        return min;
    }

    private ArrayDeque<Point> getCycle(double[][] solution, boolean[][] isMarked, double[][] potentials) {
        ArrayDeque<Point> cycle = new ArrayDeque<>();
        Point cycleHead = findCycleHead(potentials);
        cycle.add(cycleHead);

        boolean isNextIterationVertical = true;
        do {
            if (isNextIterationVertical) {
                Point searchVerticalResult = searchVertical(cycle.getLast(), solution, isMarked);
                if (searchVerticalResult != null) {
                    cycle.add(searchVerticalResult);
                    if (searchVerticalResult.i == cycle.getFirst().i) {
                        break;
                    }
                } else {
                    Point lastPoint = cycle.pollLast();
                    isMarked[lastPoint.i][lastPoint.j] = true;
                }
                isNextIterationVertical = false;
            } else {
                Point searchHorizontalResult = searchHorizontal(cycle.getLast(), solution, isMarked);
                if (searchHorizontalResult != null) {
                    cycle.add(searchHorizontalResult);
                } else {
                    Point lastPoint = cycle.pollLast();
                    isMarked[lastPoint.i][lastPoint.j] = true;
                }
                isNextIterationVertical = true;
            }
        } while (!cycle.getLast().equals(cycle.getFirst()));
        return cycle;
    }

    private Point searchVertical(Point fromPoint, double[][] solution, boolean[][] isMarked) {
        for (int i = 0; i < solution.length; i++) {
            if (solution[i][fromPoint.j] == 0) {
                isMarked[i][fromPoint.j] = true;
            }
            if (solution[i][fromPoint.j] != 0 && !isMarked[i][fromPoint.j] && i != fromPoint.i) {
                return new Point(i, fromPoint.j);
            }
        }
        return null;
    }

    private Point searchHorizontal(Point fromPoint, double[][] solution, boolean[][] isMarked) {
        for (int j = 0; j < solution[fromPoint.i].length; j++) {
            if (solution[fromPoint.i][j] == 0) {
                isMarked[fromPoint.i][j] = true;
            }
            if (solution[fromPoint.i][j] != 0 && !isMarked[fromPoint.i][j] && j != fromPoint.j) {
                return new Point(fromPoint.i, j);
            }
        }
        return null;
    }

    private Point findCycleHead(double[][] potentials) {
        double min = Double.POSITIVE_INFINITY;
        Point minPoint = new Point(0, 0);
        for (int i = 0; i < potentials.length; i++) {
            for (int j = 0; j < potentials[i].length; j++) {
                if (potentials[i][j] < 0 && potentials[i][j] < min) {
                    min = potentials[i][j];
                    minPoint = new Point(i, j);
                }
            }
        }
        return minPoint;
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

    private static class Point {

        int i;
        int j;

        public Point(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString() {
            return "Point{" +
                    "i=" + i +
                    ", j=" + j +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Point point = (Point) o;

            if (i != point.i) return false;
            return j == point.j;

        }

        @Override
        public int hashCode() {
            int result = i;
            result = 31 * result + j;
            return result;
        }
    }

}