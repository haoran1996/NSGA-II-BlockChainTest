/*
 * This repository / codebase is Open Source and free for use and rewrite.
 */
package NSGA.api;


import NSGA.configuration.Configuration;
import NSGA.datastructure.Allele;
import NSGA.datastructure.Chromosome;
import NSGA.datastructure.Population;
import blockchain.CMDRedirect.CMDRedirect;
import blockchain.CMDRedirect.Main;
import blockchain.ExtractUtils.GetTotalCoverageInfo;
import blockchain.ExtractUtils.GetTotalGasUsed;
import blockchain.Generation.TestCase;
import blockchain.Generation.TestFile;
import blockchain.JDBC.MysqlUtil;
import blockchain.Jnotify.FileNotify;
import org.moeaframework.problem.DTLZ.BCdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static blockchain.CMDRedirect.CMDUtils.KillNodeProcess;
import static blockchain.CMDRedirect.Main.ProjectPath;
import static blockchain.CMDRedirect.Main.TableName;
import static blockchain.CMDRedirect.Main.uncov;
import static blockchain.Generation.RandomTypeGeneration.GenerateRandomNumber;
import static blockchain.Generation.WriteUtil.ClassifyTestCases;
import static blockchain.Generation.WriteUtil.WriteFileFromTestFile;

/**
 * This is the synthesis class that does many of the under-the-hood work (biological simulation) that is abstracted/encapsulated
 * by other classes at the business/controller layer.
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 1.1
 * @since   0.2
 */
public class Synthesis {
    
    private static final Random LOCAL_RANDOM = new Random();
    
    /**
     * depending on the settings available in the Configuration.java file, this method synthesizes a
     * random population of chromosomes with pseudo-randomly generated genetic code for each chromosome.
     * 
     * @return  a randomly generated population
     */
    public static Population InitPopulation(String dbname) {
        List<BCdata> listbcdata = MysqlUtil.GetBCdataFromTable(dbname);
        List<Chromosome> populace = new ArrayList<>();
        for(int i=0; i<listbcdata.size(); i++){
            Chromosome c = new Chromosome(listbcdata.get(i));
            populace.add(c);
        }

//        for(int i = 0; i < Configuration.getPOPULATION_SIZE(); i++) {
//
//            Chromosome chromosome = new Chromosome();
//            chromosome.setGeneticCode(synthesizeGeneticCode(Configuration.getCHROMOSOME_LENGTH()));
//            populace.add(chromosome);
//        }

        return new Population(populace);
    }
    
    /**
     * a child population of the same size as the parent is synthesized from the parent population
     * 交叉变异
     * 
     * @param   parent  the parent population object
     * @return          a child population synthesized from the parent population
     */
    public static Population synthesizeChild(Population parent) {

        Population child = new Population();
        List<Chromosome> populace = new ArrayList<>();

        /**
         * child chromosomes undergo crossover and mutation.
         * the child chromosomes are selected using binary tournament selection.
         * crossover returns an array of exactly two child chromosomes synthesized from two parent
         * chromosomes.
         */
        if (Configuration.getPOPULATION_SIZE() % 2 == 0){
            while(populace.size() < Configuration.getPOPULATION_SIZE()){
                for(Chromosome chromosome : crossover(binaryTournamentSelection(parent), binaryTournamentSelection(parent)))
                    populace.add(mutation(chromosome));
            }
        }else{
            while(populace.size() < Configuration.getPOPULATION_SIZE()-2){
                for(Chromosome chromosome : crossover(binaryTournamentSelection(parent), binaryTournamentSelection(parent)))
                    populace.add(mutation(chromosome));
            }
            populace.add(mutation(binaryTournamentSelection(parent)));
        }
        child.setPopulace(populace);
        return child;
    }

