/* ------------------------------------------------------------
   About:      3D Point class
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package Java3D;

import java.awt.*;

public class	Point3D extends Point2D
{
	protected double	z;
	
	
	/* Constructors
	---------------------------------------------------------------------- */
	public	Point3D()
	{
		super();
		setZ(0);
	}
	
	public	Point3D(double x, double y, double z)
	{
		super(x, y);
		setZ(z);
	}
	
	public	Point3D(GraphicsObject object)
	{
		set(object);
	}
	
	
	/* Accessors
	---------------------------------------------------------------------- */
	public double	getZ()
	{
		return z;
	}
	
	public double	getDistanceFrom(Point3D p)
	{
		if (p instanceof Point3D)
		{
			Point3D	p3D = (Point3D) p;
			return Math.sqrt( (x - p3D.getX()) * (x - p3D.getX()) + 
							  (y - p3D.getY()) * (y - p3D.getY()) + 
							  (z - p3D.getZ()) * (z - p3D.getZ()) );
		}
		return 0;
	}
	
	public Point	getCenterPoint()
	{
		return (Point3D) clone();
	}
	
	public Group	toGroup()
	{
		return new Group(clone());
	}
	
	public GraphicsObject	clone()
	{
		return new Point3D(this);
	}
	
	public boolean	equals(Point3D p)
	{
		return super.equals(p) && 
		       getZ() == p.getZ();
	}
	
	public String	toString()
	{
		return "(" + (int)getX() + ", " + (int)getY() + ", " + (int)getZ() + ", " + color + ", " + radius + ")";
	}
	
	
	/* Modifiers
	---------------------------------------------------------------------- */
	public void	setZ(double z)
	{
		this.z = z;
	}
	
	public void	set(double x, double y, double z)
	{
		super.set(x, y);
		setZ(z);
	}
	
	public void	set(GraphicsObject object)
	{
		super.set(object);
		if (object instanceof Point3D)
		{
			Point3D	p = (Point3D) object;
			setZ(p.getZ());
		}
	}
	
	public void	add(GraphicsObject object)
	{
		set(object);
	}
	
	public void	rotate(Point angles, Point origin)
	{
		if (angles instanceof Point3D)
		{
			Point3D	angles3D = (Point3D) angles;
			
			// Adjusts for the rotation origin
			setDifference(origin);
			
			// Rotates the point CW around the Z axis
			if (angles3D.getZ() != 0)
			{
				double	pointAngle = Math.atan2(y, x) - Math.toRadians(angles3D.getZ());
				double	distance = Math.hypot(x, y);
				x = distance * Math.cos(pointAngle);
				y = distance * Math.sin(pointAngle);
			}
			
			// Rotates the point CCW around the Y axis
			if (angles3D.getY() != 0)
			{
				double	pointAngle = Math.atan2(z, x) - Math.toRadians(angles3D.getY());
				double	distance = Math.hypot(x, z);
				x = distance * Math.cos(pointAngle);
				z = distance * Math.sin(pointAngle);
			}
			
			// Rotates the point CW around the X axis
			if (angles3D.getX() != 0)
			{
				double	pointAngle = Math.atan2(z, y) - Math.toRadians(angles3D.getX());
				double	distance = Math.hypot(y, z);
				y = distance * Math.cos(pointAngle);
				z = distance * Math.sin(pointAngle);
			}
			
			// Re-adjusts for the rotation origin
			setSum(origin);
		}
	}
	
	public void	rotate(Point angles)
	{
		rotate(angles, new Point3D());
	}
	
	
	/* Arithmetic
	---------------------------------------------------------------------- */
	public GraphicsObject	getNegative()
	{
		Point3D	r = (Point3D) clone();
		r.set(-x, -y, -z);
		return r;
	}
	
	public GraphicsObject	getSum(Point p)
	{
		if (p instanceof Point3D)
		{
			Point3D	p3D = (Point3D) p;
			Point3D	r = (Point3D) clone();
			r.set( x + p3D.getX() , 
			       y + p3D.getY() , 
			       z + p3D.getZ() );
			return r;
		}
		return new Point3D();
	}
	
	public GraphicsObject	getDifference(Point p)
	{
		if (p instanceof Point3D)
		{
			Point3D	p3D = (Point3D) p;
			Point3D	r = (Point3D) clone();
			r.set( x - p3D.getX() , 
			       y - p3D.getY() , 
			       z - p3D.getZ() );
			return r;
		}
		return new Point3D();
	}
	
	public GraphicsObject	getProduct(double factor)
	{
		Point3D	r = (Point3D) clone();
		r.set( x * factor , 
		       y * factor , 
		       z * factor );
		return r;
	}
	
	public GraphicsObject	getQuotient(double factor)
	{
		Point3D	r = (Point3D) clone();
		r.set( x / factor , 
		       y / factor , 
		       z / factor );
		return r;
	}
	
	
	/* Graphics
	---------------------------------------------------------------------- */
	public GraphicsObject	render(Camera camera)
	{
		return camera.project3DtoScreen(this);
	}
	
	public void	draw(Camera camera, Graphics g)
	{
		super.draw(camera, g);
	}
}
