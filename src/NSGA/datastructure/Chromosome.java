/*
 * This repository / codebase is Open Source and free for use and rewrite.
 */
package NSGA.datastructure;

import NSGA.api.Service;
import blockchain.Generation.TestCase;
import org.moeaframework.problem.DTLZ.BCdata;

import java.util.ArrayList;
import java.util.List;

/**
 * this is a simulation of a biological chromosome that contains a genetic code, a fitness value,
 * a domination rank, an unique ID, and a list of dominated chromosomes.
 * 
 * @author  Debabrata Acharya <debabrata.acharya@icloud.com>
 * @version 1.1
 * @since   0.1
 */
public class Chromosome {
    
    public Chromosome() {
        this(100);
    }
    
    public Chromosome(final double uncov) {
        this(0, uncov, "");
    }
    
    public Chromosome(final int id, final double uncov, final String data) {
        this(id, uncov, data, 0);
        
    }

    public Chromosome(BCdata bcdata){
        this.ID = bcdata.getId();
        this.data = bcdata.getData();
        setTestCases(bcdata.getData());
        this.gas = bcdata.getGas();
        this.uncov = bcdata.getUncov();
        this.time = bcdata.getTime();
    }
    
    public Chromosome(final int id, final double uncov, final String data, final int rank) {
        this.ID = id;
        this.uncov = uncov;
        this.data = data;
        this.dominationRank = rank;
        this.dominatedChromosomes = new ArrayList<>();
    }


    private int ID;
    private double gas;
    private String data;
    private List<TestCase> TestCases;
    private double uncov;
    private double time;
//    private Allele[] geneticCode;
    private double fitness;
    private String extraInfo;
    private int dominationRank = 0;
    private String uniqueID;
    private List<Chromosome> dominatedChromosomes;

    public List<Chromosome> getDominatedChromosomes() {
        return dominatedChromosomes;
    }

    public void setDominatedChromosomes(List<Chromosome> dominatedChromosomes) {
        this.dominatedChromosomes = dominatedChromosomes;
    }

    public String getData() {
        return data;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setGas(double gas) {
        this.gas = gas;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setUncov(double uncov) {
        this.uncov = uncov;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getGas() {
        return gas;
    }

    public double getUncov() {
        return uncov;
    }

    public double getTime() {
        return time;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public int getID() {
        return ID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public int getDominationRank() {
        return dominationRank;
    }

    public void setDominationRank(int dominationRank) {
        this.dominationRank = dominationRank;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
//
//    public Allele[] getGeneticCode() {
//        return geneticCode;
//    }

//    /**
//     * the new fitness value is set as soon as a new genetic code is set for a chromosome.
//     * @param   geneticCode     the genetic code that the chromosome carries.
//     */
//    public void setGeneticCode(Allele[] geneticCode) {
//        this.geneticCode = geneticCode;
//        this.setFitness(Service.calculateFitness(geneticCode));
//    }


    public List<TestCase> getTestCases() {
        return TestCases;
    }

    public void setTestCases(List<TestCase> testCases) {
        TestCases = testCases;
    }

    public void setTestCases(String data) {
        List<TestCase> TestCases = new ArrayList<>();
        String[] temp1 = data.split("\\}\\);");
        StringBuffer sb1 = new StringBuffer();
        for(int i=0; i<temp1.length-1; i++){
            sb1.setLength(0);
            sb1.append(temp1[i]);
            sb1.append("});");
            TestCase TC = new TestCase(sb1.toString());
            TestCases.add(TC);
        }
        this.TestCases = TestCases;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    //run solcov
    public static Chromosome runsolcov(List<TestCase> testCases){
        return null;

    }


}
