/* ------------------------------------------------------------
   About:      Graphics Panel class
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package Java3D;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class	GraphicsPanel extends JPanel implements MouseListener, MouseMotionListener, 
            	                                        MouseWheelListener
{
	protected JFrame	window;
	protected Environment	env;
	private Point3D	objectCenter;
	private java.awt.Point	lastMousePt;
	
	public void	dump()
	// DEBUG
	{
		System.out.println("Dumping GraphicsPanel:");
		System.out.println("  .object:\n" + env.group);
		System.out.println("  .objectCenter = " + objectCenter);
		System.out.println("  .env.camera:\n" + env.camera);
		System.out.println("  .lastMousePt = " + lastMousePt);
		System.out.println("Dump complete.");
		System.out.println();
	}
	
	public	GraphicsPanel(JFrame window, Environment env)
	{
		this.window = window;
		this.env = env;
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		
		//setBounds(0, 0, window.getWidth(), window.getHeight());
		setPreferredSize(new Dimension(window.getWidth(), window.getHeight()));
		setBackground(Color.GREEN);
		
		env.camera.screenOrigin.set(window.getWidth()/2, window.getHeight()/2);
		env.camera.viewAngles.set(0, 0, 0);
		//env.camera.setProjector(new PerspectiveProjector());
		
		recenter();
	}
	
	public void	paint(Graphics g)
	{
		super.paint(g);
		
		//setBounds(0, 0, window.getWidth(), window.getHeight());
		setPreferredSize(new Dimension(window.getWidth(), window.getHeight()));
		 
		// Draws the object
		env.group.render(env.camera).draw(env.camera, g);
		
		// Draws the env.camera info
		g.setColor(Color.WHITE);
		env.camera.paint(g);
	}
	
	public void	refresh()
	{
		recenter();
		repaint();
	}
	
	public void	recenter()
	{
		objectCenter = (Point3D) env.group.getCenterPoint();
		env.camera.rotateOrigin.set(objectCenter);
		
		// Adjusts the screen origin
		Point2D	objectCenter2D = (Point2D) objectCenter.render(env.camera);
		Point2D	centerDiff = (Point2D) env.camera.screenOrigin.getDifference(objectCenter2D);
		env.camera.screenOrigin.setSum(centerDiff);
	}
	
	public void	redraw(Graphics g)
	{
		env.group.render(env.camera).draw(env.camera, g);
	}
	
	public void	mouseReleased(MouseEvent e)
	{
		lastMousePt = null;
	}
	
	public void	mouseDragged(MouseEvent e)
	{
		java.awt.Point	p = e.getPoint();
		if (lastMousePt != null)
		{
			// Camera screen origin translation
			if (e.isShiftDown() || e.getButton() == MouseEvent.BUTTON3)
			{
				env.camera.screenOrigin.setSum(new Point2D( p.x - lastMousePt.x , 
				                                            p.y - lastMousePt.y ));
			}
			
			// 3D camera rotation
			else
			{
				double	factor = 0.50;
				env.camera.viewAngles.setY( 1 * env.camera.viewAngles.getY() + factor * (p.y - lastMousePt.y) );
				env.camera.viewAngles.setZ( 1 * env.camera.viewAngles.getZ() + factor * (lastMousePt.x - p.x) );
			}
			
			repaint();
		}
		lastMousePt = p;
	}
	
	public void	mouseWheelMoved(MouseWheelEvent e)
	{
		int	clicks = e.getWheelRotation();
		double	zoomFactor = 1.05;
		Point2D	oldObjectCenter2D = (Point2D) objectCenter.render(env.camera);
		
		// Zooms in
		if (clicks > 0)
		{
			env.camera.zoom *= Math.pow(zoomFactor, clicks);
		}
		
		// Zooms out
		else
		{
			env.camera.zoom /= Math.pow(zoomFactor, -clicks);
		}
		
		// Adjusts the screen origin
		Point2D	newObjectCenter2D = (Point2D) objectCenter.render(env.camera);
		Point2D	centerDiff = (Point2D) oldObjectCenter2D.getDifference(newObjectCenter2D);
		env.camera.screenOrigin.setSum(centerDiff);
		
		repaint();
	}
	
	public void	mouseClicked(MouseEvent e)  {}
	public void	mousePressed(MouseEvent e)  {}
	public void	mouseEntered(MouseEvent e)  {}
	public void	mouseExited(MouseEvent e)  {}
	public void	mouseMoved(MouseEvent e)  {}
}
