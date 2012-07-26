/* ------------------------------------------------------------
   About:      3D Vector class
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package Java3D;

public class	Vector3D extends Point3D implements Vector
{
	/* Constructors
	---------------------------------------------------------------------- */
	public	Vector3D()
	{
		super();
	}
	
	public	Vector3D(double x, double y, double z)
	{
		super(x, y, z);
	}
	
	public	Vector3D(GraphicsObject object)
	{
		super(object);
	}
	
	public	Vector3D(Point start, Point end)
	{
		set(start, end);
	}
	
	
	/* Accessors
	---------------------------------------------------------------------- */
	public double	getLength()
	{
		return getDistanceFrom(new Point3D());
	}
	
	public Vector	getUnitVector()
	{
		return new Vector3D( getQuotient(getLength()) );
	}
	
	public GraphicsObject	clone()
	{
		return new Vector3D(this);
	}
	
	public String	toString()
	{
		return "<" + getX() + ", " + getY() + ", " + getZ() + ">";
	}
	
	
	/* Modifiers
	---------------------------------------------------------------------- */
	public void	set(Point start, Point end)
	{
		set(end.getDifference(start));
	}
	
	public void	setLength(double length)
	{
		set( getUnitVector().getProduct(length) );
	}
	
	
	/* Mathematics
	---------------------------------------------------------------------- */
	public double	getDotProduct(Vector v)
	{
		if (v instanceof Vector3D)
		{
			Vector3D	v3D = (Vector3D) v;
			return ( getX() * v3D.getX() + 
					 getY() * v3D.getY() + 
					 getZ() * v3D.getZ() );
		}
		return 0;
	}
	
	public Vector	getCrossProduct(Vector v)
	{
		if (v instanceof Vector3D)
		{
			Vector3D	v3D = (Vector3D) v;
			return new Vector3D( this.getY() * v3D.getZ() - this.getZ() * v3D.getY() , 
								 this.getZ() * v3D.getX() - this.getX() * v3D.getZ() , 
								 this.getX() * v3D.getY() - this.getY() * v3D.getX() );
		}
		return new Vector3D();
	}
	
	public double	getAngleBetween(Vector v)
	{
		if (v instanceof Vector3D)
		{
			Vector3D	v3D = (Vector3D) v;
			double	length1 = this.getLength();
			double	length2 = v3D.getLength();
			
			if (length1 == 0 || length2 == 0)
			{
				return 0;
			}
			
			return Math.acos( this.getDotProduct(v3D) / (length1 * length2) );
		}
		return 0;
	}
	
	public boolean	isParallelTo(Vector v)
	{
		if (v instanceof Vector3D)
		{
			Vector3D	v3D = (Vector3D) v;
			double	x1 = this.getX();
			double	y1 = this.getY();
			double	z1 = this.getZ();
			double	x2 = v3D.getX();
			double	y2 = v3D.getY();
			double	z2 = v3D.getZ();
			double	xR = 0, yR = 0, zR = 0;
			
			// If one component is zero and the other is not, 
			// the vectors cannot be parallel
			if (  x1 != x2  &&  x1 * x2 == 0  ||  
				  y1 != y2  &&  y1 * y2 == 0  ||  
				  z1 != z2  &&  z1 * z2 == 0  )
			{
				return false;
			}
			
			// Calculates the component ratios
			if (x2 != 0)	xR = x1 / x2;
			if (y2 != 0)	yR = y1 / y2;
			if (z2 != 0)	zR = z1 / z2;
			
			// Compares non-zero ratios for equality
			return (xR == 0 || yR == 0 || xR == yR) && 
				   (yR == 0 || zR == 0 || yR == zR) && 
				   (xR == 0 || zR == 0 || xR == zR);
		}
		return false;
	}
}
