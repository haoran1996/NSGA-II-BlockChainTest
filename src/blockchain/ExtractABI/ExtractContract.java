package blockchain.ExtractABI;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ExtractContract {
    private String ContractName;
    public String getContractName() {
        return ContractName;
    }

    /*
        解析abi获取方法序列
     */
    public  List<Function> GetFunctionList(String JPath){
        List<Function> listfunction = new ArrayList<Function>();
//        String path = "F:\\blockchain\\SmartContrat\\TestAdd\\build\\contracts\\Add.json";
        String Jsonstr = RDJSON.ReadFile(JPath);
        JSONObject jobj = JSON.parseObject(Jsonstr);
        ContractName = jobj.getString("contractName");
        JSONArray arr = jobj.getJSONArray("abi");
        for (int i = 0; i < arr.size(); i++) {
            JSONObject object = arr.getJSONObject(i);
            String temp = object.getString("type");
            if (temp.equals("function")) {
//                System.out.println(object);
                Function func = JSONObject.parseObject(object.toJSONString(), Function.class);
                listfunction.add(func);
            }
        }
        return listfunction;
//        for(int j=0; j<listfunction.size(); j++) {
//            System.out.println("********第"+"j"+"个function********");
//            Function functionj = listfunction.get(j);
//            System.out.println("function_name:"+functionj.getName());
//            List<Inputs> inputs = functionj.getInputs();
//            for(int i=0; i<inputs.size(); i++){
//                System.out.println("inputs:");
//                System.out.println("name = "+inputs.get(i).getName());
//                System.out.println("type = "+inputs.get(i).getType());
//            }
//
//        }
    }


}

//		for(Function function_ : listfunction){
//			System.out.println(listfunction);
//		}
//		for(int i=0 ;i<arr.size();i++){
//			JSONObject jsonObject = (JSONObject) arr.get(i);
//			if(jsonObject.getString("type").equals("function")){
//
//				listfunction = JSON.parseArray(arr.toString(),Function.class);
//				String name = jsonObject.getString("name");
//				String inputs = jsonObject.getString("inputs");
//				JSONArray inputsarr = JSON.parseArray(inputs);
//				String outputs = jsonObject.getString("outputs");
//			}
//		}
//		List<ABI> listabi = JSON.parseArray(arr.toString(), ABI.class);
//		 for(ABI abi_ : listabi){
//		    System.out.println(abi_.);
//		 }
//		JSONObject jobj1 = arr.getJSONObject(1);
//		System.out.println(arr);
//		System.out.println(jobj1);

