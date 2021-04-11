package fr.kezua.v2zua;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LinearGradientPaint;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

import fr.kezua.v2zua.objects.HtmlScript;
import fr.kezua.v2zua.objects.PythonScript;
import fr.kezua.v2zua.objects.RubyScript;
import fr.kezua.v2zua.objects.ScriptJava;
import fr.kezua.v2zua.objects.ScriptJavaScript;
import fr.kezua.v2zua.superclasses.ScriptFile;

public class Main {
	
	public static final String CurrentPath = System.getProperty("user.dir");

	private JFrame frame;
	private JLabel lblVzua;
	private JLabel lblBienvenueSurVzua;
	private JLabel rainbow;
	private JPanel panel;
	private JLabel lblVzua_1;
	
	public Border bord = new RainbowBorder();
	
	public ArrayList<ScriptFile> scripts = new ArrayList<>();
	
	private ImageIcon background;
	private Image icon;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void initValues() {
		background = new ImageIcon(this.getClass().getClassLoader().getResource("assets/bg.png"));
		icon = new ImageIcon(this.getClass().getClassLoader().getResource("assets/icon.jpg")).getImage();
	}
	
	public void mkd(File cpath) {
		if(!cpath.exists()) {
			if(!cpath.mkdir()) {
				System.out.println("Error while mkdir of scripts folder : '"+cpath.getPath()+"'");
				return;
			}
		}
	}
	
	public void loadDirs() {
		File cpath = new File(CurrentPath+"\\scripts");
		mkd(cpath);
		cpath = new File(CurrentPath+"\\cache");
		mkd(cpath);
		cpath = new File(CurrentPath+"\\scripts\\Java");
		mkd(cpath);
		cpath = new File(CurrentPath+"\\scripts\\Python");
		mkd(cpath);
		cpath = new File(CurrentPath+"\\scripts\\Ruby");
		mkd(cpath);
		cpath = new File(CurrentPath+"\\scripts\\HTML");
		mkd(cpath);
		cpath = new File(CurrentPath+"\\scripts\\JavaScript");
		mkd(cpath);
	}
	
	public void loadScripts() {
		File cpath = new File(CurrentPath+"\\scripts");
		for(File f1 : cpath.listFiles()) {
			if(f1.isDirectory()) {
				if(f1.getName().equalsIgnoreCase("Java")) {
					for(File f2 : f1.listFiles()) {
						if(f2.isFile() && f2.getName().endsWith(".jar")) {
							ScriptFile fscript = new ScriptJava(f2);
							scripts.add(fscript);
						}
					}
				}else if(f1.getName().equalsIgnoreCase("JavaScript")) {
					for(File f2 : f1.listFiles()) {
						if(f2.isFile() && f2.getName().endsWith(".js")) {
							ScriptFile fscript = new ScriptJavaScript(f2);
							scripts.add(fscript);
						}
					}
				}else if(f1.getName().equalsIgnoreCase("Ruby")) {
					for(File f2 : f1.listFiles()) {
						if(f2.isFile() && f2.getName().endsWith(".rb")) {
							ScriptFile fscript = new RubyScript(f2);
							scripts.add(fscript);
						}
					}
				}else if(f1.getName().equalsIgnoreCase("Python")) {
					for(File f2 : f1.listFiles()) {
						if(f2.isFile() && f2.getName().endsWith(".py")) {
							ScriptFile fscript = new PythonScript(f2);
							scripts.add(fscript);
						}
					}
				}else if(f1.getName().equalsIgnoreCase("HTML")) {
					for(File f2 : f1.listFiles()) {
						if(f2.isFile() && f2.getName().endsWith(".html")) {
							ScriptFile fscript = new HtmlScript(f2);
							scripts.add(fscript);
						}
					}
				}
			}
		}
	}
	
