package org.moeaframework.problem.DTLZ;
import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;

public class MyDTLZ2 extends AbstractProblem {

    public MyDTLZ2() {
        super(11, 3);
    }

    /**
     * Constructs a new solution and defines the bounds of the decision
     * variables.
     */
    @Override
    public Solution newSolution() {
        Solution solution = new Solution(getNumberOfVariables(),
                getNumberOfObjectives());

        for (int i = 0; i < getNumberOfVariables(); i++) {
            solution.setVariable(i, new RealVariable(0.0, 1.0));
        }

        return solution;
    }

    /**
     * Extracts the decision variables from the solution, evaluates the
     * Rosenbrock function, and saves the resulting objective value back to
     * the solution.
     */
    @Override
    public void evaluate(Solution solution) {
        double[] x = EncodingUtils.getReal(solution);
        double[] f = new double[numberOfObjectives];

        int k = numberOfVariables - numberOfObjectives + 1;

        double g = 0.0;
        for (int i = numberOfVariables - k; i < numberOfVariables; i++) {
            g += Math.pow(x[i] - 0.5, 2.0);
        }

        for (int i = 0; i < numberOfObjectives; i++) {
            f[i] = 1.0 + g;

            for (int j = 0; j < numberOfObjectives - i - 1; j++) {
                f[i] *= Math.cos(0.5 * Math.PI * x[j]);
            }

            if (i != 0) {
                f[i] *= Math.sin(0.5 * Math.PI * x[numberOfObjectives - i - 1]);
            }
        }

        solution.setObjectives(f);
    }

}
