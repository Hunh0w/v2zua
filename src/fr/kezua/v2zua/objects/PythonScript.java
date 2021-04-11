package fr.kezua.v2zua.objects;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import fr.kezua.v2zua.Main;
import fr.kezua.v2zua.superclasses.ScriptFile;

public class PythonScript extends ScriptFile{
	
	public final Color color = Color.DARK_GRAY;

	public PythonScript(File path) {
		super(path);
	}
	
	public void execute() {
		String os = System.getProperty("os.name");
		File ex;
		String cmd = "";
		if(os.toLowerCase().contains("windows")) {
			ex = new File(Main.CurrentPath+"\\cache\\"+new Random().nextInt(999999)+".bat");
			cmd = "cmd /c start \"\" \""+ex.getPath()+"\"";
		}else {
			ex = new File(Main.CurrentPath+"/cache/"+new Random().nextInt(999999)+".sh");
			cmd = "bash /c sh \""+ex.getPath()+"\"";
		}
		if(ex.exists()) ex.delete();
		try {
			ex.createNewFile();
			BufferedWriter BW = new BufferedWriter(new FileWriter(ex));
			BW.write("python \""+path+"\"");
			BW.close();
			Runtime.getRuntime().exec(cmd);
			ex.deleteOnExit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
