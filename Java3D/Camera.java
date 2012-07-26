/* ------------------------------------------------------------
   About:      Camera class
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package Java3D;

import java.awt.*;
import javax.swing.*;
import java.util.*;

public class	Camera
{
	public static double	rotateRadius = 1e3;
	public Point3D	viewAngles;		// Specifies the angle of CW rotation around each axis
	public double	zoom;
	public Point3D	rotateOrigin;
	public Point2D	screenOrigin;
	private Projector	projector;
	
	
	public	Camera()
	{
		viewAngles = new Point3D();
		zoom = 1.0;
		rotateOrigin = new Point3D();
		screenOrigin = new Point2D();
		projector = new IsometricProjector();
	}
	
	public Point3D	getCameraOrigin()
	{
		Point3D	origin = new Point3D();
		origin.setX( rotateRadius * Math.cos(Math.toRadians( viewAngles.getY() )) 
		                          * Math.cos(Math.toRadians( viewAngles.getZ() )) );
		origin.setY( rotateRadius * Math.cos(Math.toRadians( viewAngles.getY() )) 
		                          * Math.sin(Math.toRadians( viewAngles.getZ() )) );
		origin.setZ( rotateRadius * Math.sin(Math.toRadians( viewAngles.getY() )) );
		return (Point3D) origin.getSum(rotateOrigin);
	}
	
	public String	toString()
	{
		String	str = new String();
		str += "\t" + "view          = " + viewAngles + "\n";
	//	str += "\t" + "zoom          = " + zoom + "\n";
	//	str += "\t" + "rotate origin = " + rotateOrigin + "\n";
	//	str += "\t" + "rotate radius = " + rotateRadius + "\n";
	//	str += "\t" + "screen origin = " + screenOrigin + "\n";
		str += "\t" + "camera origin = " + getCameraOrigin() + "\n";
	//	str += "\t" + "projector     = " + projector.getClass().getName();
		return str;
	}
	
	public void	simplifyAngles()
	{
		// X angle
		while (viewAngles.getX() <= -360 || 360 <= viewAngles.getX())
		{
			viewAngles.setX( viewAngles.getX() - 360 * Math.signum(viewAngles.getX()) );
		}
		
		// Y angle
		while (viewAngles.getY() <= -360 || 360 <= viewAngles.getY())
		{
			viewAngles.setY( viewAngles.getY() - 360 * Math.signum(viewAngles.getY()) );
		}
		
		// Z angle
		while (viewAngles.getZ() <= -360 || 360 <= viewAngles.getZ())
		{
			viewAngles.setZ( viewAngles.getZ() - 360 * Math.signum(viewAngles.getZ()) );
		}
	}
	
	public void	paint(Graphics g)
	{
		java.awt.Point	textPoint = new java.awt.Point(10, 0);
		int	lineSpacing = 20;
		Formatter	f;
		
		// View angles
		f = new Formatter();
		simplifyAngles();
		f.format("(%.2f¡, %.2f¡, %.2f¡)", viewAngles.getX(), viewAngles.getY(), viewAngles.getZ());
		g.drawString("View angles: " + f, textPoint.x, textPoint.y += lineSpacing);
		
		// Zoom
		f = new Formatter();
		f.format("%.2f %%", zoom * 100.0);
		g.drawString("Zoom: " + f, textPoint.x, textPoint.y += lineSpacing);
		
		// Rotation origin
		f = new Formatter();
		f.format("(%.2f, %.2f, %.2f)", rotateOrigin.getX(), rotateOrigin.getY(), rotateOrigin.getZ());
		g.drawString("Rotate origin: " + f, textPoint.x, textPoint.y += lineSpacing);
		
		// Screen origin
		f = new Formatter();
		f.format("(%d px, %d px)", (int) screenOrigin.getX(), (int) screenOrigin.getY());
		g.drawString("Screen origin: " + f, textPoint.x, textPoint.y += lineSpacing);
		
		// Camera origin
		f = new Formatter();
		Point3D	cameraOrigin = getCameraOrigin();
		f.format("(%.2f, %.2f, %.2f)", cameraOrigin.getX(), cameraOrigin.getY(), cameraOrigin.getZ());
		g.drawString("Camera origin: " + f, textPoint.x, textPoint.y += lineSpacing);
	}
	
	
	/* Projection
	---------------------------------------------------------------------- */
	public void	setProjector(Projector projector)
	{
		this.projector = projector;
	}
	
	public Point2D	project3Dto2D(Point3D point3D)
	{
		if (projector != null)
		{
			return projector.project3Dto2D(this, point3D);
		}
		else
		{
			return null;
		}
	}
	
	public Point2D	project2DtoScreen(Point2D point2D)
	{
		if (projector != null)
		{
			return projector.project2DtoScreen(this, point2D);
		}
		else
		{
			return null;
		}
	}
	
	public Point2D	project3DtoScreen(Point3D point3D)
	{
		if (projector != null)
		{
			return projector.project3DtoScreen(this, point3D);
		}
		else
		{
			return null;
		}
	}
}