    /**
     * this is an implementation of basic binary tournament selection.
     * for a tournament of size t, select t individuals (randomly) from population and determine winner of
     * tournament with the highest fitness value.
     * in case of binary tournament selection, t = 2.
     * 
     * refer [https://stackoverflow.com/questions/36989783/binary-tournament-selection] for more information.
     * 
     * @param   population  the population from which a child chromosome is to be selected
     * @return              the selected child chromosome
     */
    private static Chromosome binaryTournamentSelection(Population population) {

        Chromosome individual1 = population.getPopulace().get(LOCAL_RANDOM.nextInt(population.getPopulace().size()));
        Chromosome individual2 = population.getPopulace().get(LOCAL_RANDOM.nextInt(population.getPopulace().size()));

        if(individual1.getUncov() < individual2.getUncov()) return individual1;
        else if(individual1.getUncov() == individual2.getUncov()){
            if(individual1.getGas() < individual2.getGas()){
                return individual1;
            }
            else return individual2;
        }
        else return individual2;
    }

    /**
     * this is a basic implementation of uniform crossover where the crossover/break point is the middle
     * of the chromosomes. The genetic code of both the parent chromosomes are broken from the middle
     * and crossover is done to create two child chromosomes.
     * crossover probability is considered.
     * 
     * @param   chromosome1     the first parent chromosome taking part in crossover
     * @param   chromosome2     the second parent chromosome taking part in crossover
     * @return                  an array of exactly two child chromosomes synthesized from two parent chromosomes.
     */
    public static Chromosome[] crossover(Chromosome chromosome1, Chromosome chromosome2) {

//        Allele[] geneticCode1 = new Allele[Configuration.getCHROMOSOME_LENGTH()];
//        Allele[] geneticCode2 = new Allele[Configuration.getCHROMOSOME_LENGTH()];
//        Allele[] chromosome1geneCode = chromosome1.getGeneticCode();
//        Allele[] chromosome2geneCode = chromosome2.getGeneticCode();
        List<TestCase> testcases1 = new ArrayList<>();
        List<TestCase> testcases2 = new ArrayList<>();
        List<TestCase> chromosome1Testcases = chromosome1.getTestCases();
        List<TestCase> chromosome2Testcases = chromosome2.getTestCases();
        Chromosome[] childChromosomes = {new Chromosome(), new Chromosome()};
        int breakPoint1 = GenerateRandomNumber(0,chromosome1Testcases.size());
        int breakPoint2 = GenerateRandomNumber(0,chromosome2Testcases.size());
        /**
         * generating a new random float value and if this value is less than equal to the
         * crossover probability mentioned in the Configuration file, then crossover occurs,
         * otherwise the parents themselves are copied as child chromosomes.
         */
        if(LOCAL_RANDOM.nextFloat() <= Configuration.getCROSSOVER_PROBABILITY()) {

//            for(int i = 0; i < Configuration.getCHROMOSOME_LENGTH(); i++) {
//
//                if(i <= breakPoint) {
//                    geneticCode1[i] = chromosome1geneCode[i];
//                    geneticCode2[i] = chromosome2geneCode[i];
//                } else {
//                    geneticCode1[i] = chromosome2geneCode[i];
//                    geneticCode2[i] = chromosome1geneCode[i];
//                }
//            }
//
//            childChromosomes[0].setGeneticCode(geneticCode1);
//            childChromosomes[1].setGeneticCode(geneticCode2);
//        } else {
//            childChromosomes[0] = chromosome1;
//            childChromosomes[1] = chromosome2;

            //两种交叉方式
            if(LOCAL_RANDOM.nextFloat() <= 0.7){
                for(int i = 0; i < breakPoint1; i++){
                    testcases1.add(chromosome1Testcases.get(i));
                }
                for(int j = breakPoint2; j < chromosome2Testcases.size(); j++){
                    testcases1.add(chromosome2Testcases.get(j));
                }
                for(int i = 0; i < breakPoint2; i++){
                    testcases2.add(chromosome2Testcases.get(i));

                }
                for(int j = breakPoint1; j < chromosome1Testcases.size(); j++){
                    testcases2.add(chromosome1Testcases.get(j));
                }
            }
            else{//交错交叉
                for(int i = 0; i < breakPoint2; i++){//list1里面有b2个
                    testcases1.add(chromosome2Testcases.get(chromosome2Testcases.size()-i-1));
                }
                for(int j = breakPoint1; j < chromosome1Testcases.size(); j++){
                    testcases1.add(chromosome1Testcases.get(j));
                }
                for(int i = 0; i < breakPoint1; i++){
                    testcases2.add(chromosome1Testcases.get(chromosome1Testcases.size()-i-1));
                }
                for(int j = breakPoint2; j < chromosome2Testcases.size(); j++){
                    testcases2.add(chromosome2Testcases.get(j));
                }
            }
            childChromosomes[0].setTestCases(testcases1);
            childChromosomes[1].setTestCases(testcases2);
        }else{
            childChromosomes[0] = chromosome1;
            childChromosomes[1] = chromosome2;
        }
        return childChromosomes;
    }
    
