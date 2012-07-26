/* ------------------------------------------------------------
   About:      Graphics env container class
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

package Java3D;

public class	Environment
{
	public Group	group = new Group();
	public Camera	camera = new Camera();
	
	
	/* Constructors
	---------------------------------------------------------------------- */
	public	Environment()
	{}
	
	public	Environment(Group group)
	{
		this.group.set(group);
	}
	
	
	/* Accessors
	---------------------------------------------------------------------- */
	public boolean	load(String filename)
	{
		return ObjectLoader.load(filename, group);
	}
	
	public void	dump()
	// DEBUG
	{
		System.out.println("Dumping Environment:");
		System.out.println("  .group:");
		for (int i = 0; i < group.size(); i++)
		{
			System.out.println(group.get(i));
		}
		System.out.println("Dump complete.");
		System.out.println();
	}
}
