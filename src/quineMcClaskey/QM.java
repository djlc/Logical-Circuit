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

	// コンストラクタ
	public QM(ArrayList<BitArray> data) {
		this.data = data;
		sortBySize();
		for (int i = 1; i <= maxSize; i++) {
			if (groupData.containsKey(i)) compare(i);
		}
	}

	// 重み別にグループ分けする
	private void sortBySize() {
		for (BitArray b : data) {
			// グループが存在しなければ作成
			if (!groupData.containsKey(b.getWeight())) {
				groupData.put(b.getWeight(), new ArrayList<BitArray>());
			}
			groupData.get(b.getWeight()).add(b);

			if (b.getWeight() > maxSize) maxSize = b.getWeight();
		}
	}

	// 重みが1違うビット列の集合同士でハミング距離が1かどうかを調べる
	private void compare(int num) {

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
