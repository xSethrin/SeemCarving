AUTHOR: 
	Nikolo Sperberg and Ruiyang Yu

VERSION:
	1.0

CONTENTS: 
	Pixel.java
	Seam.java
	SeamDemo.java
	UWECImage.java
	
DISCRIPTION: 
	This program takes an image and finds seams with the lowest energy and removes them, resulting 
	in a smaller image that looks funny.
	
COMPILE AND RUN:
	To run this program you first must compile the .java file.  
	Use command prompt and type the following: javac *.java
	This will compile the code.
	To run the code use the command prompt again. Type: java SeamDemo

	By default, the code will remove seams vertically, then horizontally. However, if desired
	the code can remove a vertical seam followed by a horizontal seam by adding "alt" as a
	command line argument.
	Example: java SeamDemo alt
	
	Note: In alt mode, the seams to be removed does not get marked before the seam is 
	removed from the image.  

	By default the image to be altered must be named cat.jpg.  The code will also remove 150 seams
	vertically and 100 horizontally. To change these values please change the following global
	variables in SeamDemo.java:
	
		- fileName (String)
		- verticalSize (int)
		- horizontalSize (int)
	
OTHER:
	To handle ties in the seam, if the tie is between the left or right and middle the code will
	always choose the middle.  If the tie is between just the left and right the code will always
	choose the right.
