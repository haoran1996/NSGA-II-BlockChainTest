package blockchain.Generation;


import blockchain.CMDRedirect.Main;
import blockchain.ExtractABI.*;

import javax.print.DocFlavor;
import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static blockchain.Generation.WriteUtil.GetWritePath;
import static blockchain.Generation.WriteUtil.WriteToFile;

public class TestCaseGeneration {
    private static int num_ti = 0;//所有测试文件的测试用例总数
    public static int getNum_ti() {
        return num_ti;
    }

    public static void main(String[] args){
        run("F:\\blockchain\\Test\\Primas(23) - 副本", 100, 100);
    }

    /**
     * 解析合约path，找到abi文件，生成测试文件
     * @param ContractPath
     */
    public static void run(String ContractPath, long TimeLimit, int NumLimit){
        Main.databf.setLength(0);
        num_ti = 0;
        StringBuffer sb = new StringBuffer();
        sb.append(ContractPath);
        sb.append("\\build\\contracts");
        String rootpath = sb.toString();
        sb = null;
        try {
            File rootDirectory = new File(rootpath);
            System.out.println("正在解析文件夹。。。");
            if(rootDirectory.exists()){
                File[] fileArray = rootDirectory.listFiles();
                for(int i=0; i<fileArray.length; i++){
                    ExtractContract EC = new ExtractContract();
                    File filei = fileArray[i];
                    if(CanGenerateTestCase(filei)){
                        String JsonPath = filei.getPath();//"F:\\blockchain\\SmartContrat\\metacoin\\build\\contracts\\**.json"
                        System.out.println("正在解析 " + JsonPath + "。。。");
                        List<Function> functionlist = EC.GetFunctionList(JsonPath);
                        JsonFile jf = new JsonFile(filei, functionlist);
                        String ContractName = EC.getContractName();
                        long BeginTime = System.currentTimeMillis();
                        String TotalStr = RandomGeneration(jf, ContractName, BeginTime, TimeLimit, NumLimit);
                        if(TotalStr != null){
                            String WritePath = GetWritePath(filei);
                            System.out.println("正在写文件。。。");
                            WriteToFile(WritePath, TotalStr);
                            num_ti = num_ti + jf.getTotalTestCaseNum();
                        }
                        EC = null;
                        jf = null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成主体
     * @param jf
     * @param ContractName
     * @param BeginTime
     * @param TimeLimit
     * @return
     */
    public static String RandomGeneration(JsonFile jf, String ContractName, long BeginTime, long TimeLimit, int NumLimit){
        System.out.println("正在对" + ContractName + " 生成测试用例。。。");
        try {
            String TotalStr = null;
            long CurrentTime = 0;
            long TimeCost = 0;
            int funNum = jf.getFunNum();
            if(funNum == 0){
                System.out.println("该合约没有可测function");
                return null;
            }
            if(funNum != 0){
                List<Function> functionList = jf.getFunctionList();
                //创建tf，用于进化优化
                TestFile tf = new TestFile(ContractName);
                StringBuffer br = new StringBuffer();
                GenerateHead(br, tf, ContractName);
                //随机选择方法生成测试用例
                while(TimeCost <= TimeLimit){
                    CurrentTime = System.currentTimeMillis();
                    TimeCost = CurrentTime - BeginTime;
                    int i = RandomTypeGeneration.GenerateRandomNumber(0,funNum-1);
                    Function fun = functionList.get(i);
                    List<Inputs> inputs = fun.getInputs();
                    if(CanGenerateParameter(inputs)){
                        List<String> TypeList = GetTypeList(fun);
                        String FunctionName = fun.getName();
                        int TCNum = jf.getTCNumforEachFun(i);
                        //设定每个方法生成测试用例个数的上限
                        if(TCNum < NumLimit){
                            TCNum++;
                            GenerateTestCase(br, tf, ContractName, FunctionName, TypeList, TCNum);
                            System.out.println(FunctionName + ": tcNum = " + TCNum);
                            jf.setTCNumforEachFun(i, TCNum);
                            Thread.sleep(1);//每次生成一个测试输入暂停0.01s，避免生成数量过大
                        }
                    }
                    else jf.setTCNumforEachFun(i, 1);
                }
                //确保每个可以生成测试的方法至少生成了一个测试
                for(int i=0; i<funNum; i++) {
                    Function fun = functionList.get(i);
                    List<Inputs> inputs = fun.getInputs();
                    int TCNum = jf.getTCNumforEachFun(i);
                    if (TCNum == 0){
                        jf.setTCNumforEachFun(i, 1);
                        if (CanGenerateParameter(inputs)) {
                            List<String> TypeList = GetTypeList(fun);
                            String FunctionName = fun.getName();
                            GenerateTestCase(br, tf, ContractName, FunctionName, TypeList, 1);
                            jf.setTCNumforEachFun(i, 1);
                            System.out.println(FunctionName + ": tcNum = 1");
                        }
                    }
                }
                GenerateEnd(br,tf);
                TotalStr = br.toString();
                br = null;
                System.out.println(ContractName + "的测试生成成功！");
                return TotalStr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//       //为每个方法随机生成1-10个测试用例
//       for(int i=0; i<functionList.size(); i++){
//        Function fun = functionList.get(i);
//        List<Inputs> inputs = fun.getInputs();
//        if(CanGenerateParameter(inputs)){
//            int TestCaseNum = RandomTypeGeneration.GenerateRandomNumber(1,10);
//            for(int j=1; j<=TestCaseNum; j++){
//                List<String> TypeList = GetTypeList(fun);
//                String FunctionName = fun.getName();
//                GenerateTestCase(br, ContractName, FunctionName, TypeList, j);
//            }
//            br.append("\n");
//        }
//


    //***********Generation Part*************
    /**
     * 生成头部
     * @param tf
     * @param ContractName
     * @return
     */
    public static void GenerateHead(StringBuffer br,TestFile tf,String ContractName){
        br.append("var ");
        br.append(ContractName);
        br.append(" = artifacts.require(\"");
        br.append(ContractName);
        br.append("\");\n");
        br.append("contract(\'");
        br.append(ContractName);
        br.append("\',function(accounts) {\n\n");
        tf.setTestHead(br.toString());
    }


    /**
     * 生成主体
     * @param br
     * @param ContractName
     * @param FunctionName
     * @param type
     * @param TCNum
     * @return
     */
    public static void GenerateTestCase(StringBuffer br,TestFile tf,String ContractName, String FunctionName, List<String> type, int TCNum){
        StringBuffer sb = new StringBuffer();
        sb.append("  it(\"The ");
        sb.append(TCNum);
        sb.append("th Random Test for Function ");
        sb.append(FunctionName);
        sb.append("\", function() {\n");
        sb.append("    return ");
        sb.append(ContractName);
        sb.append(".deployed().then(function(instance) {\n");
        sb.append("      return instance.");
        sb.append(FunctionName);
        sb.append("(");
        if(type.size() != 0) {
            for (int i = 0; i < type.size() - 1; i++) {
                String RandomInput = RandomTypeGeneration.GenerateParameterByType(type.get(i));
                sb.append(RandomInput);
                sb.append(",");
            }
            String LastInput = RandomTypeGeneration.GenerateParameterByType(type.get(type.size() - 1));
            sb.append(LastInput);
        }
        sb.append(");\n");
        sb.append("    })\n");
        sb.append("  });\n");
        sb.append("\n");
        br.append(sb.toString());
        Main.databf.append(sb.toString());
        sb.setLength(0);
//        TestCase tc = new TestCase(FunctionName,TCNum,br.toString());
//        tf.addTestCase(tc);
    }

    /**
     * 生成尾部
     * @param br
     * @return
     */
    public static void GenerateEnd(StringBuffer br,TestFile tf){
        br.append("});\n");
        tf.setTestEnd(br.toString());
    }

    //***********Other Utils************

    /**
     * 获取type
     * @param fun
     * @return
     */
    public static List<String> GetTypeList(Function fun){
        List<String> TypeList = new ArrayList<String>();
        List<Inputs> InputsList = fun.getInputs();
        for(int i=0; i<InputsList.size(); i++){
            TypeList.add(InputsList.get(i).getType());
        }
        return TypeList;
    }

    /**
     * 判断是否需要生成tc
     * @param file
     * @return
     */

    public static boolean CanGenerateTestCase(File file){
        boolean bl = true;
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        if(suffix.equals("sol")||fileName.equals("Migrations.json")||fileName.equals("ConvertLib.json")
                ||fileName.equals("Token.json")||fileName.contains("ERC")||fileName.equals("SafeMath.json")){
            bl = false;
        }
        return bl;
    }

    /**
     * 判断是否可以生成参数
     * @param inputs
     * @return
     */
    public static boolean CanGenerateParameter(List<Inputs> inputs){
//        boolean bl = true;
//        for(int i=0; i<inputs.size(); i++){
//            String parType = inputs.get(i).getType();
//            if(!parType.equals("uint256")&&!parType.equals("uint256[]")
//                    &&!parType.equals("address")&&!parType.equals("address[]")
//                    &&!parType.equals("bool")&&!parType.equals("uint8")
//                    &&!parType.equals("char")&&!parType.equals("String")
//                    &&!parType.equals("byte")&&!parType.equals("byte[]")
//                    &&!parType.equals("bytes32")){
//                bl = false;
//            }
//        }
//        return bl;
        return true;
    }


}
