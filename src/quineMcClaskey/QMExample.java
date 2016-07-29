package quineMcClaskey;

import java.util.ArrayList;
import java.util.List;

public class QMExample {
	public static void main(String[] args) {
		List<BitArray> data = new ArrayList<>();
		data.add(new BitArray("00x0"));
		data.add(new BitArray("1x10"));
		data.add(new BitArray("011x"));
		data.add(new BitArray("11x1"));
		data.add(new BitArray("1000"));
		data.add(new BitArray("0101"));
		QM qm = new QM(data);
		System.out.println(data.toString() + " = " + qm.toString());
	}
}
