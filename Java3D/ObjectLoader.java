/* ------------------------------------------------------------
   About:      Object Loader class
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package Java3D;

public class	ObjectLoader
{
	public static boolean	load(String filename, GraphicsObject object)
	{
		Reader	reader;
		if (filename.endsWith(".stl") || filename.endsWith(".STL"))
		{
			reader = new STLReader();
		}
		else
		{
			reader = new MPPReader();
		}
		
		GraphicsObject	obj = reader.read(filename);
		if (obj != null)
		{
			object.set(obj);
			return true;
		}
		return false;
	}
}