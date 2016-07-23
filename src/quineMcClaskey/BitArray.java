package quineMcClaskey;

import java.util.ArrayList;

public class BitArray {

	// 定数
	private static final BitArray ZERO = new BitArray("0");
	private static final BitArray ONE = new BitArray("1");

	// 中身
	private ArrayList<Bit> bin;

	// "01x"の文字列からビット列を作成
	public BitArray(String data) {
		bin = new ArrayList<>(data.length());
		for (int i = 0; i < data.length(); i++) {
			switch (data.charAt(i)) {
			case '0':
				bin.add(Bit.LOW);
				break;
			case '1':
				bin.add(Bit.HIGH);
				break;
			default:
				bin.add(Bit.DONT_CARE);
				break;
			}
		}
	}

	// {00...00} (n bit) を作成
	public BitArray(int n) {
		bin = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			bin.add(Bit.LOW);
		}
	}

	// コピーコンストラクタ
	public BitArray(BitArray b) {
		this.bin = new ArrayList<>(b.bin);
	}

	// ビット列の長さ
	public int getSize() {
		return bin.size();
	}

	// iビット目の値を取得
	public Bit getBit(int i) {
		return bin.get(i);
	}

	// iビット目を削除して詰める
	public void remove(int i) {
		if (i >= 0 && i < this.bin.size()) {
			this.bin.remove(i);
		}
	}

	// iビット目の値を設定
	public void set(int i, Bit b) {
		if (i >= 0 && i < this.bin.size()) {
			this.bin.set(i, b);
		}
	}

	// ハミング重み
	public int getWeight() {
		return w(this);
	}

	private static int w(BitArray b) {
		int c = 0;
		for (int i = 0; i < b.getSize(); i++)
			if (b.bin.get(i) == Bit.HIGH)
				c++;
		return c;
	}

	// ハミング距離
	public static int d(BitArray a, BitArray b) {
		if (a.bin.size() != b.bin.size())
			return -1;
		int c = 0;
		for (int i = 0; i < a.getSize(); i++) {
			if (a.bin.get(i) != b.bin.get(i))
				c++;
		}
		return c;
	}

	// 1を加算(インクリメント)
	private void increment() {
		add(BitArray.ONE);
	}

	// 加算
	private void add(BitArray b) {
		// 桁数を揃える
		for (int i = 0; i < Math.abs(bin.size() - b.bin.size()); i++) {
			if (this.bin.size() > b.bin.size()) {
				b.bin.add(0, Bit.LOW);
			} else {
				bin.add(0, Bit.LOW);
			}
		}

		// 計算
		// L + L = L, L + H = H
		// H + L = H
		// H + H = L -> 隣のビットにHを足す(キャリーオーバー)
		// これを下のビットから行う
		Bit carryOver = Bit.LOW;
		for (int i = b.bin.size() - 1; i >= 0; i--) {
			Bit[] x = { this.bin.get(i), b.bin.get(i) };
			if (x[0] == Bit.LOW && x[1] == Bit.LOW && carryOver == Bit.HIGH) {
				x[0] = Bit.HIGH;
				carryOver = Bit.LOW;
			}
			if (x[0] == Bit.LOW && x[1] == Bit.HIGH && carryOver == Bit.LOW) {
				x[0] = Bit.HIGH;
			}
			if (x[0] == Bit.LOW && x[1] == Bit.HIGH && carryOver == Bit.HIGH) {
				x[0] = Bit.LOW;
			}
			if (x[0] == Bit.HIGH && x[1] == Bit.HIGH && carryOver == Bit.LOW) {
				x[0] = Bit.LOW;
				carryOver = Bit.HIGH;
			}
			if (x[0] == Bit.HIGH && x[1] == Bit.HIGH && carryOver == Bit.HIGH) {
				x[0] = Bit.HIGH;
			}

			if (i == 0 && carryOver == Bit.HIGH) {
				bin.add(0, carryOver);
			}
		}
	}

	// ビット列の結合
	private static BitArray combine(BitArray a, BitArray b) {
		BitArray c = new BitArray(a);
		c.bin.addAll(b.bin);
		return c;
	}

	// don't care bit に値を代入したものをリスト化する
	public static ArrayList<BitArray> normalize(ArrayList<BitArray> b) {
		// リスト
		ArrayList<BitArray> data = new ArrayList<>(b);

		// 再帰処理
		recursiveNormalize(data);

		// 重複除去
		deduplication(data);

		return data;
	}

	// 上のメソッドで使う再帰メソッド
	private static void recursiveNormalize(ArrayList<BitArray> data) {
		for (int i = 0; i < data.size(); i++) {
			for (int j = 0; j < data.get(i).bin.size(); j++) {
				if (data.get(i).bin.get(j) == Bit.DONT_CARE) {

					BitArray temp1 = new BitArray(data.get(i));
					temp1.bin.set(j, Bit.LOW);
					data.add(temp1);

					BitArray temp2 = new BitArray(data.get(i));
					temp2.bin.set(j, Bit.HIGH);
					data.add(temp2);

					data.remove(i);
					recursiveNormalize(data);
				}
			}
		}
	}

	// ビット列のリストの重複除去
	public static void deduplication(ArrayList<BitArray> data) {
		ArrayList<BitArray> unique = new ArrayList<>();
		boolean f = false;
		for (BitArray i : data) {
			f = false;
			for (BitArray j : unique) {
				if (i.equals(j)) {
					f = true;
					break;
				}
			}
			if (!f)
				unique.add(i);
		}
		data.clear();
		data.addAll(unique);
	}

	@Override
	public String toString() {
		String str = "";
		for (int i = 0; i < bin.size(); i++) {
			switch (bin.get(i)) {
			case LOW:
				str += "0";
				break;
			case HIGH:
				str += "1";
				break;
			case DONT_CARE:
				str += "x";
				break;
			default:
				break;
			}
		}
		return str;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BitArray) {
			BitArray temp = (BitArray) obj;
			if (temp.bin.size() != this.bin.size())
				return false;
			for (int i = 0; i < temp.bin.size(); i++) {
				if (temp.bin.get(i) != this.bin.get(i))
					return false;
			}
			return true;
		} else {
			return false;
		}
	}

	// don't careを許した場合のequals
	public boolean semiEquals(BitArray b) {
		if (b.bin.size() != this.bin.size())
			return false;
		for (int i = 0; i < b.bin.size(); i++) {
			if ((b.bin.get(i) == Bit.LOW && this.bin.get(i) == Bit.HIGH)
					|| (b.bin.get(i) == Bit.HIGH && this.bin.get(i) == Bit.LOW))
				return false;
		}
		return true;
	}
}
