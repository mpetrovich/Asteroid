/* ------------------------------------------------------------
   About:      STL Reader class
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package	Java3D;

import Java3D.*;
import java.io.*;
import java.util.*;

public class	STLReader implements Reader
{
	public GraphicsObject	read(String filename)
	{
		try
		{
			Group	object = new Group();
			Scanner	in = new Scanner(new File(filename));
			
			// Skips non-data
			while (in.hasNext() && !in.next().equals("facet"))
				;
			
			while (in.hasNext())
			{
				// Skips non-data
				while (in.hasNext() && !in.hasNextDouble())
					in.next();
				if (!in.hasNext())
					break;
				in.nextDouble();
				in.nextDouble();
				in.nextDouble();
				in.next();
				in.next();
				if (!in.hasNext())
					break;
				
				Group	polygon = new Group();
				for (int i = 0; i < 3; i++)
				{
					double	x, y, z;
					in.next();
					x = in.nextDouble();
					y = in.nextDouble();
					z = in.nextDouble();
					polygon.add(new Point3D(x, y, z));
				}
				object.add(polygon);
			}
			
			in.close();
			return object.getProduct(100);		// DEBUG: For testing only
		}
		catch (FileNotFoundException e)
		{
			System.err.println("File could not be found: " + filename);
			return null;
		}
		catch (IOException e)
		{
			System.err.println("Error reading file: " + filename);
			return null;
		}
	}
}
