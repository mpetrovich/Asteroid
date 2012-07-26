/* ------------------------------------------------------------
   About:      Projector class
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package Java3D;

public abstract class	Projector
{
	abstract Point2D	project3Dto2D(Camera camera, Point3D point3D);
	
	public Point2D	project2DtoScreen(Camera camera, Point2D point2D)
	{
		Point2D	p = new Point2D( camera.screenOrigin.getX() + point2D.getX() * camera.zoom ,
		                         camera.screenOrigin.getY() - point2D.getY() * camera.zoom );
		p.color = point2D.color;
		p.radius = point2D.radius;
		return p;
	}
	
	public Point2D	project3DtoScreen(Camera camera, Point3D point3D)
	{
		return project2DtoScreen( camera, project3Dto2D(camera, point3D) );
	}
}
