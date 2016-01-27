package com.koldbyte.hackerrank.plugin;

import java.io.File;

public class MainApp {

	public static void main(String[] args) {

		// Expect args[1]== Full file url of code
		if (args != null) {
			String file = args[0];

			// System.out.println("You provided file : " + file);

			File f = new File(file);
			// Make sure the file is valid
			if (f != null && f.isFile()) {
				new PluginWorker().FetchResults(f);
			} else {
				System.out.println("Error: Cannot read file");
			}
		} else {
			System.out.println("No Input File Provided.");
		}
	}

}
