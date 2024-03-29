Asteroid
========

A 3D particle simulator I wrote back in college. It uses my [CUB3D](https://github.com/mpetrovich/CUB3D) 3D rendering engine and true gravitational physics. Objects change size based on their distance from the camera.

You'll have to forgive the questionable code structure and formatting. I was but a wee programmer back then…

<img width="912" alt="screenshot" src="https://cloud.githubusercontent.com/assets/1235062/18459666/8049d6ce-7939-11e6-9257-47f9217cc801.png">

<img width="480" alt="screen recording" src="https://cloud.githubusercontent.com/assets/1235062/18460461/b5abc7ae-793f-11e6-85a9-cb42d497c9c4.gif">

Usage
-----
Run `Asteroid.jar` by double-clicking it.

- Rotate camera = Click + Drag
- Zoom in/out = Scroll up/down
- Move camera = Shift Click + Drag

Building from source
--------------------
To compile:
```sh
javac *.java */*.java
```

To bundle into an executable JAR file:
```sh
jar cvfm Asteroid.jar Manifest.txt *.class */*.class
```
