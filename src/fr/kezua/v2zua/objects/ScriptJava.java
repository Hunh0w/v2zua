package fr.kezua.v2zua.objects;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import fr.kezua.v2zua.superclasses.ScriptFile;

public class ScriptJava extends ScriptFile {
	
	public final Color color = Color.RED;
	
	public ScriptJava(File path) {
		super(path);
	}
	
	public void execute() {
		try {
			Runtime.getRuntime().exec("java -jar \""+path+"\"");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
