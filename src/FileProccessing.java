import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class FileProccessing {
	public FileProccessing(String filename) {
		try {
			int numberOfChars = NumberOfChars(filename);
			int numberOfLines = NumberOfLines(filename);

			Map<String, Integer> map = WordMap(filename, 10240);

			int numberOfWords = 0;
			Iterator<Entry<String, Integer>> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iter.next();
				numberOfWords += (Integer) entry.getValue();
			}

			MapComparator comparator = new MapComparator(map);
			List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
			Collections.sort(list, comparator);

			System.out.println("~~~~~~~~~~~~~~~~~~~~");
			System.out.println("chars: " + numberOfChars);
			System.out.println("lines: " + numberOfLines);
			System.out.println("words: " + numberOfWords);
			int freq0 = 0, freq1 = 0;
			for (int i = 0; i < list.size(); i++) {
				freq1 = list.get(i).getValue();
				if (freq0 != freq1) {
					System.out.println(list.get(i).getKey() + ": " + list.get(i).getValue());
					freq0 = freq1;
				}
			}
			System.out.println("~~~~~~~~~~~~~~~~~~~~");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<String, Integer> WordMap(String filename, int ch) throws IOException {
		Map<String, Integer> map = new HashMap<String, Integer>();
		FileReader fileReader = new FileReader(filename);
		BufferedReader reader = new BufferedReader(fileReader);
		int i = 0;
		char[] c = new char[ch];
		String thelast = "";
		String wordpart = "";

		FileOutputStream testfile = new FileOutputStream("Result.txt");
		testfile.write(new String("").getBytes());
		testfile.close();

		while ((i = reader.read(c)) > 0) {
			wordpart = "";
			for (int reset = i; reset < ch; reset++) {
				c[reset - 1] = ' ';
			}

			int m = i - 1;
			while (Character.isLetter(c[m])) {
				wordpart = String.valueOf(c[m]) + wordpart;
				c[m] = ' ';
				m--;
				if (m < 0) {
					thelast += wordpart;
					break;
				}
			}
			if (m < 0) {
				continue;
			}
			String s = (thelast + String.valueOf(c)).toLowerCase();// 拼接加大写转换小写
			StringTokenizer st = new StringTokenizer(s, " ,.!?\"\';:0123456789\n\r\t“”‘’·——-=*/()[]{}…（）【】｛｝\0﻿"); // 用于切分字符串

			while (st.hasMoreTokens()) {
				String word = st.nextToken();
				if (word.length() < 4)
					continue;
				if (map.get(word) != null) {
					int value = ((Integer) map.get(word)).intValue();
					value++;
					map.put(word, new Integer(value));
				} else {
					map.put(word, new Integer(1));
				}
			}
			thelast = wordpart;
		}
		fileReader.close();
		reader.close();
		if (!wordpart.isEmpty()) {
			if (map.get(wordpart) != null) {
				int value = ((Integer) map.get(wordpart)).intValue();
				value++;
				map.put(wordpart, new Integer(value));
			} else {
				map.put(wordpart, new Integer(1));
			}
		}
		return map;
	}

	private int NumberOfLines(String filename) throws IOException {
		int lines = 0;
		File file = new File(filename);

		long fileLength = file.length();
		LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(file));
		lineNumberReader.skip(fileLength);
		lines = lineNumberReader.getLineNumber();
		lineNumberReader.close();

		return lines;
	}

	private int NumberOfChars(String filename) throws IOException {
		int chars = 0;
		File file = new File(filename);
		Reader readfile = new InputStreamReader(new FileInputStream(file));
		int tempchar;
		while ((tempchar = readfile.read()) != -1) {
			if (tempchar < 128) {
				chars++;
			}
		}
		readfile.close();
		return chars;
	}

}
