package blockchain.NSGAII;

import blockchain.Generation.TestCase;
import blockchain.Generation.TestFile;

import java.util.ArrayList;
import java.util.List;

public class ExtractTestFile {
    public static void main(String[] args){
        String st1[] = SplitTotalString(testString);
        System.out.println(st1[0]);
        System.out.println(st1[1]);
        System.out.println(st1[2]);
        TestFile T = ExtractStringToTestFile(st1[2]);
    }

    static String testString = "var owned = artifacts.require(\"owned\");\n" +//contract1
            " contract('owned',function(accounts) {\n" +
            "   it(\"The 1th Random Main for Function admins\", function() {\n" +
            "     return owned.deployed().then(function(instance) {\n" +
            "       return instance.admins(accounts[7]);\n" +
            "     })\n" +
            "   });\n" +
            " \n" +
            "   it(\"The 2th Random Main for Function admins\", function() {\n" +
            "     return owned.deployed().then(function(instance) {\n" +
            "       return instance.admins(accounts[5]);\n" +
            "     })\n" +
            "   });\n" +
            " \n" +
            "   it(\"The 1th Random Main for Function transferOwnership\", function() {\n" +
            "     return owned.deployed().then(function(instance) {\n" +
            "       return instance.transferOwnership(accounts[2]);\n" +
            "     })\n" +
            "   });\n" +
            " \n" +
            "   it(\"The 1th Random Main for Function owner\", function() {\n" +
            "     return owned.deployed().then(function(instance) {\n" +
            "       return instance.owner();\n" +
            "     })\n" +
            "   });\n" +
            " \n" +
            "   it(\"The 3th Random Main for Function admins\", function() {\n" +
            "     return owned.deployed().then(function(instance) {\n" +
            "       return instance.admins(accounts[1]);\n" +
            "     })\n" +
            "   });\n" +
            " \n" +
            "   it(\"The 4th Random Main for Function admins\", function() {\n" +
            "     return owned.deployed().then(function(instance) {\n" +
            "       return instance.admins(accounts[9]);\n" +
            "     })\n" +
            "   });\n" +
            " \n" +
            "   it(\"The 5th Random Main for Function admins\", function() {\n" +
            "     return owned.deployed().then(function(instance) {\n" +
            "       return instance.admins(accounts[2]);\n" +
            "     })\n" +
            "   });\n" +
            " \n" +
            "   it(\"The 2th Random Main for Function transferOwnership\", function() {\n" +
            "     return owned.deployed().then(function(instance) {\n" +
            "       return instance.transferOwnership(accounts[0]);\n" +
            "     })\n" +
            "   });\n" +
            " \n" +
            "   it(\"The 11th Random Main for Function transferOwnership\", function() {\n" +
            "     return owned.deployed().then(function(instance) {\n" +
            "       return instance.transferOwnership(accounts[5]);\n" +
            "     })\n" +
            "   });\n" +
            " \n" +
            "   it(\"The 10th Random Main for Function admins\", function() {\n" +
            "     return owned.deployed().then(function(instance) {\n" +
            "       return instance.admins(accounts[6]);\n" +
            "     })\n" +
            "   });" +
            "\n" +
            " });\n" +
            " var tokenRecipient = artifacts.require(\"tokenRecipient\");\n" +//contract2
            " contract('tokenRecipient',function(accounts) {\n" +
            " });\n" +
            " var UranBank = artifacts.require(\"UranBank\");\n" +//contract3
            " contract('UranBank',function(accounts) {\n" +
            "   it(\"The 1th Random Main for Function transferFrom\", function() {\n" +
            "     return UranBank.deployed().then(function(instance) {\n" +
            "       return instance.transferFrom(accounts[7],accounts[4],0X1A40E67B14A44F6DE86023E242334FD7414E64296981C3);\n" +
            "     })\n" +
            "   });\n" +
            " \n" +
            "   it(\"The 1th Random Main for Function symbol\", function() {\n" +
            "     return UranBank.deployed().then(function(instance) {\n" +
            "       return instance.symbol();\n" +
            "     })\n" +
            "   });\n" +
            " \n" +
            "   it(\"The 1th Random Main for Function decimals\", function() {\n" +
            "     return UranBank.deployed().then(function(instance) {\n" +
            "       return instance.decimals();\n" +
            "     })\n" +
            "   });\n" +
            " \n" +
            "   it(\"The 1th Random Main for Function transfer\", function() {\n" +
            "     return UranBank.deployed().then(function(instance) {\n" +
            "       return instance.transfer(accounts[5],0X3D13E4AD35B531D795D2A9277297B22160770C78EFEBE36BC13A56F45F6B6);\n" +
            "     })\n" +
            "   });\n" +
            " \n" +
            " });";


    public static String[] SplitTotalString(String TotalString){
        String[] SplitString = TotalString.split("var");
        int contractNum = SplitString.length - 1;
        String[] SingleContract = new String[contractNum];
        for(int i=0; i<SplitString.length-1; i++){
            StringBuffer sb = new StringBuffer();
            sb.append("var");
            sb.append(SplitString[i+1]);
            SingleContract[i] = sb.toString();
        }
        return SingleContract;
    }

    public static TestFile ExtractStringToTestFile(String SingleContract){
        try {
            //TestName
            String[] str1 = SingleContract.split(" = ");//var owned = artifacts.require("owned");
            String[] str2 = str1[0].split(" ");//var owned
            String FileName = str2[1];
            //TestHead
            String[] str3 = SingleContract.split("function\\(accounts\\) \\{");
            StringBuffer sb1 = new StringBuffer();
            sb1.append(str3[0]);
            sb1.append("function(accounts) \\{");
            String TestHead = sb1.toString();
            //TestCase
            sb1.setLength(0);
            char[] chs = str3[1].toCharArray();
            int len = chs.length;
            sb1.append(str3[1]);
            sb1.delete(len-4, len);//去掉str3最后"});"sb1即包含所有TestCase的String
            String[] str4 = sb1.toString().split("\\}\\);");
            int TestCaseNum = str4.length - 1;
            List<TestCase> TestCases = new ArrayList<>();
            for(int i=0; i<TestCaseNum-1; i++){
                sb1.setLength(0);
                sb1.append(str4[i]);
                sb1.append("});");
                TestCase TC = new TestCase(sb1.toString());
                TestCases.add(TC);
            }
            TestFile TF = new TestFile(FileName, TestHead, TestCases);
            return TF;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
