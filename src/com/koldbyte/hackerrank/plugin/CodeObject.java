package com.koldbyte.hackerrank.plugin;

import org.json.simple.JSONObject;

public class CodeObject {
	String code;
	String language = "cpp";
	Boolean customtestcase = false;

	public CodeObject(String code, String language, Boolean customtestcase) {
		super();
		this.code = code;
		this.language = language;
		this.customtestcase = customtestcase;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Boolean getCustomtestcase() {
		return customtestcase;
	}

	public void setCustomtestcase(Boolean customtestcase) {
		this.customtestcase = customtestcase;
	}

	public CodeObject(String code) {
		super();
		this.code = code;
	}

	@SuppressWarnings("unchecked")
	public String getJson() {
		JSONObject obj = new JSONObject();
		obj.put("code", code);
		obj.put("language", extToLangauge(language));
		obj.put("customtestcase", customtestcase);

		String json = obj.toJSONString();
		return json;
	}

	public String extToLangauge(String ext) {
		if (ext.equalsIgnoreCase("sh")) {
			return "bash";
		} else if (ext.equalsIgnoreCase("c")) {
			return "c";
		} else if (ext.equalsIgnoreCase("cpp")) {
			return "cpp";
		} else if (ext.equalsIgnoreCase("cs")) {
			return "csharp";
		} else if (ext.equalsIgnoreCase("java")) {
			return "java";
		} else if (ext.equalsIgnoreCase("js")) {
			return "javascript";
		} else if (ext.equalsIgnoreCase("py")) {
			return "python";
		} else if (ext.equalsIgnoreCase("rb")) {
			return "ruby";
		} else {
			return ext;
		}

	}

}
