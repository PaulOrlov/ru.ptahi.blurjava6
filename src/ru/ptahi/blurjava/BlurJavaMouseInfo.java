/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ptahi.blurjava;

//import hypermedia.video.OpenCV;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.api.java.source.CompilationController;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.Task;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.openide.awt.StatusDisplayer;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.windows.WindowManager;
//import processing.core.PApplet;
//import processing.core.PImage;

/**
 *
 * @author paulorlov
 */
public class BlurJavaMouseInfo {

    private static BlurJavaMouseInfo singletone;
//    private static BlurJPApplet blurJPA;
    private static Lookup lookup;
    private static MultiViewEditorElement mvee;

    public static void startMouseParse() {
        if (singletone == null) {
            singletone = new BlurJavaMouseInfo();
        }
    }

    static void setLookUp(Lookup lkp) {
        BlurJavaMouseInfo.lookup = lkp;
    }

    static void setMvee(MultiViewEditorElement mvee) {
        BlurJavaMouseInfo.mvee = mvee;
    }

//    static BlurJPApplet getblurJPA() {
//        return blurJPA;
//    }

//    static Image getImage() {
//        return blurJPA.get().getImage();
//    }

    private BlurJavaMouseInfo() {

//        blurJPA = new BlurJPApplet();
//        blurJPA.init();
        
//        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//        Runnable r;
//        r = new Runnable() {
//            private MemberVisitor memberVisitor;
//
//            public void blurEditor() {
//                if (BlurJavaGlassPane.getInstanse() == null) {
//                    return;
//                }
//
//                if (mvee == null) {
//                    return;
//                }
//
//                JComponent editor = mvee.getVisualRepresentation();
//                if (editor == null) {
//                    BlurJavaGlassPane.getInstanse().setVisible(false);
//                    return;
//                }
//            
//                if (editor.getParent() == null) {
//                    BlurJavaGlassPane.getInstanse().setVisible(false);
//                    return;
//                }
//                
//                BlurJavaGlassPane.getInstanse().setVisible(true);
//                BlurJavaGlassPane.getInstanse().repaint();
//            }
//
//            private void doTrace() {
//                if (lookup == null) {
//                    return;
//                }
//                final JTextComponent editor = EditorRegistry.lastFocusedComponent();
//                if (editor == null) {
//                    return;
//                }
//                DataObject dataObject = lookup.lookup(DataObject.class);
//                JavaSource javaSource = JavaSource.forFileObject(dataObject.getPrimaryFile());
//                if (javaSource == null) {
//                    StatusDisplayer.getDefault().setStatusText("Not a Java file: "
//                            + dataObject.getPrimaryFile().getName());
//                } else {
//                    System.out.println(dataObject.getName());
//                    try {
//                        javaSource.runUserActionTask(new Task<CompilationController>() {
//                            @Override
//                            public void run(CompilationController compilationController) throws Exception {
//                                compilationController.toPhase(JavaSource.Phase.ELEMENTS_RESOLVED);
//                                compilationController.toPhase(JavaSource.Phase.PARSED);
//                                Document document = compilationController.getDocument();
//                                if (document != null) {
//                                    memberVisitor = new MemberVisitor(compilationController, editor);
//                                    memberVisitor.scan(compilationController.getCompilationUnit(), null);
//                                } else {
//                                    StatusDisplayer.getDefault().setStatusText("The Java file is closed!");
//                                }
//                            }
//                        }, true);
//                    } catch (IOException ex) {
//                        Exceptions.printStackTrace(ex);
//                    }
//                }
//            }
//
//            @Override
//            public void run() {
////                blurEditor();
//            }
//        };
//
//        executor.scheduleAtFixedRate(r, 0, 100, TimeUnit.MILLISECONDS);
    }

//    public static class BlurJPApplet extends PApplet {
//
//        private OpenCV opencv;
//        private int cWidth = 300;
//        private int cHeight = 190;
//        private BufferedImage image = null;
//        
//        String currentDir = new File("").getAbsolutePath();
//        private PImage pimg = loadImage(currentDir + "/data/fonnew.jpg"),
//                       pimg1 = loadImage(currentDir + "/data/fonnew1.jpg"),
//                       blackpoint = loadImage(currentDir + "/data/blackpoint.jpg");
//        public int nX, nY;
//
//        @Override
//        public void setup() {
//            size(cWidth, cHeight);
//            opencv = new OpenCV(this);            //  Initialises the OpenCV library
//            loadPixels();
//            setVisible(true);
//        }
//
//        @Override
//        public void draw() {
//            if (mvee == null) {
//                return;
//            }
//            JComponent editor = mvee.getVisualRepresentation();
//            if (editor == null) {
//                return;
//            }
//            
//            if (editor.getParent() == null) {
//                return;
//            }
//
//            double h = editor.getParent().getSize().getHeight();
//            double w = editor.getParent().getSize().getWidth();
//                        
//            if (h == 0 || w == 0) {
//                return;
//            }
//            
//            if(!editor.isShowing() && !BlurJavaGlassPane.getInstanse().getParent().isShowing() && !BlurJavaGlassPane.getInstanse().isShowing()){
//                return;
//            }
//            
//            try{
//                nX = - BlurJavaGlassPane.getInstanse().getParent().getLocationOnScreen().x + editor.getLocationOnScreen().x;
//                nY = - BlurJavaGlassPane.getInstanse().getParent().getLocationOnScreen().y + editor.getLocationOnScreen().y;
//            } catch(java.awt.IllegalComponentStateException e){
//                System.err.println("fail ****** ");
//            }
//            
//            
//            BlurJavaGlassPane.getInstanse().setLocation(nX, nY);
//            
//            PointerInfo info = MouseInfo.getPointerInfo();            
//            int bMouseX = info.getLocation().x - nX - BlurJavaGlassPane.getInstanse().getParent().getLocationOnScreen().x;
//            int bMouseY = info.getLocation().y - nY - BlurJavaGlassPane.getInstanse().getParent().getLocationOnScreen().y;
//                        
//            if(h != cHeight || w != cWidth){
//                cHeight = (int) h;
//                cWidth = (int) w;
//                size(cWidth, cHeight);
//            }
//
//            if (image == null
//                    || image.getWidth() != (int) w
//                    || image.getHeight() != (int) h) {
//                image = (BufferedImage) editor.createImage((int) w, (int) h);
//            }
//                
//            editor.paint(image.createGraphics());
//            
//            opencv.allocate((int) w, (int) h);
//            opencv.copy(new PImage(image));         //  Copies trailsImg into OpenCV buffer so we can put some effects on it
//            opencv.blur(OpenCV.BLUR, 6);            
////            image(opencv.image(), 0, 0);
//            Image img = opencv.image().getImage();
//            
////            PImage imagef = new PImage(image);
////            imagef.filter(PApplet.BLUR, 4);            
////            Image img = imagef.getImage();
//            
//            BufferedImage off_Image = new BufferedImage((int) w, (int) h, BufferedImage.TYPE_INT_ARGB);
//            Graphics2D g2_black = off_Image.createGraphics();
//            g2_black.setColor(Color.WHITE);
//            g2_black.fillRect(0, 0, (int) w, (int) h);
//            g2_black.drawImage(blackpoint.getImage(), bMouseX - 50, bMouseY - 50, null);
//
//            pimg = new PImage(off_Image);
//            pimg1 = new PImage(off_Image);
//            pimg1.filter(PApplet.INVERT);
//            PImage tmpimg = new PImage(img);
//            tmpimg.mask(pimg);
//
//            PImage tmpimg1 = new PImage(image);
//            tmpimg1.mask(pimg1);
//            tmpimg.blend(tmpimg1, 0, 0, (int) w, (int) h, 0, 0, (int) w, (int) h, PApplet.BLEND);
//                    
//            image(tmpimg, 0, 0);
//            
////            editor.paint(image.createGraphics());
////            opencv.allocate((int) w, (int) h);
////            opencv.copy(new PImage(image));         //  Copies trailsImg into OpenCV buffer so we can put some effects on it
////            opencv.blur(OpenCV.BLUR, 6);            
////            image(opencv.image(), 0, 0);
//            
//            testblur();
//        }
//        
//        public void testblur(){
//                if (BlurJavaGlassPane.getInstanse() == null) {
//                    return;
//                }
//
//                if (mvee == null) {
//                    return;
//                }
//
//                JComponent editor = mvee.getVisualRepresentation();
//                if (editor == null) {
//                    BlurJavaGlassPane.getInstanse().setVisible(false);
//                    return;
//                }
//            
//                if (editor.getParent() == null) {
//                    BlurJavaGlassPane.getInstanse().setVisible(false);
//                    return;
//                }
//                
//                BlurJavaGlassPane.getInstanse().setVisible(true);
//                BlurJavaGlassPane.getInstanse().repaint();
//        }
//    }
}
