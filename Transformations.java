import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class Transformations {

    static int x0 = 0;
    static int y0 = 0;
    static int x1 = 0;
    static int y1 = 0;
    static int comp = 0;
    static int i = 0;
    static ArrayList<Integer> pointsX = new ArrayList<Integer>();
    static ArrayList<Integer> pointsY = new ArrayList<Integer>();
    
    public static BufferedImage gradientSetRaster(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        WritableRaster raster = img.getRaster();
        int[] pixel = new int[3]; //RGB

            for (int y = 0; y < height; y++) {
                int val = (int) (y * 255f / height);
                for (int shift = 1; shift < 3; shift++) {
                    pixel[shift] = val;
                }
                for (int x = 0; x < width; x++) {
                    raster.setPixel(x, y, pixel);
                }
            }
        return img;
    }

    public static void main(String... args) {
        Frame w = new Frame("Raster");  //window
        final int imageWidth = 500;
        final int imageHeight = 500;

        w.setSize(imageWidth,imageHeight);
        w.setLocation(100,100);
        w.setVisible(true);

        Graphics g = w.getGraphics();

        BufferedImage img = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        gradientSetRaster(img);
        g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);  //draw the image. You can think of this as the display method.

        w.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton() == MouseEvent.BUTTON1) {
                  int[] pixel = {255,255,255};
                  img.getRaster().setPixel(e.getX(),e.getY(),pixel);
                  g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
                  if(!is_not_done()){
                      gradientSetRaster(img);
                      g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
                      pointsX.clear();
                      pointsY.clear();
                      comp = 0;
                      i = 0;
              		  System.out.println("cleared");
                  }
                  else{
            		  pointsX.add(e.getX());
            		  pointsY.add(e.getY());
            		  i++;
                      img.getRaster().setPixel(e.getX(),e.getY(),pixel);
              		  System.out.println("point added");
                  }
                }
            }
            public boolean is_not_done(){
                return comp == 0;
            }
        });
        
        w.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
            	if(e.getKeyCode() == KeyEvent.VK_R){
            		rotate_cw(img, g, pointsX, pointsY, i);
                    gradientSetRaster(img);
                    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
            		draw_polygon(img, g, pointsX, pointsY, i);
                    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
            	}
            	if(e.getKeyCode() == KeyEvent.VK_T){
            		rotate_ccw(img, g, pointsX, pointsY, i);
                    gradientSetRaster(img);
                    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
            		draw_polygon(img, g, pointsX, pointsY, i);
                    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
            	}
            	if(e.getKeyCode() == KeyEvent.VK_ENTER){
            		comp = 1;
            		System.out.println("polygon is done, press mouse once more to clear vertex selection");
            		draw_polygon(img, g, pointsX, pointsY, i);
            	}
            	if(e.getKeyCode() == KeyEvent.VK_DOWN){
            		translate(img, g, pointsX, pointsY, i, 0, 1);
                    gradientSetRaster(img);
                    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
            		draw_polygon(img, g, pointsX, pointsY, i);
                    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
            	}
            	if(e.getKeyCode() == KeyEvent.VK_UP){
            		translate(img, g, pointsX, pointsY, i, 0, -1);
                    gradientSetRaster(img);
                    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
            		draw_polygon(img, g, pointsX, pointsY, i);
                    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
            	}
            	if(e.getKeyCode() == KeyEvent.VK_LEFT){
            		translate(img, g, pointsX, pointsY, i, -1, 0);
                    gradientSetRaster(img);
                    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
            		draw_polygon(img, g, pointsX, pointsY, i);
                    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
            	}
            	if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            		translate(img, g, pointsX, pointsY, i, 1, 0);
                    gradientSetRaster(img);
                    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
            		draw_polygon(img, g, pointsX, pointsY, i);
                    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
            	}
            	if(e.getKeyCode() == KeyEvent.VK_S){
            		scale(img, g, pointsX, pointsY, i, 1.1);
                    gradientSetRaster(img);
                    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
            		draw_polygon(img, g, pointsX, pointsY, i);
                    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
            	}
            	if(e.getKeyCode() == KeyEvent.VK_D){
            		scale(img, g, pointsX, pointsY, i, 0.9);
                    gradientSetRaster(img);
                    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
            		draw_polygon(img, g, pointsX, pointsY, i);
                    g.drawImage(img, 0, 0, (img1, infoflags, x, y, width, height) -> true);
            	}
            }
        });
        
        w.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                w.dispose();
                g.dispose();
                System.exit(0);
            }
        });
    }
    public static void draw_line(BufferedImage img, Graphics g, int x0, int y0, int x1, int y1){
    	int d = 0;
        int[] pixel = {255,255,255};
    	int dx = Math.abs(x1 - x0);
    	int dy = Math.abs(y1 - y0);
    	//use for determining the sign of the slope
    	int ix = x0 < x1 ? 1 : -1; 
    	int iy = y0 < y1 ? 1 : -1;
    	int x = x0;
    	int y = y0;
    	
    	//when slope < 1
    	if(dx >= dy){
    		while(true){
    			img.getRaster().setPixel(x, y, pixel);
    			if(x == x1)
    				break;
    			x += ix;
    			d += dy*2;
    			if(d > dx){
    				y += iy;
    				d -= dx*2;
    			}
    		}
    	}
    	//when slope > 1
    	else{
    		while(true){
    			img.getRaster().setPixel(x, y, pixel);
    			if(y == y1)
    				break;
    			y += iy;
    			d += dx*2;
    			if(d > dy){
    				x += ix;
    				d -= dy*2;
    			}
    		}
    	}
    g.drawImage(img, 0, 0, (img1, infoflags, a, b, width, height) -> true);
    }
    
    public static void draw_polygon(BufferedImage img, Graphics g, ArrayList<Integer> pointsX, ArrayList<Integer> pointsY, int size){
    	for(int j = 0; j < size-1; j++){
			draw_line(img, g, pointsX.get(j), pointsY.get(j), pointsX.get(j+1), pointsY.get(j+1));
		}
			draw_line(img, g, pointsX.get(0), pointsY.get(0), pointsX.get(size-1), pointsY.get(size-1));
    }
    
    public static void rotate_cw(BufferedImage img, Graphics g, ArrayList<Integer> pointsX, ArrayList<Integer> pointsY, int size){
        double degrees = 0.1f;
        //double radians = degrees * Math.PI / 180;
        int xx = pointsX.get(0);
        int yy = pointsY.get(0);
        translate(img, g, pointsX, pointsY, size, -xx, -yy);
        for(int i = 0; i < size; i++){
        	int xnew, ynew = 0;
        	xnew = (int) Math.round((pointsX.get(i) * Math.cos(degrees)) - (pointsY.get(i) * Math.sin(degrees)));
        	ynew   = (int) Math.round((pointsX.get(i) * Math.sin(degrees)) + (pointsY.get(i) * Math.cos(degrees)));
        	pointsX.set(i, xnew);
        	pointsY.set(i, ynew);
        }
        translate(img, g, pointsX, pointsY, size, xx, yy);
    }
    
    public static void rotate_ccw(BufferedImage img, Graphics g, ArrayList<Integer> pointsX, ArrayList<Integer> pointsY, int size){
        double degrees = 0.1f;
        //double radians = degrees * Math.PI / 180;
        int xx = pointsX.get(0);
        int yy = pointsY.get(0);
        translate(img, g, pointsX, pointsY, size, -xx, -yy);
        for(int i = 0; i < size; i++){
        	int xnew, ynew = 0;
        	xnew = (int) Math.round((pointsX.get(i) * Math.cos(degrees)) + (pointsY.get(i) * Math.sin(degrees)));
        	ynew   = (int) Math.round(((-1)*pointsX.get(i) * Math.sin(degrees)) + (pointsY.get(i) * Math.cos(degrees)));
        	pointsX.set(i, xnew);
        	pointsY.set(i, ynew);
        }
        translate(img, g, pointsX, pointsY, size, xx, yy);
    }
    public static void translate(BufferedImage img, Graphics g, ArrayList<Integer> pointsX, ArrayList<Integer> pointsY, int size, int x_incr, int y_incr){
    	for(int i=0; i < size; i++){
    		int xnew, ynew = 0;
    		xnew = pointsX.get(i) + x_incr;
    		ynew = pointsY.get(i) + y_incr;
        	pointsX.set(i, xnew);
        	pointsY.set(i, ynew);
    	}
    }
    public static void scale(BufferedImage img, Graphics g, ArrayList<Integer> pointsX, ArrayList<Integer> pointsY, int size, double k){
    	int transX = pointsX.get(0);
        int transY = pointsY.get(0);
        translate(img, g, pointsX, pointsY, size, -transX, -transY);
    	for(int i = 0; i < size; i++){
    		pointsX.set(i, (int) Math.round(k * pointsX.get(i)));
    		pointsY.set(i, (int) Math.round(k * pointsY.get(i)));
    	}
    	translate(img, g, pointsX, pointsY, size, transX, transY);
    }
}
