package quineMcClaskey;

import java.util.ArrayList;

public class QMExample {
	public static void main(String[] args) {
		// ~ABC + AB~C + ABD + B~CD + ~AC~D + AC~D
		ArrayList<BitArray> data = new ArrayList<>();
		data.add(new BitArray("011x"));
		data.add(new BitArray("110x"));
		data.add(new BitArray("11x1"));
		data.add(new BitArray("x101"));
		data.add(new BitArray("0x10"));
		data.add(new BitArray("1x10"));
		for (BitArray i : BitArray.normalize(data)) System.out.println(i.toString());
	}
}
