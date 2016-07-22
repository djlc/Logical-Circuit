package quineMcClaskey;

import java.util.ArrayList;
import java.util.HashMap;

public class QM {
	
	// 論理式
	private ArrayList<BitArray> data;
	
	// 長さ別で分けた論理式 (<長さ, 論理式>のHashで管理)
	private HashMap<Integer, ArrayList<BitArray>> groupData = new HashMap<>();
	
	// ビット列の最大長
	private int maxSize;
	
	public QM(ArrayList<BitArray> data) {
		this.data = data;
	}
	
	// ビット列の長さ別にグループ分けする
	private void sortBySize() {
		for (BitArray i : data) {
			// グループが存在しなければ作成
			if (!groupData.containsKey(i.getSize())) {
				groupData.put(i.getSize(), new ArrayList<BitArray>());
			}
			groupData.get(i.getSize()).add(i);
			
			if (i.getSize() > maxSize) maxSize = i.getSize();
		}
	}
	
	// 長さが1違うビット列のグループ同士でハミング距離が1かどうかを調べる
	private void compare() {
		
	}
	
	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < data.size(); i++) {
			str += data.get(i).toString();
			str += (i != data.size() - 1) ? " + " : "";
		}
		return str;
	}
}
