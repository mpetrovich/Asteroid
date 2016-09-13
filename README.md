Asteroid
========

A 3D particle simulator I wrote back in college. It uses my [CUB3D](https://github.com/mpetrovich/CUB3D) 3D rendering engine and true gravitational physics.

<img width="912" alt="screenshot 2016-09-12 22 33 11" src="https://cloud.githubusercontent.com/assets/1235062/18459627/2761871e-7939-11e6-8d28-3cfa21365505.png">

Controls
--------
Rotate camera = Click + Drag
Zoom in/out = Scroll up/down
Move camera = Shift Click + Drag

Building
--------
To compile:
javac *.java */*.java

To bundle into an executable JAR file:
jar cvfm Asteroid.jar Manifest.txt *.class */*.class