    /**
     * in this mutation operation implementation, a random bit-flip takes place.
     * a random float value is generated and if this value is less than equal to the mutation
     * probability defined in Configuration, then mutation takes place, otherwise the original
     * chromosome is returned.
     * 
     * @param   chromosome  the chromosome over which the mutation takes place
     * @return              the mutated chromosome
     */
    private static Chromosome mutation(Chromosome chromosome) {
        List<TestCase> testCaseList = chromosome.getTestCases();

        try {
            if(LOCAL_RANDOM.nextFloat() <= 0.03) {
    //            Allele[] geneticCode = chromosome.getGeneticCode();
    //            geneticCode[LOCAL_RANDOM.nextInt(geneticCode.length)].bitFlip();
    //            chromosome.setGeneticCode(geneticCode);
                testCaseList.remove(testCaseList.size()-1);//delete
            }
            if(LOCAL_RANDOM.nextFloat() <= 0.03){
                Collections.swap(testCaseList,0,testCaseList.size()-1);//swap
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        writeintodb(chromosome);
        List<BCdata> listbcdata = MysqlUtil.GetBCdataFromTable(TableName);
        BCdata bCdata = listbcdata.get(listbcdata.size()-1);
        Chromosome lastestchromsome = new Chromosome(bCdata);
        return lastestchromsome;
    }



    public static void writeintodb(Chromosome chromosome){
        Main.databf.setLength(0);
        try {
            Main.tcnum = 0;
            List<TestCase> chromosomeTestcases = chromosome.getTestCases();
            List<TestFile> testFiles = ClassifyTestCases(chromosomeTestcases);
            for(int i = 0; i < testFiles.size(); i++){
                WriteFileFromTestFile(testFiles.get(i));
                System.out.println("生成:testName = " + testFiles.get(i).getFileName());
                String s = TestFile.getTestcasesString(testFiles.get(i).getTestCases());
                Main.databf.append(s);
                Main.tcnum = Main.tcnum + testFiles.get(i).getTestCases().size();
            }
            runsolcov();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void runsolcov(){
//        String ProjectPath = "F:\\blockchain\\Test20181124\\UranBank";
        try {
            //确保Node进程关闭
            KillNodeProcess();
            //运行solidity-coverage
            CMDRedirect.RunTestrpc(ProjectPath);
            CMDRedirect.RunSolcov(ProjectPath);
            FileNotify.notify(ProjectPath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    /**
     * a genetic code as an array of Alleles is synthesized.
     * refer Allele.java for more information.
     * 
     * @param   length  the required length of the genetic code.
     * @return          the synthesized genetic code.
     */
//    public static Allele[] synthesizeGeneticCode(final int length) {
//
//        Allele[] geneticCode = new Allele[length];
//
//        for(int i = 0; i < length; i++) geneticCode[i] = synthesizeAllele();
//
//        return geneticCode;
//    }

    /**
     * an allele object with a randomly selected boolean gene value is synthesized.
     *
     * @return  a randomly generated Allele object
     */
//    public static Allele synthesizeAllele() {
//        return new Allele(LOCAL_RANDOM.nextBoolean());

}
