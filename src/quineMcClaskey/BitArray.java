package quineMcClaskey;

import java.util.ArrayList;

public class BitArray {
	
	// 中身
	private ArrayList<Byte> bin;
	
	public int getSize() {
		return bin.size();
	}
	
	// ハミング距離
	public static int d(BitArray a, BitArray b) {
		if (a.bin.size() != b.bin.size()) return -1;
		int c = 0;
		for (int i = 0; i < a.getSize(); i++) {
			if (a.bin.get(i) != b.bin.get(i)) i++;
		}
		return c;
	}
	
	@Override
	public String toString() {
		byte c = 'A';
		String str = "";
		for (int i = 0; i < bin.size(); i++) {
			str += (bin.get(i) == 0) ? ("!" + c) : c;
			c++;
		}
		return str;
	}
}
