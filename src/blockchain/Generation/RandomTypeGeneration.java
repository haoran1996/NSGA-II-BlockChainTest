package blockchain.Generation;



import java.util.List;
import java.util.Random;

/**
 * 根据type随机生成参数
 */
public class RandomTypeGeneration {
    public static void main(String[] args){
        System.out.println(getRandomAddressArray(2));
    }

    /**
     * 生成随机数
     * @param min
     * @param max
     * @return
     */
    public static int GenerateRandomNumber(int min, int max){
        int result = min+(int)(Math.random()*(max - min +1));
        return result;
    }

    /**
     * 判断type,目前考虑数据类型和地址类型
     * @param type
     * @return
     */
    public static String GenerateParameterByType(String type){
        try {
            if(type.equals("uint256")){
                return getRandomUint256();
            }
            else if(type.equals("uint[]")||type.equals("int[]")
                    ||type.equals("uint8[]")||type.equals("int8[]")
                    ||type.equals("uint16[]")||type.equals("int16[]")
                    ||type.equals("uint32[]")||type.equals("int32[]")
                    ||type.equals("uint64[]")||type.equals("int64[]")
                    ||type.equals("uint128[]")||type.equals("int128[]")
                    ||type.equals("uint256[]")||type.equals("int256[]")){
                return getRandomUint256Array(2);
            }
            else if(type.equals("uint16[4]")){
                return getRandomUint256Array(4);
            }
            else if(type.equals("uint16[9]")){
                return getRandomUint256Array(9);
            }
            else if(type.equals("uint256[3]")){
                return getRandomUint256Array(3);
            }
            else if(type.equals("address")){
                return getRandomAddress();
            }
            else if(type.equals("address[]")){
                return getRandomAddressArray(2);
            }
            else if(type.equals("address[3]")){
                return getRandomAddressArray(3);
            }
            else if(type.equals("bool")){
                return getRandomBoolean();
            }
            else if(type.equals("bool[]")){
                return getRandomBooleanArray(2);
            }
            else if(type.equals("uint")||type.equals("int")
                    ||type.equals("uint8")||type.equals("int8")
                    ||type.equals("uint16")||type.equals("int16")
                    ||type.equals("uint32")||type.equals("int32")
                    ||type.equals("uint64")||type.equals("int64")
                    ||type.equals("uint128")||type.equals("int128")){
                return getRandomUint();
            }
            else if(type.equals("char")){
                return getRandomchar();
            }
            else if(type.equals("string")){
                return getRandomString(GenerateRandomNumber(1,3));
            }
            else if(type.equals("byte")||type.equals("byte16")||type.equals("byte32")){
                return getRandomHexString(2);
            }
            else if(type.equals("byte[]")||type.equals("bytes")||type.equals("bytes[]")//可能需要修改
                    ||type.equals("bytes16")||type.equals("bytes32")
                    ||type.equals("bytes16[]")||type.equals("bytes32[]")){
                return getRandomByteArray(2);
            }
            else {
                return getUnknownType();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    /**
     * 16进制生成
     * @param
     * @return
     */
    public static String getRandomHexString(int len) {
        try {
            StringBuffer result = new StringBuffer();
            result.append("0x");
            for (int j = 0; j < len; j++) {
                result.append(Integer.toHexString(new Random().nextInt(16)));
            }
            return result.toString().toUpperCase();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    public static String getRandomUint256(){
//        long i = GenerateRandomNumber(0,9);
//        if(i<=3){
//            return "0";
//        }else{
            int j = GenerateRandomNumber(1,32);
            return getRandomHexString(j);
//        }
    }

    public static String getRandomUint256Array(int size){//[0x1232,0x421]
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for(int i=0; i<size-1; i++){
            sb.append(getRandomUint256());
            sb.append(",");
        }
        sb.append(getRandomUint256());
        sb.append("]");
        return sb.toString();
    }

    /**
     * 生成随机本地address
     * @return
     */
    public static String getRandomAddress(){
        StringBuffer sb = new StringBuffer();
        sb.append("accounts[");
        int i = 0;
        int p = GenerateRandomNumber(0,9);
        if(p <= 3){
            i = 0;
        }else{
            i = GenerateRandomNumber(1,9);
        }
        sb.append(i);
        sb.append("]");
        return sb.toString();
    }

    public static String getRandomAddressArray(int size){//[accounts[3],accounts[6]]
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for(int i=0; i<size-1; i++){
            sb.append(getRandomAddress());
            sb.append(",");
        }
        sb.append(getRandomAddress());
        sb.append("]");
        return sb.toString();
    }

    public static String getRandomBoolean(){
        Random rd = new Random();
        boolean value = rd.nextBoolean();
        return String.valueOf(value);
    }

    public static String getRandomBooleanArray(int size){
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for(int i=0; i<size-1; i++){
            sb.append(getRandomBoolean());
            sb.append(",");
        }
        sb.append(getRandomBoolean());
        sb.append("]");
        return sb.toString();
    }

    public static String getRandomUint(){
        int uint = (int)(Math.random()*255.999);
        return String.valueOf(uint);
    }


    public static String getRandomInt(){
        int intUnbounded = new Random().nextInt();
        return String.valueOf(intUnbounded);
    }


    public static String getRandomchar(){
        StringBuffer sb=new StringBuffer();
        char ch = (char)(Math.random()*128);
        sb.append("\'");
        sb.append(String.valueOf(ch));
        sb.append("\'");
        return sb.toString();
    }

    public static String getRandomString(long length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        sb.append("\"");
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        sb.append("\"");
        return sb.toString();
    }

//    public static String getRandomByte(){
//        try {
//            int len = 2;
//            StringBuffer result = new StringBuffer();
//            result.append("0x");
//            for (int i = 0; i < len; i++) {
//                result.append(Integer.toHexString(new Random().nextInt(16)));
//            }
//            return result.toString().toUpperCase();
//
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//
//        }
//        return null;
//    }

    public static String getRandomByteArray(int size){
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        for(int i=0; i<size-1; i++){
            sb.append(getRandomHexString(2));
            sb.append(",");
        }
        sb.append(getRandomHexString(2));
        sb.append("]");
        return sb.toString();
    }


    public static String getUnknownType(){
        return "UnknownType";
    }
}
