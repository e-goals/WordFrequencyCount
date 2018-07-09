import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.io.LineNumberReader;
import java.io.Reader;

public class FileProccessing {
	public FileProccessing(String filename) {
		try {
			new FileProccessing(filename, 10240);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public FileProccessing(String filename, int ch) throws Exception {

		int numberOfChars = NumberOfChars(filename);
		int numberOfLines = NumberOfLines(filename);

		Map<String, Integer> hm = new HashMap<String, Integer>();
		FileReader fk = new FileReader(filename);
		BufferedReader br = new BufferedReader(fk);

		int i = 0;
		char[] c = new char[ch];
		String thelast = "";
		String wordpart = "";

		FileOutputStream testfile = new FileOutputStream("Result.txt");
		testfile.write(new String("").getBytes());
		testfile.close();

		while ((i = br.read(c)) > 0) {
			// int n = 0;
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
				if (hm.get(word) != null) {
					int value = ((Integer) hm.get(word)).intValue();
					value++;
					hm.put(word, new Integer(value));
				} else {
					hm.put(word, new Integer(1));
				}
			}
			thelast = wordpart;

		}
		if (!wordpart.isEmpty()) {
			if (hm.get(wordpart) != null) {
				int value = ((Integer) hm.get(wordpart)).intValue();
				value++;
				hm.put(wordpart, new Integer(value));
			} else {
				hm.put(wordpart, new Integer(1));
			}
		}

		MapComparator bvc = new MapComparator(hm);
		List<Map.Entry<String, Integer>> ll = new ArrayList<Map.Entry<String, Integer>>(hm.entrySet());
		Collections.sort(ll, bvc);

		int NumofWord = 0;
		Iterator<Entry<String, Integer>> iter = hm.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) iter.next();
			NumofWord += (Integer) entry.getValue();
		}
		String reg = ".*\\\\(.*)";
		String name = filename.replaceAll(reg, "$1");
		if (hm.size() > 500) {

			FileWriter result = new FileWriter("Result.txt", true);

			result.write("~~~~~~~~~~~~~~~~~~~~\r\n");
			result.write(name.substring(0, name.lastIndexOf(".")) + "\r\n");
			result.write("totals of the words:" + NumofWord + "\r\n");
			result.write("quantity of vocabulary:" + hm.size() + "\r\n");
			for (Map.Entry<String, Integer> str : ll) {
				result.write(str.getKey() + "——" + str.getValue() + "\r\n");
			}

			result.write("~~~~~~~~~~~~~~~~~~~~\r\n");

			System.out.println("由于" + name.substring(0, name.lastIndexOf(".")) + "文件过大，输出到文件Result中。");
			result.close();
		} else {
			System.out.println("~~~~~~~~~~~~~~~~~~~~");
			System.out.println("chars: " + numberOfChars);
			System.out.println("lines: " + numberOfLines);
			System.out.println("totals of the words:" + NumofWord);
			System.out.println("quantity of vocabulary:" + hm.size());
			for (Map.Entry<String, Integer> str : ll) {
				System.out.println(str.getKey() + "——" + str.getValue());
			}

			System.out.println("~~~~~~~~~~~~~~~~~~~~");
		}
		fk.close();
		br.close();
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
