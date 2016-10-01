package ua.fpm.appsoft.linear_equations_system;

import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

public interface LinearSystemDecompositionSolver {

    RealVector solve();

    RealMatrix getBottomTriangleMatrix();

    RealMatrix getUpperTriangleMatrix();

}
