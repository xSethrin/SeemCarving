//package SeamCarving;
import java.util.ArrayList;

public class Seam {

    int delay = 10;

    private UWECImage im;
    private int width;
    private int height;
    public ArrayList <Pixel> seam = new ArrayList <Pixel>();

    /**
     * vertically shrink the image
     * @param im image
     */
    public void verticalSeamShrink(UWECImage im, boolean rotate) {
        this.im = im;
        this.width = im.getWidth();
        this.height = im.getHeight();

        seam.clear();//clear the seam for finding the new seam
        int currentPixel_X = 0;//the x value for the pixel used by the program now
        int currentPixel_Y = height - 1;//the y value for the pixel used by the program now

        //find the pixel with min energy on the bottom row
        int minEnergy = 0;//save the energy of pixel with smallest energy
        int energy = 0;//temporarily save the pixel energy
        for(int i = 0; i < width; i++){//calculate all pixels on the bottom row
            energy = getImageEnergy(i,currentPixel_Y);
            if(minEnergy < energy){
                minEnergy = energy;//save the min energy
                currentPixel_X = i;//save the x value of the pixel with min energy
            }
        }

        int leftEnergy, middleEnergy, rightEnergy;//temporarily save the energy of three pixels above the current pixel
        for(; currentPixel_Y > 0; --currentPixel_Y) {//go from bottom row to the top row
            Pixel pixel = new Pixel(currentPixel_X, currentPixel_Y);
            seam.add(pixel);//add the current pixel to the seam
            //get the pixel with min energy on the next row above the current pixel
            if (currentPixel_X > 0 && currentPixel_X < (width - 1)) {//get three pixels on the next row and find the min energy
                leftEnergy = getImageEnergy(currentPixel_X - 1, currentPixel_Y - 1);
                middleEnergy = getImageEnergy(currentPixel_X, currentPixel_Y - 1);
                rightEnergy = getImageEnergy(currentPixel_X + 1, currentPixel_Y - 1);
                if (leftEnergy < middleEnergy && leftEnergy < rightEnergy) //the pixel with min energy is on the left
                    currentPixel_X--;
                else if (rightEnergy < leftEnergy && rightEnergy < middleEnergy) //the pixel with min energy is on the right
                    currentPixel_X++;
                else if (middleEnergy < leftEnergy && middleEnergy < rightEnergy){//the pixel with min energy is on the middle
                    ;
                }
                else if (leftEnergy == middleEnergy && middleEnergy < rightEnergy){
                    ;
                }
                else if (rightEnergy == middleEnergy && middleEnergy < leftEnergy){
                    ;
                }
                else if (rightEnergy == leftEnergy && middleEnergy > leftEnergy){
                    currentPixel_X++;
                }
            } else if (currentPixel_X == 0) {//get two pixels on the next row and find the min energy
                middleEnergy = getImageEnergy(currentPixel_X, currentPixel_Y - 1);
                rightEnergy = getImageEnergy(currentPixel_X + 1, currentPixel_Y - 1);
                if (rightEnergy < middleEnergy)//the pixel with min energy is on the right
                    currentPixel_X++;
                else
                    ;//the pixel with min energy is on the middle
            } else if (currentPixel_X == (width - 1)) {
                leftEnergy = getImageEnergy(currentPixel_X - 1, currentPixel_Y - 1);
                middleEnergy = getImageEnergy(currentPixel_X, currentPixel_Y - 1);
                if (leftEnergy < middleEnergy)//the pixel with min energy is on the left
                    currentPixel_X--;
                else
                    ;//the pixel with min energy is on the middle
            }
        }
        Pixel pixel = new Pixel(currentPixel_X, currentPixel_Y);
        seam.add(pixel);//add the last pixel of the seam
        if(!rotate) {
            markSeam();
            im.repaintCurrentDisplayWindow();//mark the seam and repaint the image
        }

        //delay to show the seam

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        im.switchImage(remove());//remove the seam and switch the image
    }


    /**
     * horizontally shrink the image
     * @param im image
     */
    public void horizontalSeamShrink(UWECImage im, boolean rotate) {
        if(rotate) im.rotate(im);
        verticalSeamShrink(im,rotate);
        if(rotate) im.rotate(im);
    }

    /**
     * mark the seam on the image
     */
    private void markSeam() {
        int x,y;
    	for(int i = 0; i < seam.size(); i++) {
    	    x = seam.get(i).getX();
    	    y = seam.get(i).getY();
            im.setRGB(x,y,255 - im.getRed(x,y),255 - im.getGreen(x,y),255 - im.getBlue(x,y));
    	}
    }

