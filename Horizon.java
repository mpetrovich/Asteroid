/* ------------------------------------------------------------
   About:      CUB3D class - Java3D Tester
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Version:    0.1.0 build 00
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

import Java3D.*;
import java.awt.*;
import javax.swing.*;

public class	CUB3D
{
	public static void	main(String[] args)
	{
		// Loads the object
		Environment	env = new Environment();
		String	filename = "../Input/cube.stl";
		if (args.length > 0)
		{
			filename = args[0];
		}
		System.out.println("Loading object from file: " + filename);
		env.load(filename);
		if (env.group == null)
		{
			System.err.println("Error reading object from file: " + filename);
			return;
		}
		System.out.println("Object loaded successfully.");
		
		// Creates the main window
		String	version = "0.1.0";
		JFrame	window = new JFrame("CUB3D " + version);
		window.setBounds(0, 0, 600, 600);
		window.setLayout(new FlowLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GraphicsPanel	graphics = new GraphicsPanel(window, env);
		window.add(new ObjectPanel(graphics, env, filename));
		window.add(graphics);
		window.setVisible(true);
		
		/*/ Creates the object window
		JFrame	window2 = new JFrame("Object");
		window2.setBounds(0, 0, 275, 75);
		window2.setLayout(new GridLayout());
		window2.add(new ObjectPanel(graphics, env, filename));
		window2.setVisible(true);//*/
	}
}
