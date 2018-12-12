package blockchain.Jnotify;
import java.io.File;
import net.contentobjects.jnotify.JNotify;

public class FileNotify {
    public static boolean Completed;
    public static void notify(String filepath) throws Exception {
        Completed = false;
        if (!new File(filepath).exists()) {
            System.out.println("文件目录不存在");
            return;
        }

        // 定义你所需要检测的事件类型，或者是全部FILE_ANY
        int mask = JNotify.FILE_CREATED | JNotify.FILE_DELETED | JNotify.FILE_MODIFIED | JNotify.FILE_RENAMED;
        int mask1 =JNotify.FILE_ANY;
//        System.out.println(JNotify.FILE_CREATED);//1
//        System.out.println(JNotify.FILE_DELETED);//2
//        System.out.println( JNotify.FILE_MODIFIED);//4
//        System.out.println( JNotify.FILE_RENAMED);//8
//        System.out.println(mask);//15
//        System.out.println(mask1);//15

        // 是否检测子目录
        boolean watchSubtree = false;

        // 添加监听
        int watchID = JNotify.addWatch(filepath, 2, watchSubtree, new Listener());
        System.out.println("开始监听scTopics文件");
        long BeginTime = System.currentTimeMillis();
        long CurrentTime = 0;
        // 定义监听持续时间，100s
        while(!Completed) {
            Thread.sleep(1000*2);
            CurrentTime = System.currentTimeMillis();
            if(CurrentTime - BeginTime > 1000*120){
                System.out.println("solcov超时，退出");
                break;
            }
        }

        // 定义监听时间，如果超过这个时间，程序会退出；如果不定义就得不到监听
//        Thread.sleep(1000*0);//60秒

        //移除监听
        boolean res = JNotify.removeWatch(watchID);
        if (!res) {
            System.out.println("已退出监听。");
        }


    }

    public static void main(String[] args) {
        String path ="F:\\blockchain\\test1";
        System.out.println(path);
        try {
            notify(path);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