    /**
     * get the energy of a pixel in an image
     * @param x x-value of the pixel
     * @param y y-value of the pixel
     * @return the energy of the pixel
     */
    public int getImageEnergy(int x, int y){

        /** store the RGB value of pixels around this pixel
         *       |  R      G      B
         * Left  |(0,0)  (0,1)  (0,2)
         * Right |(1,0)  (1,1)  (1,2)
         * Up    |(2,0)  (2,1)  (2,2)
         * Down  |(3,0)  (3,1)  (3,2)
         */
        int [][] pixelsRGB = new int[4][3];

        /** store the coordinate values of pixels around this pixel
         *       |  X      Y
         * Left  |(0,0)  (0,1)
         * Right |(1,0)  (1,1)
         * Up    |(2,0)  (2,1)
         * Down  |(3,0)  (3,1)
         */
        int[][] pixelsXY = new int[4][2];

        //get left neighbor
        if((x - 1) < 0) pixelsXY[0][0] = width - 1;//pixel is at left border
        else pixelsXY[0][0] = x - 1;
        pixelsXY[0][1] = y;

        //get right neighbor
        if((x + 1) == width) pixelsXY[1][0] = 0;//pixel is at right border
        else pixelsXY[1][0] = x + 1;
        pixelsXY[1][1] = y;

        //get up neighbor
        if((y - 1) < 0) pixelsXY[2][1] = height - 1;//pixel is at up border
        else pixelsXY[2][1] = y - 1;
        pixelsXY[2][0] = x;

        //get down neighbor
        if((y + 1) == height) pixelsXY[3][1] = 0;//pixel is at down border
        else pixelsXY[3][1] = y + 1;
        pixelsXY[3][0] = x;

        //get the RGB values of the pixels
        for(int i = 0; i < 4; i++){
            pixelsRGB[i][0] = im.getRed(pixelsXY[i][0], pixelsXY[i][1]);
            pixelsRGB[i][1] = im.getGreen(pixelsXY[i][0], pixelsXY[i][1]);
            pixelsRGB[i][2] = im.getBlue(pixelsXY[i][0], pixelsXY[i][1]);
        }

                //Δx^2
        return  (pixelsRGB[0][0] - pixelsRGB[1][0]) * (pixelsRGB[0][0] - pixelsRGB[1][0]) + //(Left_R - Right_R)^2
                (pixelsRGB[0][1] - pixelsRGB[1][1]) * (pixelsRGB[0][1] - pixelsRGB[1][1]) + //(Left_G - Right_G)^2
                (pixelsRGB[0][2] - pixelsRGB[1][2]) * (pixelsRGB[0][2] - pixelsRGB[1][2]) + //(Left_B - Right_B)^2
                //Δy^2
                (pixelsRGB[3][0] - pixelsRGB[2][0]) * (pixelsRGB[3][0] - pixelsRGB[2][0]) + //(Down_R - Up_R)^2
                (pixelsRGB[3][1] - pixelsRGB[2][1]) * (pixelsRGB[3][1] - pixelsRGB[2][1]) + //(Down_G - Up_G)^2
                (pixelsRGB[3][2] - pixelsRGB[2][2]) * (pixelsRGB[3][2] - pixelsRGB[2][2]);  //(Down_B - Up_B)^2
    }

    /**
     * print the energy of the image
     * @param im
     */
    public void test(UWECImage im){
        this.im = im;
        this.width = im.getWidth();
        this.height = im.getHeight();
        for(int w = 0; w < im.getWidth()-1; w++){
            for(int h = 0; h < im.getHeight()-1; h++){
                int e = getImageEnergy(w,h);
                int v = map(e);
                im.setRGB(w,h,v,v,v);
            }
        }
    }

    /**
     * helper method to change energy value to gray scale
     * @param x
     * @return
     */
    private int map(int x){
        double k = x/303750.0*255;
        return (int)k;
    }

    public UWECImage remove(){
        UWECImage newImage = new UWECImage(width - 1, height); //create a new smaller image
        for(int y = height - 1; y >= 0; y--){
            for(int x = 0; x < seam.get(seam.size() - y - 1).getX(); x++){ //copy the pixels before the seam
                newImage.setRGB(x, y, im.getRed(x,y),im.getGreen(x,y),im.getBlue(x,y));
            }
            for(int x = seam.get(seam.size() - y - 1).getX(); x < width - 2; x++){ // skip the seam
                newImage.setRGB(x, y, im.getRed(x+1,y),im.getGreen(x+1,y),im.getBlue(x+1,y));
            }
        }
        return newImage;
    }
}
