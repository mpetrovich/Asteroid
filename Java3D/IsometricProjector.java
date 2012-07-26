/* ------------------------------------------------------------
   About:      Isometric Projector class
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package Java3D;

public class	IsometricProjector extends Projector
{
	public Point2D	project3Dto2D(Camera camera, Point3D point3D)
	{
		Point3D	rotated = (Point3D) point3D.clone();
		rotated.rotate(camera.viewAngles, camera.rotateOrigin);
		Point2D	p = new Point2D(rotated.getY(), rotated.getZ());
		p.color = rotated.color;
		p.radius = rotated.radius;
		return p;
	}
}
