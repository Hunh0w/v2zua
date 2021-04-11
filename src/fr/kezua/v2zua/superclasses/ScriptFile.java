package fr.kezua.v2zua.superclasses;

import java.io.File;

public class ScriptFile {
	
	protected String name;
	protected String path;
	
	public ScriptFile(File path) {
		this.name = path.getName();
		this.path = path.getPath();
	}
	
	public String getName() {
		return name;
	}
	
	public String getPath() {
		return path;
	}

}
