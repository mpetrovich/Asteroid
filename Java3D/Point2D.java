/* ------------------------------------------------------------
   About:      2D Point class
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package Java3D;

import java.awt.*;

public class	Point2D implements Point
{
	public Color	color = Color.RED;
	public int	radius = 2;
	protected double	x, y;
	
	
	/* Constructors
	---------------------------------------------------------------------- */
	public	Point2D()
	{
		set(0, 0);
	}
	
	public	Point2D(double x, double y)
	{
		set(x, y);
	}
	
	public	Point2D(GraphicsObject object)
	{
		set(object);
	}
	
	
	/* Accessors
	---------------------------------------------------------------------- */
	public double	getX()
	{
		return x;
	}
	
	public double	getY()
	{
		return y;
	}
	
	public double	getDistanceFrom(Point p)
	{
		if (p instanceof Point2D)
		{
			Point2D	p2D = (Point2D) p;
			return Math.sqrt( (x - p2D.getX()) * (x - p2D.getX()) + 
							  (y - p2D.getY()) * (y - p2D.getY()) );
		}
		return 0;
	}
	
	public Point	getCenterPoint()
	{
		return (Point2D) clone();
	}
	
	public Group	toGroup()
	{
		return new Group(clone());
	}
	
	public GraphicsObject	clone()
	{
		return new Point2D(this);
	}
	
	public boolean	equals(Point2D p)
	{
		return getX() == p.getX() && 
		       getY() == p.getY();
	}
	
	public String	toString()
	{
		return "(" + getX() + ", " + getY() + ", " + color + ", " + radius + ")";
	}
	
	
	/* Modifiers
	---------------------------------------------------------------------- */
	public void	setX(double x)
	{
		this.x = x;
	}
	
	public void	setY(double y)
	{
		this.y = y;
	}
	
	public void	set(double x, double y)
	{
		setX(x);
		setY(y);
	}
	
	public void	set(GraphicsObject object)
	{
		if (object instanceof Point2D)
		{
			Point2D	p = (Point2D) object;
			set(p.getX(), p.getY());
			color = p.color;
			radius = p.radius;
		}
	}
	
	public void	add(GraphicsObject object)
	{
		if (object instanceof Point2D)
		{
			set((Point2D) object);
		}
	}
	
	public void	rotate(Point angles, Point origin)
	{
		if (angles instanceof Point2D)
		{
			Point2D	angles2D = (Point2D) angles;
			
			// Adjusts for the rotation origin
			setDifference(origin);
			
			// Rotates the point around the origin
			if (angles2D.getX() != 0)
			{
				double	pointAngle = Math.atan2(y, x) - Math.toRadians(angles2D.getX());
				double	distance = getDistanceFrom(new Point2D());
				x = distance * Math.cos(pointAngle);
				y = distance * Math.sin(pointAngle);
			}
			
			// Re-adjusts for the rotation origin
			setSum(origin);
		}
	}
	
	public void	rotate(Point angles)
	{
		rotate(angles, new Point2D());
	}
	
	
	/* Arithmetic
	---------------------------------------------------------------------- */
	public GraphicsObject	getNegative()
	{
		Point2D	r = (Point2D) clone();
		r.set(-x, -y);
		return r;
	}
	
	public GraphicsObject	getSum(Point p)
	{
		if (p instanceof Point2D)
		{
			Point2D	p2D = (Point2D) p;
			Point2D	r = (Point2D) clone();
			r.set( x + p2D.getX() , 
			       y + p2D.getY() );
			return r;
		}
		return new Point2D();
	}
	
	public GraphicsObject	getDifference(Point p)
	{
		if (p instanceof Point2D)
		{
			Point2D	p2D = (Point2D) p;
			Point2D	r = (Point2D) clone();
			r.set( x - p2D.getX() , 
			       y - p2D.getY() );
			return r;
		}
		return new Point2D();
	}
	
	public GraphicsObject	getProduct(double factor)
	{
		Point2D	r = (Point2D) clone();
		r.set( x * factor , 
		       y * factor );
		return r;
	}
	
	public GraphicsObject	getQuotient(double factor)
	{
		Point2D	r = (Point2D) clone();
		r.set( x / factor , 
		       y / factor );
		return r;
	}
	
	public void	setNegative()
	{
		set( (Point) getNegative() );
	}
	
	public void	setSum(Point p)
	{
		set( (Point) getSum(p) );
	}
	
	public void	setDifference(Point p)
	{
		set( (Point) getDifference(p) );
	}
	
	public void	setProduct(double factor)
	{
		set( (Point) getProduct(factor) );
	}
	
	public void	setQuotient(double factor)
	{
		set( (Point) getQuotient(factor) );
	}
	
	
	/* Graphics
	---------------------------------------------------------------------- */
	public GraphicsObject	render(Camera camera)
	{
		return camera.project2DtoScreen(this);
	}
	
	public void	draw(Camera camera, Graphics g)
	{
		g.setColor(color);
		if (radius > 0)
		{
			g.fillOval((int) x, (int) y, radius, radius);
		}
		else
		{
			g.drawRect((int) x, (int) y, 1, 1);
		}
	}
}
