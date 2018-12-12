package org.moeaframework;

import blockchain.JDBC.MysqlUtil;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.operator.InjectedInitialization;
import org.moeaframework.problem.DTLZ.BCTest;
import org.moeaframework.problem.DTLZ.BCdata;

import java.util.ArrayList;
import java.util.List;

public class BlockChainTest {

    public static void GetParet() {
        String TableName = "Etherchicks\n";
        double uncov = 0;
        long gas = 0;
        long time = 0;
        Problem problem = new BCTest();
        List<BCdata> listbcdata = MysqlUtil.GetBCdataFromTable(TableName);
        List<Solution> Solutions = new ArrayList<>();



//        for(int i = 0; i < 10; i++){



        for(int i = 10; i <listbcdata.size(); i++){



            Solution s = new Solution(listbcdata.get(i));
            Solutions.add(s);
        }

//        InjectedInitialization initialization = new InjectedInitialization(
//                problem, 20, Solutions);
//
//        Solution[] solutions = initialization.initialize();

        NondominatedPopulation mergedSolutions = new NondominatedPopulation(Solutions);
        //display the results
        System.out.format("    result   %n%n");

        for (Solution s : mergedSolutions) {
//            double cov = 100 - s.getObjective(1);
            System.out.println(" " +
                    s.getObjective(0) + "   "
                    + s.getObjective(1)+ "   "
                    + s.getObjective(2));
        }
    }
    public static void main(String[] args){
        GetParet();
    }
}
