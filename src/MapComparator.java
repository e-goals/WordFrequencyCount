import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public class MapComparator implements Comparator<Entry<String, Integer>> {

	Map<String, Integer> hashmap;

	public MapComparator(Map<String, Integer> map) {
		this.hashmap = map;
	}

	@Override
	public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {

		if (o1.getValue().compareTo(o2.getValue()) == -1) {
			return 1;
		} else if (o1.getValue().compareTo(o2.getValue()) == 0) {
			return o1.getKey().compareTo(o2.getKey());
		} else {
			return -1;
		}
	}
}