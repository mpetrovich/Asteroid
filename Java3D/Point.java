/* ------------------------------------------------------------
   About:      Interface for point objects
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package Java3D;

public interface	Point extends GraphicsObject
{
	// Accessors
	public double	getDistanceFrom(Point p);
	
	// Arithmetic
	public GraphicsObject	getSum(Point p);
	public GraphicsObject	getDifference(Point p);
	public void	setSum(Point p);
	public void	setDifference(Point p);
}
