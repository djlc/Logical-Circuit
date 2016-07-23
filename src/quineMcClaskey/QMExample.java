package quineMcClaskey;

import java.util.ArrayList;

public class QMExample {
	public static void main(String[] args) {
		// ~ABC + AB~C + ABD + B~CD + ~AC~D + AC~D
		ArrayList<BitArray> data = new ArrayList<>();
		data.add(new BitArray("1x01x"));
		data.add(new BitArray("1010x"));
		data.add(new BitArray("0x011"));
		data.add(new BitArray("00101"));
		QM qm = new QM(data);
		System.out.println(data.toString() + " = " + qm.toString());
	}
}
