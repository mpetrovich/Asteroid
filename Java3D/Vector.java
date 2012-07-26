/* ------------------------------------------------------------
   About:      Interface for vector objects
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package Java3D;

public interface	Vector extends GraphicsObject
{
	// Accessors
	public double	getLength();
	public Vector	getUnitVector();
	
	// Modifiers
	public void	set(Point start, Point end);
	public void	setLength(double length);
	
	// Mathematics
	public double	getDotProduct(Vector v);
	public Vector	getCrossProduct(Vector v);
	public double	getAngleBetween(Vector v);
	public boolean	isParallelTo(Vector v);
}
