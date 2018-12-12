package blockchain.CMDRedirect;

import blockchain.JDBC.MysqlUtil;
import org.moeaframework.problem.DTLZ.BCdata;
import java.util.List;


public class getResult {
    public static void main(String[] args){
        String TableName = "Etherchicks\n";
        double uncov = 0;
        long gas = 0;
        long time = 0;
        int num = 0;
        List<BCdata> listbcdata = MysqlUtil.GetBCdataFromTable(TableName);
        for(int i=36; i<listbcdata.size(); i++){
            num ++;
            uncov = uncov + listbcdata.get(i).getUncov();
            gas = gas + listbcdata.get(i).getGas();
            time = time + listbcdata.get(i).getTime();
        }
        double avecov = (100*num-uncov)/num;
        System.out.println("avecov = " + avecov);
        System.out.println("time = " + time/num);
        System.out.println("avegas = " + gas/num);
    }

    public static void getavenage(){

    }
}
