/* ------------------------------------------------------------
   About:      3D Particle class
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

import Java3D.*;
import java.awt.*;

public class	Particle extends Point3D
{
	public Vector3D	velocity = new Vector3D();
	public double	mass = 1;
	
	
	
	/* Constructors
	---------------------------------------------------------------------- */
	public	Particle()
	{
		super();
	}
	
	public	Particle(Particle min, Particle max)
	{
		super();
		set(min, max);
	}
	
	
	/* Accessors
	---------------------------------------------------------------------- */
	public double   getSpeed()
	{
	    return this.velocity.getLength();
	}
	
	public GraphicsObject	clone()
	{
		Particle	p = new Particle();
		p.set(this);
		return p;
	}
	
	public boolean	equals(Particle p)
	{
		return super.equals(p) && 
		       this.velocity.equals(p.velocity) && 
		       this.mass == p.mass;
	}
	
	public String	toString()
	{
		String	s = new String();
		s += "  .location = " + super.toString() + "\n";
		s += "  .velocity = " + velocity + "\n";
		s += "  .mass     = " + mass + "\n";
		s += "  .color    = " + color + "\n";
		s += "  .radius   = " + radius + "\n";
		return s;
	}
	
	public void	dump()
	// DEBUG
	{
		System.out.println("Dumping Particle:");
		System.out.print(this);
		System.out.println("Dump complete.");
		System.out.println();
	}
	
	
	/* Modifiers
	---------------------------------------------------------------------- */
	public void	set(Particle p)
	{
		super.set(p);
		this.velocity.set(p.velocity);
		this.mass = p.mass;
		this.color = p.color;
		this.radius = p.radius;
	}
	
	public void	set(Particle min, Particle max)
	{
		this.setX( random(min.getX(), max.getX()) );
		this.setY( random(min.getY(), max.getY()) );
		this.setZ( random(min.getZ(), max.getZ()) );
		
		velocity.setX( random(min.velocity.getX(), max.velocity.getX()) );
		velocity.setY( random(min.velocity.getY(), max.velocity.getY()) );
		velocity.setZ( random(min.velocity.getZ(), max.velocity.getZ()) );
		
		mass = random(min.mass, max.mass);
		
		int	r = (int) random(min.color.getRed(), max.color.getRed());
		int	g = (int) random(min.color.getGreen(), max.color.getGreen());
		int	b = (int) random(min.color.getBlue(), max.color.getBlue());
		color = new Color(r, g, b);
	}
	
	private double	random(double min, double max)
	{
		return min + Math.random() * (max - min);
	}
	
	public void	rotate(Java3D.Point angles, Java3D.Point origin)
	{
		super.rotate(angles, origin);
		velocity.rotate(angles, origin);
	}
	
	
	/* Arithmetic
	---------------------------------------------------------------------- */
	public GraphicsObject	getNegative()
	{
		Particle	p = (Particle) clone();
		((Point3D) p).setNegative();
		p.velocity.setNegative();
		return p;
	}
	
	public GraphicsObject	getProduct(double factor)
	{
		Particle	p = (Particle) clone();
		((Point3D) p).setProduct(factor);
		p.velocity.setProduct(factor);
		return p;
	}
	
	public GraphicsObject	getQuotient(double factor)
	{
		Particle	p = (Particle) clone();
		((Point3D) p).setQuotient(factor);
		p.velocity.setQuotient(factor);
		return p;
	}
	
	
	/* Graphics
	---------------------------------------------------------------------- */
	public GraphicsObject	render(Camera camera)
	{
		return super.render(camera);
	}
	
	public void	draw(Camera camera, Graphics g)
	{
		super.draw(camera, g);
	}
}
