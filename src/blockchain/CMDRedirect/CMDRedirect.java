package blockchain.CMDRedirect;



import blockchain.Generation.WriteUtil;
import blockchain.Jnotify.FileNotify;

import java.io.IOException;

import static blockchain.CMDRedirect.CMDUtils.KillNodeProcess;

public class CMDRedirect {

    private static String FilePath; //F:\\blockchain\\metacoin

    /**
     * testrpc-sc --gasLimit 0xfffffffffff --port 8555
     */
    public static void RunTestrpc(String FilePath){
        StringBuffer sc = new StringBuffer();
        sc.append(Main.ProjectPath);
        sc.append("\\scTopics");
        WriteUtil.deleteFile(sc.toString());
        Runtime rt = Runtime.getRuntime();
        StringBuffer sb = new StringBuffer();
        StringBuffer fp = new StringBuffer();
        fp.append(FilePath);
        fp.append("\\GasInfo.txt");
        String filepath = fp.toString();
        fp = null;
//        CMDUtils.clearInfoForFile(filepath);
        WriteUtil.deleteFile(filepath);
        sb.append("cmd /c pushd ");
        sb.append(FilePath);
        sb.append(" && testrpc-sc --gasLimit 0xfffffffffff --port 8555 > ");
        sb.append(filepath);
        String cmd = sb.toString();
        sb = null;
        System.out.println("启动本地测试链。。。");
        try {
            Process pr1 = rt.exec(cmd);
            System.out.println("Testrpc is running.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ����solidity-coverage
     */
    public static void RunSolcov(String FilePath){
        Runtime rt = Runtime.getRuntime();
        StringBuffer sb = new StringBuffer();
        sb.append("cmd /c pushd ");
        sb.append(FilePath);
        sb.append(" && solidity-coverage > ");
        sb.append(FilePath);
        sb.append("\\CovInfo.txt");
        String cmd = sb.toString();
        System.out.println("启动solidity-coverage。。。");
        try {
            Process pr2 = rt.exec(cmd);
            Main.begintime = System.currentTimeMillis();
            System.out.println("Solcov is running.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        Main.ProjectPath = "F:\\blockchain\\Test20181124\\CryptoTomatoes";
        try {
            //确保Node进程关闭
            KillNodeProcess();
            //运行solidity-coverage
            CMDRedirect.RunTestrpc(Main.ProjectPath);
            CMDRedirect.RunSolcov(Main.ProjectPath);
            FileNotify.notify(Main.ProjectPath);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
