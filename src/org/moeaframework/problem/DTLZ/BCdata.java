package org.moeaframework.problem.DTLZ;

import java.util.ArrayList;

public class BCdata {
    private int id;
    private String data;
    private int tcnum;
    private long gas;
    private double uncov;
    private long time;

    public BCdata(int id){
        this(id,null,0,0,0,0);
    }
    public BCdata(int id, String data, int tcnum, long gas, double uncov, long time) {
        this.id = id;
        this.data = data;
        this.tcnum = tcnum;
        this.gas = gas;
        this.uncov = uncov;
        this.time = time;
    }

    public BCdata(int id, long gas, double uncov, long time) {
        this.id = id;
        this.gas = gas;
        this.uncov = uncov;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public int getTcnum() {
        return tcnum;
    }

    public long getGas() {
        return gas;
    }

    public double getUncov() {
        return uncov;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setTcnum(int tcnum) {
        this.tcnum = tcnum;
    }

    public void setGas(long gas) {
        this.gas = gas;
    }

    public void setUncov(double uncov) {
        this.uncov = uncov;
    }

//    ArrayList listbcdata = new ArrayList();
//    double[] data1 = {105,12071966,50};
//    double[] data2 = {104,12071966,50};
//    double[] data3 = {106,12071966,50};
//    double[] data4 = {105,12071965,50};
//    double[] data5 = {105,12071967,50};
//    double[] data6 = {105,12071966,49};
//    double[] data7 = {105,12071966,51};
//    double[] data8 = {106,12071963,50};
//    double[] data9 = {105,12071980,49};
//    double[] data10 = {100,12071962,60};
}
