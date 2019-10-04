package com.lin.stock.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chen Lin
 */

public class FileUtil {

	public static ArrayList<String> load(String inFile) throws IOException {

		ArrayList<String> inputCommands = new ArrayList<String>();

		ClassLoader classLoader = FileUtil.class.getClassLoader();

		try (InputStream in = classLoader.getResourceAsStream(inFile)) {
			String line;
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			while ((line = reader.readLine()) != null) {
				inputCommands.add(line);
			}
			return new ArrayList<String>(inputCommands);
		}
	}

	public static void write(String outFile, List<String> output) throws IOException {

		try (Writer writer = new BufferedWriter(new FileWriter(new File(outFile)))) {
			for (int i = 0; i < output.size(); i++) {
				if (i == output.size() - 1) {
					writer.write(output.get(i));
				} else {
					writer.write(output.get(i) + "\n");
				}
			}
		}
	}
}