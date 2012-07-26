/* ------------------------------------------------------------
   About:      Camera Panel class
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package Java3D;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class	CameraPanel extends JPanel implements ActionListener
{
	private Camera	camera;
	private JButton	updateButton;
	private JTextField	viewAngleXField, viewAngleYField, viewAngleZField;
	private JTextField	zoomField;
	private JTextField	rotateXField, rotateYField, rotateZField;
	private JTextField	rotateRadiusField;
	private JTextField	screenOriginXField, screenOriginYField;
	
	public	CameraPanel(Camera camera)
	{
		this.camera = camera;
		
		updateButton = new JButton("Update");
		zoomField = new JTextField((new Double(camera.zoom)).toString(), 4);
		
		updateButton.addActionListener(this);
		if (getRootPane() != null)
			getRootPane().setDefaultButton(updateButton);
		
		add(zoomField);
		add(updateButton);
	}
	
	public void	actionPerformed(ActionEvent e)
	{
		if (e.getSource() == updateButton)
		{
			camera.zoom = Integer.parseInt(zoomField.getText());
		}
	}
}
