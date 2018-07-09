import java.util.*;
import java.io.*;

public class WordFrequencyCount {
	public static void main(String[] args) throws Exception {
		long start = System.currentTimeMillis();

		if (args.length == 0) {
			System.out.println("请输入文件名：");
			Scanner in = new Scanner(System.in);
			String str = in.nextLine();
			if (str.startsWith("<")) {
				new FileProccessing(str.substring(str.indexOf(" ") + 1));
			} else if (!str.isEmpty()) {

				FileWriter out = new FileWriter(str + ".txt");

				System.out.println("请输入内容，结尾以回车后ctrl+z结束：");
				while (in.hasNext()) {
					out.write(in.nextLine() + "\r\n");
				}
				out.close();

				new FileProccessing(str + ".txt");
			}
			in.close();
		}
		for (int i = 0; i < args.length; i++) {
			String FileName = args[i];
			File fs = new File(FileName);
			if (fs.isDirectory()) {
				File[] filelist = fs.listFiles();
				for (int n = 0; n < filelist.length; n++) {
					new FileProccessing(filelist[n].getAbsolutePath());
				}

			} else {
				new FileProccessing(FileName);
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("time:" + (end - start) + "ms");

	}
}