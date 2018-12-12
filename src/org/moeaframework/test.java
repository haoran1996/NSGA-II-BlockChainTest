package org.moeaframework;

import blockchain.JDBC.MysqlUtil;
import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.*;
import org.moeaframework.core.comparator.ChainedComparator;
import org.moeaframework.core.comparator.CrowdingComparator;
import org.moeaframework.core.comparator.ParetoDominanceComparator;
import org.moeaframework.core.operator.GAVariation;
import org.moeaframework.core.operator.InjectedInitialization;
import org.moeaframework.core.operator.TournamentSelection;
import org.moeaframework.core.operator.real.PM;
import org.moeaframework.core.operator.real.SBX;
import org.moeaframework.problem.DTLZ.BCTest;
import org.moeaframework.problem.DTLZ.BCdata;

import java.util.ArrayList;
import java.util.List;

public class test {
//    public static void main(String[] args) {
//        //configure and run this experiment
//        NondominatedPopulation result = new Executor()
//                .withProblem("UF1")
//                .withAlgorithm("NSGAII")
//                .withMaxEvaluations(30)
//                .run();
//
//        //display the results
//        System.out.format("Objective1  Objective2%n");
//
//        for (Solution solution : result) {
//            System.out.format("%.4f      %.4f%n",
//                    solution.getObjective(0),
//                    solution.getObjective(1));
//        }
//    }



    public static void GetParet() {
        Problem problem = new BCTest();
        List<BCdata> listbcdata = MysqlUtil.GetBCdataFromTable("CryptoTomatoes");
        List<Solution> Solutions = new ArrayList<>();
        for(int i=0; i<listbcdata.size(); i++){
            Solution s = new Solution(listbcdata.get(i));
            Solutions.add(s);
        }

        InjectedInitialization initialization = new InjectedInitialization(
                problem, 10, Solutions);

//        Solution[] solutions = initialization.initialize();
//
//        NondominatedPopulation mergedSolutions = new NondominatedPopulation(Solutions);
//        //display the results

        TournamentSelection selection = new TournamentSelection(2,
                new ChainedComparator(
                        new ParetoDominanceComparator(),
                        new CrowdingComparator()));


        Variation variation = new GAVariation(
                new SBX(1.0, 25.0),
                new PM(1.0 / problem.getNumberOfVariables(), 30.0));

        NSGAII algorithm = new NSGAII(
                problem,
                new NondominatedSortingPopulation(),
                null, // no archive
                selection,
                variation,
                initialization);

        while (algorithm.getNumberOfEvaluations() < 1000) {
            algorithm.step();
        }

        NondominatedPopulation finalResult = algorithm.getResult();

        System.out.format("tcnum       gas       uncov%n");

        for (Solution s : finalResult) {
            System.out.println(" " +
                    s.getObjective(0) + "   " +
                    s.getObjective(1) + "   " +
                    s.getObjective(2));
        }
    }

    public static void main(String[] args){
        GetParet();
    }

}
