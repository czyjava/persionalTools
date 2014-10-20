package com.czy.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConfigUtil {
	public static Map<String, String> gzMap = new LinkedHashMap<String, String>();

	static {
		getRegs();
	}

	public static List<String> getRegs() {
		List<String> regs = new ArrayList<String>();

		try {
			Scanner scanner = new Scanner(new FileInputStream("F://test.txt"));

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				if (!line.trim().equals("")) {
					String[] arr = line.split("===");
					gzMap.put(arr[0].trim(), arr[1].trim());
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return regs;
	}

	public static String getGzByPattern(String reg) {
		return gzMap.get(reg);
	}
}
