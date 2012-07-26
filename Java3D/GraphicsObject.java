/* ------------------------------------------------------------
   About:      Base interface for objects
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package Java3D;

import java.awt.Graphics;

public interface	GraphicsObject extends Cloneable
{
	// Accessors
	public Point	getCenterPoint();
	public Group	toGroup();
	public GraphicsObject	clone();
	
	// Modifiers
	public void	set(GraphicsObject object);
	public void	add(GraphicsObject object);
	public void	rotate(Point angles, Point origin);
	public void	rotate(Point angles);
	
	// Arithmetic
	public GraphicsObject	getNegative();
	public GraphicsObject	getProduct(double factor);
	public GraphicsObject	getQuotient(double factor);
	public void	setNegative();
	public void	setProduct(double factor);
	public void	setQuotient(double factor);
	
	// Graphics
	public GraphicsObject	render(Camera camera);
	public void	draw(Camera camera, Graphics g);
}
