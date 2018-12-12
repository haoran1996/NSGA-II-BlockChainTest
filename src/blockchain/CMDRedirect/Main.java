package blockchain.CMDRedirect;


import blockchain.ExtractUtils.GetTotalCoverageInfo;
import blockchain.ExtractUtils.GetTotalGasUsed;
import blockchain.Generation.TestCaseGeneration;
import blockchain.JDBC.MysqlUtil;
import blockchain.Jnotify.FileNotify;
import org.moeaframework.problem.DTLZ.BCdata;

import java.util.ArrayList;
import java.util.List;

import static blockchain.CMDRedirect.CMDUtils.KillNodeProcess;

public class Main {
    public static StringBuffer databf = new StringBuffer();
    public static int tcnum = 0;
    public static long gas = 0;
    public static double uncov = 0;
    public static List<BCdata> listbcdata = new ArrayList<>();
    public static String sql_statement;
    public static long begintime = 0;
    public static long endtime = 0;
    public static long time = 0;
    public static String ProjectPath;
    public static String DirPath;
    public static String TableName;


    public static void main(String[] args){

        run("F:\\blockchain\\Test20181124\\Etherchicks","Etherchicks");
    }

    public static void run(String projectPath, String tableName){
        //路径
        Main.ProjectPath = projectPath;
        //表名
        Main.TableName = tableName;
        sql_statement = getSql_statement(TableName);
        int TimeLimitforSingleFile = 100;
        int TestCaseNumLimitforEachFunction = 100;
        int n = 50;
        tcnum = 0;
        try {
            for(int i=1; i<=n; i++){
                System.out.println("*********开始第" + i + "次测试生成***********");
                GenerateSingleTestfile(ProjectPath,TimeLimitforSingleFile,TestCaseNumLimitforEachFunction,sql_statement);
                Thread.sleep(2000);
            }
            System.out.println("测试结束，共生成了" + n + "个测试文件。");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void GenerateSingleTestfile(String ProjectPath, int TimeLimit, int Numlimit, String sql_statement){
        try {
            //确保Node进程关闭
            KillNodeProcess();
            //生成测试用例
            TestCaseGeneration.run(ProjectPath , TimeLimit, Numlimit);
            //运行solidity-coverage
            CMDRedirect.RunTestrpc(ProjectPath);
            CMDRedirect.RunSolcov(ProjectPath);
            //TCNUM
            Main.tcnum =  TestCaseGeneration.getNum_ti();
            //获得gas及coverage,监听文件的删除来判断solidity-coverage是否运行完毕
            FileNotify.notify(ProjectPath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static String getSql_statement(String TableName){
        StringBuffer sb = new StringBuffer();
        sb.append("insert into `");
        sb.append(TableName);
        sb.append("`(data, tcnum, gas, uncov, time) values (?,?,?,?,?)");
        return sb.toString();
    }


}

