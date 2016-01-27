package com.koldbyte.hackerrank.plugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.koldbyte.hackerrank.plugin.util.RequestWrapper;

public class PluginWorker {
	private String APIURl_prefix = "https://www.hackerrank.com/rest/contests/master/challenges/";
	private String APIURl_suffix = "/compile_tests";
	private int LIMIT = 10;

	public void FetchResults(File f) {

		// Create POST URL from file name
		String problemName = getProblemName(f.getName());
		String url = APIURl_prefix + problemName + APIURl_suffix;

		// System.out.println("Final Api url is " + url);

		// Prepare file to create payload for request
		String fileContents = readFile(f);
		CodeObject code = new CodeObject(fileContents);
		String postBody = code.getJson();

		// System.out.println(postBody);

		RequestWrapper rw = new RequestWrapper();

		try {

			// initial Request to get the CSRF token
			String rootDoc = rw.get("https://www.hackerrank.com/");

			// Get CSRF Token from meta
			Document doc = Jsoup.parse(rootDoc);
			Elements metaCsrf = doc.select("meta[name=csrf-token]");
			rw.setCsrf(metaCsrf.attr("content"));

			long unixTime = System.currentTimeMillis();

			// Post the code to get Compiled
			String postCT = rw.post(url, postBody);

			// System.out.println("Response-> " + postCT);

			// Process the response
			ResponseParser parser = new ResponseParser();
			Long runId = parser.getRunId(postCT);

			System.out.println("Code Submitted for testing...");

			for (int i = 0; i < LIMIT; i++) {
				String getCTResult = rw.get(url + "/" + runId + "?_=" + unixTime);

				// System.out.println("Status ->" + getCTResult);

				if (parser.isValidStatus(getCTResult)) {
					// Got a valid result
					// prepare the output and show it
					System.out.println();
					parser.getRunStatus(getCTResult);
					break;
				} else {
					// Server is still preparing results.. just show the current
					// status
					parser.getCurrentStatus(getCTResult);
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	private String getProblemName(String name) {
		// assuming problem name does not contain any "."
		return name.substring(0, name.indexOf("."));
	}

	private String readFile(File f) {
		if (f == null || !f.isFile()) {
			return null;
		}
		String ret = "";
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(f));
			while ((sCurrentLine = br.readLine()) != null) {
				ret += sCurrentLine + "\n";
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				System.out.println(ex.getMessage());
			}
		}
		return ret;
	}
}
