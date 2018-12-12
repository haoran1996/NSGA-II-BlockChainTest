package blockchain.Jnotify;

import blockchain.CMDRedirect.CMDRedirect;
import blockchain.CMDRedirect.CMDUtils;
import blockchain.CMDRedirect.Main;
import blockchain.ExtractUtils.GetTotalCoverageInfo;
import blockchain.ExtractUtils.GetTotalGasUsed;
import blockchain.Generation.TestCaseGeneration;
import blockchain.JDBC.MysqlUtil;
import net.contentobjects.jnotify.JNotify;
import net.contentobjects.jnotify.JNotifyException;
import net.contentobjects.jnotify.JNotifyListener;

import java.sql.SQLException;

import static blockchain.CMDRedirect.Main.*;

public class Listener implements JNotifyListener{
    public void fileRenamed(int wd, String rootPath, String oldName, String newName) {
        System.out.println(wd);
        System.out.println(rootPath);
        print("renamed " + rootPath + " : " + oldName + " -> " + newName);
    }

    public void fileModified(int wd, String rootPath, String name) {
        print("modified " + rootPath + " : " + name);
    }

    public void fileDeleted(int wd, String rootPath, String name) {
        print("deleted " + rootPath + " : " + name);
        if(name.equals("scTopics")){
            try {
                Thread.sleep(2000);
                System.out.println("**********************");
                gain_result();
                FileNotify.Completed = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally{
                System.out.println("结束node.exe进程");
                CMDUtils.KillNodeProcess();
            }
        }
    }

    public void fileCreated(int wd, String rootPath, String name) {
        print("created " + rootPath + " : " + name);
    }

    void print(String msg) {
        System.err.println(msg);
    }

    public static void gain_result(){
        try {
            //GAS
            Main.gas = GetTotalGasUsed.GetTotalGas(ProjectPath);
            //COV
            double cov = GetTotalCoverageInfo.GetTotalCoverage(ProjectPath);
            uncov = 100 - cov;//存的是未覆盖率
            //TIME
            Main.endtime = System.currentTimeMillis();
            Main.time = Main.endtime - Main.begintime;
            String data = databf.toString();
            System.out.println("正在解析实验数据。。。");
            System.out.println("tcnum = ：" + tcnum);
            System.out.println("Gas = "+ gas);
            System.out.println("Cov = "+ cov);
            System.out.println("Time = "+ time);
            Thread.sleep(2000);
            System.out.println("正在将本次测试结果写入数据库。。。");
            MysqlUtil.InsertTestFile(data, tcnum, gas, uncov,time,sql_statement);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
