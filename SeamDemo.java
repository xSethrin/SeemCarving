/**Driver for your code**/
//package SeamCarving;

public class SeamDemo {

	public static void main(String[] args) {
        int delay = 10;
        String fileName = args[0];
		int verticalSize = 150;
		int horizontalSize = 100;
		if (args.length == 2 && args[1].equals("alt")) modeTwo(delay, fileName, verticalSize, horizontalSize);
		else modeOne(delay, fileName, verticalSize, horizontalSize);




/*

        im.write("src/SeamCarving/newImage.png");

*/
	}


	public static void modeOne(int delay, String fileName, int verticalSize, int horizontalSize){
		UWECImage im = new UWECImage(fileName);
		im.openNewDisplayWindow();
		Seam s = new Seam();//you write this

		//watch it shrink vertically!
		for (int i = 0; i < verticalSize; i++) {

			s.verticalSeamShrink(im,false);//you write this
			im.repaintCurrentDisplayWindow();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

		//now horizontally!
		im.rotate(im);
		for (int i = 0; i < horizontalSize; i++) {

			s.horizontalSeamShrink(im,false);//you write this

			im.repaintCurrentDisplayWindow();
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		im.rotate(im);
		im.repaintCurrentDisplayWindow();
	}

	public static void modeTwo(int delay, String fileName, int verticalSize, int horizontalSize){
		UWECImage im = new UWECImage(fileName);

		int max = Math.max(verticalSize,horizontalSize);
		im.openNewDisplayWindow();
		Seam s = new Seam();

		for (int i = 0; i < max; i++) {
			if(i < verticalSize) s.verticalSeamShrink(im,true);
			if(i < horizontalSize) s.horizontalSeamShrink(im, true);
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			im.repaintCurrentDisplayWindow();
		}
		im.repaintCurrentDisplayWindow();
	}


}
