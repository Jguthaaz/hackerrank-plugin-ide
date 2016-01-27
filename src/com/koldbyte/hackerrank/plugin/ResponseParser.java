package com.koldbyte.hackerrank.plugin;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class ResponseParser {

	public Long getRunId(String response) {
		JSONObject rootObj = (JSONObject) JSONValue.parse(response);
		JSONObject modelObj = (JSONObject) rootObj.get("model");

		Long runId = (Long) modelObj.get("id");

		// System.out.println(runId);

		return runId;
	}

	public void getRunStatus(String response) {
		JSONObject rootObj = (JSONObject) JSONValue.parse(response);
		JSONObject modelObj = (JSONObject) rootObj.get("model");

		String compileMessage = (String) modelObj.get("compilemessage");

		if (compileMessage.isEmpty()) {
			JSONArray testcases = (JSONArray) modelObj.get("testcase_message");

			JSONArray stdout = (JSONArray) modelObj.get("stdout");
			JSONArray expected = (JSONArray) modelObj.get("expected_output");

			Integer cnt = 0;
			Integer failed = 0;
			for (Object tc : testcases) {
				String testcase = (String) tc;
				System.out.println("Testcase #" + cnt + " : " + testcase);
				if (testcase.compareToIgnoreCase("Wrong Answer") == 0) {
					failed++;
					System.out.println("Expected:\n" + expected.get(cnt));
					System.out.println("Your Output:\n" + stdout.get(cnt));
				} else if (testcase.compareToIgnoreCase("success") != 0) {
					failed++;
				}

				cnt++;
			}

			System.out.println("Passed : " + (cnt - failed) + "/" + cnt);
		} else {
			System.out.println("Compile Error:");
			System.out.println(compileMessage);
		}
	}

	public Boolean isValidStatus(String response) {
		JSONObject rootObj = (JSONObject) JSONValue.parse(response);
		JSONObject modelObj = (JSONObject) rootObj.get("model");
		Long status = (Long) modelObj.get("status");
		if (status != 0) {
			return true;
		}
		return false;
	}

	public void getCurrentStatus(String getCTResult) {
		JSONObject rootObj = (JSONObject) JSONValue.parse(getCTResult);
		JSONObject modelObj = (JSONObject) rootObj.get("model");
		String status = (String) modelObj.get("status_string");
		System.out.println(status);
	}

}