	public void createButtons() {
		
		for(ScriptFile sf : scripts) {
			JButton but = new JButton(sf.getName());
			but.setBackground(Color.BLACK);
			but.setFocusable(false);
			but.setBorder(BorderFactory.createCompoundBorder(bord, but.getBorder()));
			if(sf instanceof PythonScript) {
				PythonScript pysc = (PythonScript)sf;
				but.setForeground(pysc.color);
			}else if(sf instanceof RubyScript) {
				RubyScript rsc = (RubyScript)sf;
				but.setForeground(rsc.color);
			}else if(sf instanceof ScriptJava) {
				ScriptJava javsc = (ScriptJava)sf;
				but.setForeground(javsc.color);
			}else if(sf instanceof HtmlScript) {
				HtmlScript html = (HtmlScript)sf;
				but.setForeground(html.color);
			}else if(sf instanceof ScriptJavaScript) {
				ScriptJavaScript js = (ScriptJavaScript)sf;
				but.setForeground(js.color);
			}
			panel.add(but);
			but.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(sf instanceof PythonScript) {
						PythonScript pysc = (PythonScript)sf;
						pysc.execute();
					}else if(sf instanceof RubyScript) {
						RubyScript rsc = (RubyScript)sf;
						rsc.execute();
					}else if(sf instanceof ScriptJava) {
						ScriptJava javsc = (ScriptJava)sf;
						javsc.execute();
					}else if(sf instanceof HtmlScript) {
						HtmlScript html = (HtmlScript)sf;
						html.execute();
					}else if(sf instanceof ScriptJavaScript) {
						ScriptJavaScript js = (ScriptJavaScript)sf;
						js.execute();
					}
				}
			});
		}
	}

	public Main() {
		loadDirs();
		loadScripts();
		initValues();
		
		frame = new JFrame("v2zua By Kezua");
		frame.setIconImage(icon);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		rainbow = new JLabel("");
		rainbow.setBounds(0, 200, 1084, 461);
		
		panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(50);
		flowLayout.setHgap(20);
		panel.setBackground(new Color(0f, 0f, 0f, 0.6f));
		panel.setBounds(10, 321, 1064, 329);
		createButtons();
		frame.getContentPane().add(panel);
		
		
		
		lblVzua_1 = new JLabel("v2zua");
		lblVzua_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblVzua_1.setForeground(new Color(128, 0, 0));
		lblVzua_1.setFont(new Font("Cambria", Font.BOLD, 70));
		lblVzua_1.setBounds(0, 0, 1084, 200);
		frame.getContentPane().add(lblVzua_1);
		rainbow.setBorder(bord);
		frame.getContentPane().add(rainbow);
		lblVzua = new JLabel("");
		lblVzua.setIcon(background);
		
		
		lblVzua.setBackground(new Color(128, 0, 0));
		lblVzua.setFont(new Font("Sitka Text", Font.BOLD, 99));
		lblVzua.setForeground(new Color(0, 0, 0));
		lblVzua.setHorizontalAlignment(SwingConstants.CENTER);
		lblVzua.setBounds(0, 0, 1084, 200);
		frame.getContentPane().add(lblVzua);
		
		lblBienvenueSurVzua = new JLabel("Tool By Kezua");
		lblBienvenueSurVzua.setFont(new Font("Tahoma", Font.PLAIN, 50));
		lblBienvenueSurVzua.setBackground(new Color(0, 0, 0));
		lblBienvenueSurVzua.setForeground(new Color(128, 0, 0));
		lblBienvenueSurVzua.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenueSurVzua.setBounds(0, 200, 1084, 93);
		frame.getContentPane().add(lblBienvenueSurVzua);

		frame.setSize(1100, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		setupListeners();
		
	}
	
	public void setupListeners() {
		frame.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Component c = (Component)e.getSource();
                if(c.getWidth() != 1100) {
                	lblVzua.setSize(c.getWidth()-16, lblVzua.getHeight());
                	lblVzua_1.setSize(c.getWidth()-16, lblVzua.getHeight());
                	lblBienvenueSurVzua.setSize(c.getWidth()-16, lblBienvenueSurVzua.getHeight());
                	rainbow.setSize(c.getWidth()-16, rainbow.getHeight());
                	int w = 1100-c.getWidth();
                	w = 1064-w;

                	panel.setSize(w, panel.getHeight());
                }
                if(c.getHeight() != 700) {
                	
                	int h = 700-c.getHeight();
                	h = 461-(h);
                	rainbow.setSize(rainbow.getWidth(), h);
                	int w = 700-c.getHeight();
                	w = 329-(w);

                	panel.setSize(panel.getWidth(), w);
                }
            }
        });
	}
}
class RainbowBorder extends AbstractBorder {

	private static final long serialVersionUID = 1L;

	@Override
    public Insets getBorderInsets(Component c) {
        return super.getBorderInsets(c);
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        insets.bottom = insets.top = insets.left = insets.right = 1;
        return insets;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        LinearGradientPaint lpg = new LinearGradientPaint(
            new Point(x, y),
            new Point(x*2, y + height),
            new float[]{0.0f, 0.25f, 0.5f, 0.75f, 1.0f},
            new Color[]{Color.YELLOW,    Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA});
        g2d.setPaint(lpg);
        g2d.draw(new Rectangle2D.Double(x, y, width - 1, height - 1));
        g2d.dispose();
    }

}