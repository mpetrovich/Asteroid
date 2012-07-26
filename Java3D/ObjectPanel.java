/* ------------------------------------------------------------
   About:      Object Panel class
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package Java3D;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class	ObjectPanel extends JPanel implements ActionListener
{
	private GraphicsPanel	graphics;
	private Environment	env;
	private String	filename;
	private JButton	loadButton;
	private JTextField	filenameField;
	
	public	ObjectPanel(GraphicsPanel graphics, Environment env, String filename)
	{
		this.graphics = graphics;
		this.env = env;
		this.filename = filename;
		
		loadButton = new JButton("Load");
		filenameField = new JTextField(filename, 12);
		
		loadButton.addActionListener(this);
		
		add(filenameField);
		add(loadButton);
		
		int	width = filenameField.getWidth() + loadButton.getWidth() + 10;
		int	height = filenameField.getHeight() + 10;
		setSize(new Dimension(width, height));
	}
	
	public void	actionPerformed(ActionEvent e)
	{
		if (e.getSource() == loadButton)
		{
			env.load(filenameField.getText());
			graphics.refresh();
		}
	}
}
