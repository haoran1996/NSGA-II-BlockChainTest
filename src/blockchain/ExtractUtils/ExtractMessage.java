package blockchain.ExtractUtils;

import java.util.ArrayList;
import java.util.List;

public class ExtractMessage {
 
//	public static void main(String[] args) {
//
//		String msg = "PerformanceManager[��1��������]Product[��2��������[�������а���������]]<[��3��������]79~";
//		List<String> list = extractMessage(msg);
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(i+"-->"+list.get(i));
//		}
//	}
	
	/**
	 *
	 * @param msg
	 * @return
	 */
	public static List<String> extractMessage(String msg) {
 
		List<String> list = new ArrayList<String>();
		int start = 0;
		int startFlag = 0;
		int endFlag = 0;
		for (int i = 0; i < msg.length(); i++) {
			if (msg.charAt(i) == '[') {
				startFlag++;
				if (startFlag == endFlag + 1) {
					start = i;
				}
			} else if (msg.charAt(i) == ']') {
				endFlag++;
				if (endFlag == startFlag) {
					list.add(msg.substring(start + 1, i));
				}
			}
		}
		return list;
	}
 
}