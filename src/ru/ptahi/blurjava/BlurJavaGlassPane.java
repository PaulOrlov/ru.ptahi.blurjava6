/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ptahi.blurjava;

//import hypermedia.video.OpenCV;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.image.BufferedImage;
import java.awt.image.MemoryImageSource;
import java.io.File;
import javax.swing.JComponent;
import javax.swing.JFrame;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.openide.windows.WindowManager;
//import processing.core.PApplet;
//import processing.core.PImage;

/**
 *
 * @author paulorlov
 */
public class BlurJavaGlassPane{
    
}
//public class BlurJavaGlassPane extends JComponent implements Runnable {
//
//    static private BlurJavaGlassPane singleTonPane;
//    private int positionX, positionY;
//    /**
//     * ***
//     */
//    // program execution frame rate (millisecond)
//    final int FRAME_RATE = 1000 / 30;
//    OpenCV cv = null;	// OpenCV Object
//    Thread t = null;	// the sample thread
////   PApplet pa = new PApplet();
//    int x, y;
//    private int cWidth, cHeight;
//    private BufferedImage image = null;
//    String currentDir = new File("").getAbsolutePath();
////    private PImage pimg = pa.loadImage(currentDir + "/data/fonnew.jpg"),
////            pimg1 = pa.loadImage(currentDir + "/data/fonnew1.jpg"),
////            blackpoint = pa.loadImage(currentDir + "/data/blackpoint.jpg");
//    public int nX, nY;
//    private static MultiViewEditorElement mvee;
//    Graphics newR = null;
//
//    /**
//     * *
//     */
//    static void setMvee(MultiViewEditorElement mvee) {
//        BlurJavaGlassPane.mvee = mvee;
//    }
//
////    static void setInstantTo(JFrame frame) {
////        if (singleTonPane == null) {
////            singleTonPane = new BlurJavaGlassPane();
////            frame.setGlassPane(singleTonPane);
////            singleTonPane.setVisible(true);
////        }
////    }
//
//    static BlurJavaGlassPane getInstanse() {
//        return singleTonPane;
//    }
//
//    BlurJavaGlassPane() {
//        cv = new OpenCV();
//        t = new Thread(this);
//        t.start();
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//
//        if (mvee == null) {
//            return;
//        }
//        JComponent editor = mvee.getVisualRepresentation();
//        if (editor == null) {
//            return;
//        }
//
//        if (editor.getParent() == null) {
//            return;
//        }
//
//        double h = editor.getParent().getSize().getHeight();
//        double w = editor.getParent().getSize().getWidth();
//
//        if (h == 0 || w == 0) {
//            return;
//        }
//
//        if (!editor.isShowing() && !this.getParent().isShowing() && !this.isShowing()) {
//            return;
//        }
////
////        try {
////            nX = -this.getParent().getLocationOnScreen().x + editor.getLocationOnScreen().x;
////            nY = -this.getParent().getLocationOnScreen().y + editor.getLocationOnScreen().y;
////        } catch (java.awt.IllegalComponentStateException e) {
////            System.err.println("fail ****** ");
////        }
//
//
////        this.setLocation(nX, nY);
//
////        PointerInfo info = MouseInfo.getPointerInfo();
////        int bMouseX = info.getLocation().x - nX - this.getParent().getLocationOnScreen().x;
////        int bMouseY = info.getLocation().y - nY - this.getParent().getLocationOnScreen().y;
//
//        w = editor.getWidth();
//        h = editor.getHeight();
//        
//        if (image == null
//                || image.getWidth() != (int) w
//                || image.getHeight() != (int) h) {
//            if(newR != null){
//                newR.dispose();
//            }
//            image = (BufferedImage) editor.createImage((int) w, (int) h);
//            newR = image.createGraphics();
//        }
//        
////        editor
////        newR.drawImage(
////                editor.createImage((int)w, (int)h),
////                0, 0,
////                null);
//        
////        editor.paintComponents(newR);
//        newR.fillRect(0, 0, 100, 100);
//        g.drawImage(image, 0, 0, null);
//
////        cv.allocate((int) w, (int) h);
////        cv.copy(new PImage(image));         //  Copies trailsImg into OpenCV buffer so we can put some effects on it
////        cv.blur(OpenCV.BLUR, 6);
//////            image(opencv.image(), 0, 0);
////        Image img = cv.image().getImage();
////
//////            PImage imagef = new PImage(image);
//////            imagef.filter(PApplet.BLUR, 4);            
//////            Image img = imagef.getImage();
////
////        BufferedImage off_Image = new BufferedImage((int) w, (int) h, BufferedImage.TYPE_INT_ARGB);
////        Graphics2D g2_black = off_Image.createGraphics();
////        g2_black.setColor(Color.WHITE);
////        g2_black.fillRect(0, 0, (int) w, (int) h);
////        g2_black.drawImage(blackpoint.getImage(), bMouseX - 50, bMouseY - 50, null);
////
////        pimg = new PImage(off_Image);
////        pimg1 = new PImage(off_Image);
////        pimg1.filter(PApplet.INVERT);
////        PImage tmpimg = new PImage(img);
////        tmpimg.mask(pimg);
////
////        PImage tmpimg1 = new PImage(image);
////        tmpimg1.mask(pimg1);
////        tmpimg.blend(tmpimg1, 0, 0, (int) w, (int) h, 0, 0, (int) w, (int) h, PApplet.BLEND);
////
////        g.drawImage(tmpimg.getImage(), 0, 0, null);
//    }
////
////    @Override
////    public void repaint() {
////        if(BlurJavaMouseInfo.getblurJPA().nX == 0){
////            return;
////        }
////        this.setLocation(BlurJavaMouseInfo.getblurJPA().nX,BlurJavaMouseInfo.getblurJPA().nY);
////        super.repaint();
////    }
//
//    @Override
//    public void run() {
//        while (t != null && cv != null) {
//            try {
//                t.sleep(FRAME_RATE);
//                // of course, repaint
//                repaint();
//            } catch (InterruptedException e) {;
//            }
//        }
//    }
//}
