package fr.kezua.v2zua.objects;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import fr.kezua.v2zua.superclasses.ScriptFile;

public class HtmlScript extends ScriptFile {
	
	public final Color color = Color.GREEN;

	public HtmlScript(File path) {
		super(path);
	}
	
	public void execute() {
		String os = System.getProperty("os.name");
		String cmd = "";
		if(os.toLowerCase().contains("windows")) {
			cmd = "cmd /c start file:///"+path;
		}else {
			cmd = "bash /c firefox "+path;
		}
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
