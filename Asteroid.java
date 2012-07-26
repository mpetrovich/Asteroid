/* ------------------------------------------------------------
   About:      Asteriod - Particle Physics Simulator
   Author:     Michael C. Petrovich
               mpetrovich@comcast.net
   
   Copyright © 2003-2006 Michael C. Petrovich
------------------------------------------------------------ */

import Java3D.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class	Asteroid
{
	public static int	numParticles = 80;
	public static double	time = 0.02;
	public static int	pause = 1;
	public static int	historySize = 50;
	public static int	historySpan = 100;
	public static boolean	enableHistory = true;
	public static boolean	fadeHistory = true;
	public static Color	backgroundColor = Color.BLACK;
	public static int   maxSpeed = 50;
	
	public static void	main(String[] args)
	{
		Dimension	windowSize = new Dimension(800, 800);
		Camera.rotateRadius = 5e2;
		
		// Creates the window
		JFrame	window = new JFrame("Asteroid ");
		window.setBounds(new Rectangle(windowSize));
		window.setLayout(new FlowLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final AsteroidPanel	ast = new AsteroidPanel(window, numParticles, time, 
		                   	                        historySize, historySpan, 
		                   	                        backgroundColor, 
		                   	                        enableHistory, fadeHistory);

		window.add(ast);
		window.setVisible(true);
		
		// Display timer
		javax.swing.Timer	dispTimer = new javax.swing.Timer(pause, new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				ast.repaint();
			}
		});
		dispTimer.setRepeats(true);
		dispTimer.start();
	}
}


class	AsteroidPanel extends GraphicsPanel
{
	private int	numParticles;
	private double	time;
	private Group	base;
	private Color	backgroundColor;
	private int	numEvents = 0;
	
	private java.util.Vector<GraphicsObject>	history = new java.util.Vector<GraphicsObject>();
	private int	historySize;
	private int	historySpan;
	private int	historySkipRatio;
	private boolean	enableHistory;
	private boolean	fadeHistory;
	
	public void	reset()
	{
		setBackground(backgroundColor);
		
		// Sets the particle extremes
		int	maxSpeed = Asteroid.maxSpeed;
		Particle	min = new Particle();
		Particle	max = new Particle();
		// Position
		min.set(0, 0, 0);
		max.set(1000, 1000, 1000);
		// Velocity
		min.velocity.set(-maxSpeed, -maxSpeed, -maxSpeed);
		max.velocity.set(maxSpeed, maxSpeed, maxSpeed);
		// Mass
		min.mass = 1;
		max.mass = 1;
		// Color
		min.color = new Color(150, 100, 0);
		max.color = new Color(255, 255, 255);
		
		// Creates the particles
		env.group.clear();
		
		for (int i = 0; i < numParticles; i++)
		{
			Particle	p = new Particle(min, max);
			env.group.add(p);
		}
	}
	
	public void	debug()
	{
		env.camera.rotateOrigin.set(0, 0, 0);
		for (int a = 0; a < 360; a += 10)
		{
			env.camera.viewAngles.setX(0);
			env.camera.viewAngles.setY(a);
			env.camera.viewAngles.setZ(60);
			System.out.println(env.camera);
			System.out.println();
		}
	}
	
	public	AsteroidPanel(JFrame window, int numParticles, double time, 
	      	              int historySize, int historySpan, Color backgroundColor, 
	      	              boolean enableHistory, boolean fadeHistory)
	{
		super(window, new Environment());
		this.numParticles = numParticles;
		this.time = time;
		this.historySize = historySize;
		this.historySpan = historySpan;
		historySkipRatio = (int) (historySpan / historySize);
		this.backgroundColor = backgroundColor;
		this.enableHistory = enableHistory;
		this.fadeHistory = fadeHistory;
		reset();
		recenter();
		base = env.group.clone();
	}
	
	public void	paint(Graphics g)
	{
		super.paint(g);
		
		env.group.set(base);		// Restores the uncolorized points
		ParticlePhysics.interactAll(env.group, time);
		ParticlePhysics.moveAll(env.group, time);
		base.set(env.group);		// Backs up the points before they're colorized
		
		if (enableHistory)
		{
			while (history.size() >= historySize)
				history.remove(0);
			if (history.size() < historySize-1 || numEvents % historySkipRatio == 0)
			{
				history.add(env.group.clone());
			}
			numEvents++;
			if (fadeHistory)
			{
				ParticlePhysics.fadeHistory(history, historySize, backgroundColor);
			}
			for (GraphicsObject obj : history)
			{
				Group	group = (Group) obj.clone();
				for (int i = 0; i < group.size(); i++)
				{
					env.group.add(group.get(i));
				}
			}
		}
		
		ParticlePhysics.colorAll(env);
	}
}