/* ------------------------------------------------------------
   About:      MPP Reader class
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package	Java3D;

import Java3D.*;
import java.io.*;
import java.util.*;

public class	MPPReader implements Reader
{
	public GraphicsObject	read(String filename)
	{
		try
		{
			Group	object = new Group();
			Scanner	in = new Scanner(new File(filename));
			
			// Skips non-data
			while (in.hasNext() && !in.next().equals("..."))
				;
			
			Group	line = new Group();
			while (in.hasNext())
			{
				double	x, y, z, velocity, intensity;
				
				x = in.nextDouble();
				if (!in.hasNext())
					break;
				y = in.nextDouble();
				z = in.nextDouble();
				velocity = in.nextDouble();
				intensity = in.nextDouble();
				
				if (intensity == 0)
				{
					line.clear();
				}
				line.add(new Point3D(x, y, z));
				
				if (line.size() >= 2)
				{
					object.add(line);
					line.clear();
					line.add(new Point3D(x, y, z));
				}
			}
			
			in.close();
			return object;
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
