/* ------------------------------------------------------------
   About:	  Particle physics class
   Author:	 Michael C. Petrovich
			   mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

import Java3D.*;
import java.util.*;
import java.awt.*;

public class	ParticlePhysics
{
	public static double	G = 1e3;
	public static boolean	constantForce = false;
	
	
	public static void	interactAll(Group particles, double time)
	{
		for (int i = 0; i < particles.size(); i++)
		{
			for (int k = 0; k < particles.size(); k++)
			{
				if (i != k)
				{
					ParticlePhysics.interactParticle((Particle) particles.get(i), (Particle) particles.get(k), time);
				}
			}
		}
	}
	
	public static void	moveAll(Group particles, double time)
	{
		for (int i = 0; i < particles.size(); i++)
		{
			ParticlePhysics.moveParticle((Particle) particles.get(i), time);
		}
	}
	
	public static void	interactParticle(Particle p, Particle effect, double time)
	{
		Vector3D	direction = new Vector3D(effect.getDifference(p));
		double	force;
		if (constantForce)
			force = getConstantForce(p, effect);
		else
			force = getGravitationalForce(p, effect);
		p.velocity.setSum( (Point3D) direction.getProduct(force / p.mass * time) );
	}
	
	public static void	moveParticle(Particle p, double time)
	{
		p.setSum( (Point3D) p.velocity.getProduct(time) );
	}
	
	public static double	getConstantForce(Particle p1, Particle p2)
	{
		return G / 5e4;
	}
	
	public static double	getGravitationalForce(Particle p1, Particle p2)
	{
		double	distance = p1.getDistanceFrom(p2);
		if (distance == 0)
			return 0;
		return (G * p1.mass * p2.mass) / (distance * distance);
	}
	
	public static void	colorAll(Environment env)
	{
		java.util.Vector<Double>	distances = new java.util.Vector<Double>();
		Point3D	cameraOrigin = env.camera.getCameraOrigin();
		double	refDistance = env.camera.rotateOrigin.getDistanceFrom(cameraOrigin);
		
		// Calculates all camera-object distances
		double	min = 1e10, max = 0;
		for (int i = 0; i < env.group.size(); i++)
		{
			Point3D	p = (Point3D) env.group.get(i);
			p = (Point3D) p.getCenterPoint();
			distances.add(p.getDistanceFrom(cameraOrigin));
		}
		
		// Adjusts particle colors & radii based on their absolute object-camera distances
		for (int i = 0; i < env.group.size(); i++)
		{
			if (env.group.get(i) instanceof Point2D)
			{
				double	proximity = getProximity(distances.get(i), refDistance, 0.01, 4);
				
				// Color
				Point2D pt = (Point2D) env.group.get(i);
				int	r = pt.color.getRed();
				int	g = pt.color.getGreen();
				int	b = pt.color.getBlue();
				r = (r * proximity > 255 ? 255 : (int) (r * proximity));
				g = (g * proximity > 255 ? 255 : (int) (g * proximity));
				b = (b * proximity > 255 ? 255 : (int) (b * proximity));
				pt.color = new Color(r, g, b);
				
				// Radius
				int	radius = pt.radius;
				radius *= Math.pow(proximity, 1) * 2;
				radius = (int)(radius * env.camera.zoom);
				if (radius < 1)
					radius = 0;
				pt.radius = radius;
			}
		}
	}
	
	private static double	getProximity(double distance, double refDistance, double min, double max)
	{
		if (distance == 0)
			return max;
		
		double	proximity = refDistance / distance;
		
		if (proximity < min)
			proximity = min;
		if (proximity > max)
			proximity = max;
		
		return proximity;
	}
	
	public static void	fadeHistory(java.util.Vector<GraphicsObject> history, int historySize, Color backColor)
	{
		double	step = 1.0 / (historySize);
		double	time = 0;
		for (GraphicsObject obj : history)
		{
			Group	group = (Group) obj;
			double opacity = ParticlePhysics.getOpacity(time, (Particle) group.get(0));
			time += step;
			for (int i = 0; i < group.size(); i++)
			{
				Point3D	p = (Point3D) group.get(i);
				p.color = makeTransparent(backColor, p.color, opacity);
			}
		}
	}
	
	private static double	getOpacity(double time, Particle particle)
	{
		return 100.0 * Math.pow(time, 1.0 / 4);
	}
	
	private static Color	makeTransparent(Color back, Color front, double opacity)
	{
		if (opacity < 0)
		{
			return back;
		}
		if (opacity >= 100)
		{
			return front;
		}
		
		double	r = back.getRed() + (front.getRed() - back.getRed()) * (opacity / 100.0);
		double	g = back.getGreen() + (front.getGreen() - back.getGreen()) * (opacity / 100.0);
		double	b = back.getBlue() + (front.getBlue() - back.getBlue()) * (opacity / 100.0);
		return new Color((int) r, (int) g, (int) b);
	}
}
