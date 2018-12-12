package org.moeaframework.problem.DTLZ;

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.EncodingUtils;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;

import java.util.ArrayList;
import java.util.List;

public class BCTest extends AbstractProblem {

//    private List<Solution> injectedSolutions = new ArrayList<>();
//
//    private List<BCdata> listbcdata;
//
//    public List<BCdata> getListbcdata() {
//        return listbcdata;
//    }
//
//    public void initListbcdata(List<BCdata> listbcdata) {
//        BCdata data1 = new BCdata(105,12071966,50){};
//        BCdata data2 = new BCdata(104,12071966,50){};
//        BCdata data3 = new BCdata(106,12071966,50){};
//        BCdata data4 = new BCdata(105,12071965,50){};
//        BCdata data5 = new BCdata(105,12071967,50){};
//        BCdata data6 = new BCdata(105,12071966,49){};
//        BCdata data7 = new BCdata(105,12071966,51){};
//        BCdata data8 = new BCdata(106,12071963,50){};
//        BCdata data9 = new BCdata(105,12071980,49){};
//        BCdata data10 = new BCdata(100,12071962,60){};
//        listbcdata.add(data1);
//        listbcdata.add(data2);
//        listbcdata.add(data3);
//        listbcdata.add(data4);
//        listbcdata.add(data5);
//        listbcdata.add(data6);
//        listbcdata.add(data7);
//        listbcdata.add(data8);
//        listbcdata.add(data9);
//        listbcdata.add(data10);
//        this.listbcdata = listbcdata;
//    }

    /**
     * Constructs a new instance of the DTLZ2 function, defining it
     * to include 11 decision variables and 2 objectives.
     */
    public BCTest() {
        super(0, 3);
    }

    /**
     * Constructs a new solution and defines the bounds of the decision
     * variables.
     */
    @Override
    public Solution newSolution() {
        Solution solution = new Solution(0,3);
        return solution;
    }

    /**
     * Extracts the decision variables from the solution, evaluates the
     * Rosenbrock function, and saves the resulting objective value back to
     * the solution.
     */
    @Override
    public void evaluate(Solution solution) {
        return;
    }

}
