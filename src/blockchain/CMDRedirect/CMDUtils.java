package blockchain.CMDRedirect;

import java.io.*;
import java.io.File;



public class CMDUtils {

    /**
     * 每次运行solidity-coverage前要重新启动node.exe
     */
    public static void KillNodeProcess()
    {
        try
        {
            String[] cmd = {"tasklist"};
            Process proc = Runtime.getRuntime().exec(cmd);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String string_Temp = in.readLine();
            while (string_Temp != null)
            {
                if(string_Temp.indexOf("node.exe")!=-1)
                    Runtime.getRuntime().exec("taskkill /f /im node.exe");
                string_Temp = in.readLine();
            }
        }
        catch (Exception e)
        {
        }
    }

    /**
     * 清空文件内容
     * @param fileName
     */
    public static void clearInfoForFile(String fileName) {
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    /**
//     *  根据路径删除指定的目录或文件，无论存在与否
//     *@param sPath  要删除的目录或文件
//     *@return 删除成功返回 true，否则返回 false。
//     */
//    public static boolean DeleteFolder(String sPath) {
//        boolean flag = false;
//        File file = new File(sPath);
//        // 判断目录或文件是否存在
//        if (!file.exists()) {  // 不存在返回 false
//            return flag;
//        } else {
//            // 判断是否为文件
//            if (file.isFile()) {  // 为文件时调用删除文件方法
//                return deleteFile(file);
//            } else {  // 为目录时调用删除目录方法
//                return deleteDirectory(file);
//            }
//        }
//    }

}
