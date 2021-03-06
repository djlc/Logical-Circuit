package quineMcClaskey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QM {

	// 最小項
	private List<BitArray> min;

	// 主項
	private List<BitArray> prime = new ArrayList<>();

	// 重み別で分けた論理式 (<重み, 論理式>のHashで管理)
	private Map<Integer, List<BitArray>> groupData = new HashMap<>();
	private Map<Integer, List<BitArray>> newGroupData = new HashMap<>();


	// ビット列の最大長
	private int maxSize;

	// コンストラクタ
	public QM(List<BitArray> data) {
		// 論理式を加法標準形にする
		this.min = BitArray.normalize(data);
		sortBySize();

		// 簡単化
		for (int c = 1; c <= data.get(0).getSize(); c++) {

			// 比較操作
			for (int i = 1; i <= maxSize; i++) {
				if (groupData.containsKey(i) && groupData.containsKey(i - 1)) {
					compare(i - 1);
				}
			}

			// 簡単化出来なかったものは主項
			for (Map.Entry<Integer, List<BitArray>> e : groupData.entrySet()) {
				for (BitArray b : e.getValue()) {
					if (b.simplified == false) {
						prime.add(b);
					}
				}
			}

			// 重複除去
			for (Map.Entry<Integer, List<BitArray>> e : newGroupData.entrySet()) {
				BitArray.deduplication(e.getValue());
			}

			// もしリストが空ならば終了
			if (newGroupData.isEmpty()) {
				break;
			}

			// 新しいデータを移して古いデータは消去
			groupData.clear();
			groupData.putAll(newGroupData);
			newGroupData.clear();
		}


		// 最小項と主項の表を作って重複項の除去
		int[][] table = new int[min.size()][prime.size()];
		List<Integer> removeList = new ArrayList<>();

		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				table[i][j] = (min.get(i).semiEquals(prime.get(j))) ? 1 : 0;
			}
		}
		for (int j = 0; j < table[0].length; j++) {
			int sum = 0;
			for (int i = 0; i < table.length; i++) {
				sum = 0;
				if (table[i][j] == 1) {
					for (int k = 0; k < table[i].length; k++) {
						sum += table[i][k];
					}
					if (sum == 1) break;
				}
			}
			if (sum >= 2) removeList.add(j);
		}
		for (int i : removeList) {
			prime.set(i, null);
		}
		prime.removeAll(Collections.singleton(null));
	}

	// 重み別にグループ分けする
	private void sortBySize() {
		for (BitArray b : min) {
			// グループが存在しなければ作成
			if (!groupData.containsKey(b.getWeight())) {
				groupData.put(b.getWeight(), new ArrayList<BitArray>());
			}
			groupData.get(b.getWeight()).add(b);

			if (b.getWeight() > maxSize)
				maxSize = b.getWeight();
		}
	}

	// 重みが1違うビット列の集合同士でハミング距離が1になる組があるかどうかを調べる
	private void compare(int num) {
		for (BitArray i : groupData.get(num)) {
			for (BitArray j : groupData.get(num + 1)) {
				if (BitArray.d(i, j) == 1) {
					i.simplified = true;
					j.simplified = true;
					for (int k = 0; k < i.getSize(); k++) {
						if (i.getBit(k) != j.getBit(k)) {
							BitArray t = new BitArray(i);
							t.set(k, Bit.DONT_CARE);
							if (!newGroupData.containsKey(t.getWeight())) {
								newGroupData.put(t.getWeight(), new ArrayList<BitArray>());
							}
							newGroupData.get(t.getWeight()).add(t);
							break;
						}
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		return prime.toString();
	}
}
