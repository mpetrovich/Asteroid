/* ------------------------------------------------------------
   About:      Perspective Projector class
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package Java3D;

import java.util.*;

public class	PerspectiveProjector extends Projector
{
	public double	perspectiveUnit;
	public List<Point2D>	vanishingPoints;
	
	public PerspectiveProjector()
	{
		perspectiveUnit = 25;
		double	dist = 2000;
		vanishingPoints = new ArrayList<Point2D>();
		vanishingPoints.add(new Point2D(dist, 0));
		vanishingPoints.add(new Point2D(-dist, 0));
		vanishingPoints.add(new Point2D(0, dist));//dist * Math.sqrt(3)));
	}
	
	public Point2D	project3Dto2D(Camera camera, Point3D point3D)
	{
		// DEBUG
		System.out.println("project3Dto2D(" + point3D + ")");
		//*/
		
		// Adjusts for the camera view angles
		Point3D	rotated3D = (Point3D) point3D.clone();
		rotated3D.rotate(camera.viewAngles, camera.rotateOrigin);
		Point2D	rotated = new Point2D(rotated3D.getY(), rotated3D.getZ());
		
		// Calculates the perspective scaling factor
		double	distance = rotated.getDistanceFrom(camera.getCameraOrigin());
		
		// DEBUG
		System.out.println("\trotated     = " + rotated);
		System.out.println("\tcam origin  = " + camera.getCameraOrigin());
		System.out.println("\tdistance    = " + distance);
		//*/
		
		distance /= perspectiveUnit;
		double	scaling = Math.pow(0.5, distance);
		
		// DEBUG
		System.out.println("\tpersp. unit = " + perspectiveUnit);
		System.out.println("\tdistance2   = " + distance);
		System.out.println("\tscaling     = " + scaling);
		//*/
		
		// Calculates the average point transformation
		System.out.println("\tLoop:");     // DEBUG
		Point2D	transform = new Point2D();
		for (Point2D vp : vanishingPoints)
		{
			System.out.println("\t   vp = " + vp);     // DEBUG
			Point2D	shifted = (Point2D) rotated.getDifference(vp);
			System.out.println("\t\tshifted  = " + shifted);     // DEBUG
			transform.setSum( (Point2D) shifted.getProduct(scaling) );
			System.out.println("\t\ttransform = " + transform);     // DEBUG
		}
		transform.setQuotient(vanishingPoints.size());
		System.out.println("\ttransform   = " + transform);     // DEBUG
		rotated.setSum(transform);
		System.out.println("\trotated     = " + rotated);     // DEBUG
		
		return rotated;
	}
}
