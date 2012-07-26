/* ------------------------------------------------------------
   About:      Renderer class
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package Java3D;

import java.util.*;
import java.awt.Graphics;

public class	Renderer
{
	public List<GraphicsObject>	objects;
	public Camera	camera;
	
	
	/* Commands
	---------------------------------------------------------------------- */
	public Renderer()
	{
		camera = new Camera();
		objects = new ArrayList<GraphicsObject>();
		sorted = new ArrayList<SortObject>();
		rendered = new ArrayList<GraphicsObject>();
	}
	
	public void	render()
	{
		// Calculates camera-point distances
		CalculateDistances();
		
		// Sorts by camera-point distances
		SortByDistances();
		
		// Projects 3D points to 2D screen points
		CalculateProjections();
	}
	
	public void	draw(Graphics g)
	{
		for (GraphicsObject obj : rendered)
		{
			obj.draw(camera, g);
		}
	}
	
	public void	update(Graphics g)
	{
		render();
		draw(g);
	}
	
	
	/* Privates
	---------------------------------------------------------------------- */
	private List<SortObject>	sorted;
	public List<GraphicsObject>	rendered;
	
	private class	SortObject
	{
		public GraphicsObject	object;
		public double	distance;
		
		public	SortObject(GraphicsObject object)
		{
			this.object = object;
			distance = 0;
		}
		
		public	SortObject(GraphicsObject object, double distance)
		{
			this.object = object;
			this.distance = distance;
		}
	}
	
	private void	CalculateDistances()
	{
		sorted.clear();
		for (GraphicsObject obj : objects)
		{
			Point2D	center = (Point2D) obj.getCenterPoint();
			Point3D	origin = camera.getCameraOrigin();
			double	distance = center.getDistanceFrom(origin);
			sorted.add( new SortObject(obj, distance) );
		}
	}
	
	private void	SortByDistances()
	{
		Collections.sort(sorted, new Comparator<SortObject>()
		{
			public int compare(SortObject o1, SortObject o2)
			{  return (int)(o1.distance - o2.distance);  }
		} );
	}
	
	private void	CalculateProjections()
	{
		rendered.clear();
		for (SortObject sobj : sorted)
		{
			rendered.add( sobj.object.render(camera) );
		}
	}
}
