/* ------------------------------------------------------------
   About:      Object container class
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package Java3D;

import java.awt.*;
import java.util.*;

public class	Group implements GraphicsObject
{
	private java.util.List<GraphicsObject>	objects = new java.util.Vector<GraphicsObject>();
	
	
	/* Constructors
	---------------------------------------------------------------------- */
	public	Group()
	{}
	
	public	Group(java.util.List<GraphicsObject> objects)
	{
		set(objects);
	}
	
	public	Group(GraphicsObject object)
	{
		set(object);
	}
	
	
	/* Accessors
	---------------------------------------------------------------------- */
	public int	size()
	{
		return objects.size();
	}
	
	public GraphicsObject	get(int index)
	{
		return objects.get(index);
	}
	
	public Point	getCenterPoint()
	{
		if (size() == 0)
		{
			return new Point3D();
		}
		
		Point3D	center = new Point3D();
		for (GraphicsObject obj : objects)
		{
			center.setSum(obj.getCenterPoint());
		}
		center.setQuotient(objects.size());
		return center;
	}
	
	public Group	toGroup()
	{
		return clone();
	}
	
	public Group	clone()
	{
		return new Group(objects);
	}
	
	public boolean	equals(Group group)
	{
		return objects.equals(group.objects);
	}
	
	public String	toString()
	{
		String	str = new String();
		for (GraphicsObject obj : objects)
		{
			str += obj + "\n";
		}
		return str;
	}
	
	
	/* Modifiers
	---------------------------------------------------------------------- */
	public void	set(java.util.List<GraphicsObject> objects)
	{
		clear();
		for (GraphicsObject obj : objects)
		{
			add(obj);
		}
	}
	
	public void	set(GraphicsObject object)
	{
		if (object instanceof Group)
		{
			Group	group = (Group) object;
			set(group.objects);
		}
		else
		{
			objects.clear();
			add(object);
		}
	}
	
	public void	clear()
	{
		objects.clear();
	}
	
	public void	add(GraphicsObject object)
	{
		objects.add(object.clone());
	}
	
	public void	rotate(Point angles, Point origin)
	{
		for (GraphicsObject obj : objects)
		{
			obj.rotate(angles, origin);
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
		GraphicsObject	group = new Group();
		for (GraphicsObject obj : objects)
		{
			group.add(obj.getNegative());
		}
		return group;
	}
	
	public GraphicsObject	getProduct(double factor)
	{
		GraphicsObject	group = new Group();
		for (GraphicsObject obj : objects)
		{
			group.add(obj.getProduct(factor));
		}
		return group;
	}
	
	public GraphicsObject	getQuotient(double factor)
	{
		GraphicsObject	group = new Group();
		for (GraphicsObject obj : objects)
		{
			group.add(obj.getQuotient(factor));
		}
		return group;
	}
	
	public void	setNegative()
	{
		set((Group) getNegative());
	}
	
	public void	setProduct(double factor)
	{
		set((Group) getProduct(factor));
	}
	
	public void	setQuotient(double factor)
	{
		set((Group) getQuotient(factor));
	}
	
	
	/* Graphics
	---------------------------------------------------------------------- */
	public GraphicsObject	render(Camera camera)
	{
		GraphicsObject	rendered = new Group();
		for (GraphicsObject obj : objects)
		{
			rendered.add(obj.render(camera));
		}
		return rendered;
	}
	
	public void	draw(Camera camera, Graphics g)
	{
		java.awt.Polygon	polygon = null;
		for (GraphicsObject obj : objects)
		{
			if (obj instanceof Point2D)
			{
				// Polygon point
				if (false && objects.size() > 1)		// ASTEROID
				{
					if (polygon == null)
						polygon = new java.awt.Polygon();
					
					Point2D	pt = (Point2D) obj;
					polygon.addPoint((int) pt.getX(), (int) pt.getY());
				}
				
				// Lone point
				else
				{
					obj.draw(camera, g);
				}
			}
			else
			{
				obj.draw(camera, g);
			}
		}
		
		if (polygon != null)
		{
			g.drawPolygon(polygon);
		}
	}
}
