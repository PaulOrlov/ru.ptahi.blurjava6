/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ptahi.blurjava;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.util.Hashtable;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.IOException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.SwingUtilities;
import javax.swing.plaf.LayerUI;
import org.netbeans.core.spi.multiview.MultiViewElement;
import org.netbeans.core.spi.multiview.text.MultiViewEditorElement;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.MIMEResolver;
import org.openide.loaders.DataObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.MultiFileLoader;
import org.openide.util.Lookup;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_core.*;
import com.jogamp.opencl.CLContext;
import java.awt.image.DataBufferByte;
import static java.lang.System.out;


@Messages({
    "LBL_BlurJavaModule_LOADER=Files of BlurJavaModule"
})
@MIMEResolver.ExtensionRegistration(
    displayName = "#LBL_BlurJavaModule_LOADER",
mimeType = "text/x-java",
extension = {"Extention", "of", "blurJava", "code"})
@DataObject.Registration(
    mimeType = "text/x-java",
iconBase = "ru/ptahi/blurjava/actionDraftConsole2.gif",
displayName = "#LBL_BlurJavaModule_LOADER",
position = 300)
@ActionReferences({
    @ActionReference(
        path = "Loaders/text/x-java/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.OpenAction"),
    position = 100,
    separatorAfter = 200),
    @ActionReference(
        path = "Loaders/text/x-java/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.CutAction"),
    position = 300),
    @ActionReference(
        path = "Loaders/text/x-java/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.CopyAction"),
    position = 400,
    separatorAfter = 500),
    @ActionReference(
        path = "Loaders/text/x-java/Actions",
    id =
    @ActionID(category = "Edit", id = "org.openide.actions.DeleteAction"),
    position = 600),
    @ActionReference(
        path = "Loaders/text/x-java/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.RenameAction"),
    position = 700,
    separatorAfter = 800),
    @ActionReference(
        path = "Loaders/text/x-java/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.SaveAsTemplateAction"),
    position = 900,
    separatorAfter = 1000),
    @ActionReference(
        path = "Loaders/text/x-java/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.FileSystemAction"),
    position = 1100,
    separatorAfter = 1200),
    @ActionReference(
        path = "Loaders/text/x-java/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.ToolsAction"),
    position = 1300),
    @ActionReference(
        path = "Loaders/text/x-java/Actions",
    id =
    @ActionID(category = "System", id = "org.openide.actions.PropertiesAction"),
    position = 1400)
})
public class BlurJavaModuleDataObject extends MultiDataObject {

    public BlurJavaModuleDataObject(FileObject pf, MultiFileLoader loader) throws DataObjectExistsException, IOException {
        super(pf, loader);
        registerEditor("text/x-java", true);
//        BlurJavaMouseInfo.startMouseParse();      
    }

    @Override
    protected int associateLookup() {
        return 1;
    }

    @MultiViewElement.Registration(
        displayName = "LBL_BlurJavaModule_EDITOR",
    iconBase = "ru/ptahi/blurjava/actionDraftConsole2.gif",
    mimeType = "text/x-java",
    persistenceType = TopComponent.PERSISTENCE_ONLY_OPENED,
    preferredID = "BlurJavaModule",
    position = 1000)
    @Messages("LBL_BlurJavaModule_EDITOR=Source")
    public static MultiViewEditorElement createEditor(Lookup lkp) {
        JFrame frame = (JFrame) WindowManager.getDefault().getMainWindow();
        java.awt.Container oldContentPane = frame.getContentPane();
        JLayer<java.awt.Container> newContentPane = new JLayer<>(oldContentPane, new MYLAYERUI());
        frame.setContentPane(newContentPane);
        MultiViewEditorElement mvee = new MultiViewEditorElement(lkp);
//        BlurJavaMouseInfo.setMvee(mvee);
        return mvee;
    }
}

class MYLAYERUI extends LayerUI<java.awt.Container> {

    private BufferedImage mOffscreenImage, destOffscreenImage;
    private BufferedImageOp mOperation;
//    PApplet pa = new PApplet();
//    private BufferedImage image = null;
//    String currentDir = new File("").getAbsolutePath();
//    private PImage pimg = pa.loadImage(currentDir + "/data/fonnew.jpg"),
//            pimg1 = pa.loadImage(currentDir + "/data/fonnew1.jpg"),
//            blackpoint = pa.loadImage(currentDir + "/data/blackpoint.jpg");
//    
    int bMouseX, bMouseY;
    IplImage srcImg, destImg ;
    public MYLAYERUI() {

    }

    BufferedImage asd;
    Graphics2D asdg;
    Timer t = new Timer();
    @Override
    public void paint(Graphics g, JComponent c) {
        int w = c.getWidth();
        int h = c.getHeight();

        if (w == 0 || h == 0) {
            return;
        }
        
        if (asd == null) {
            asd =  new BufferedImage(
                        new ComponentColorModel(
                                ColorSpace.getInstance(ColorSpace.CS_sRGB),
                                new int[] { 32 },
                                true,
                                true,
                                ComponentColorModel.TRANSLUCENT,
                                DataBuffer.TYPE_INT
                                ),
                       Raster.createInterleavedRaster(
                                DataBuffer.TYPE_INT,
                                w,
                                h,
                                w,
                                0,
                                new int[] { 2, 1, 0, 3 },
                                null
                                ),
                        false,
                        new Hashtable<Object, Object>()
                    );
            asdg = asd.createGraphics();
        }

        // Only create the offscreen image if the one we have
        // is the wrong size.
        if (mOffscreenImage == null
                || mOffscreenImage.getWidth() != w
                || mOffscreenImage.getHeight() != h) {
            mOffscreenImage = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
            destOffscreenImage = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
//           JavaCVCL.createCLGLImageFrom()
            CLContext context = CLContext.create();
            out.println("created " + context);
         
            srcImg = IplImage.create(w,h,IPL_DEPTH_8U,3);
            destImg = IplImage.create(w,h,IPL_DEPTH_8U,3);
        }

        Graphics2D ig2 = mOffscreenImage.createGraphics();
        ig2.setClip(g.getClip());
        super.paint(ig2, c);
        t.reset();
        super.paint(asdg, c);
        System.out.println("asdg: " + t.diffAndReset());
        ig2.dispose();
        t.reset();
        
        
        
                
        srcImg.copyFrom(mOffscreenImage);
        System.out.println("copyFrom: " + t.diffAndReset());
       
//        destImg = srcImg.clone();
//        BufferedImage dest = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        
        t.reset();
        GaussianBlur(srcImg, destImg, new CvSize(0, 0), 3d, 3d, BORDER_DEFAULT);
        System.out.println("GaussianBlur: " + t.diffAndReset());
        destImg.copyTo(destOffscreenImage);
        System.out.println("destImg.copyTo: " + t.diffAndReset());
        
//        cv.allocate((int) w, (int) h);
//        cv.copy(new PImage(mOffscreenImage));         //  Copies trailsImg into OpenCV buffer so we can put some effects on it
//        cv.blur(OpenCV.BLUR, 6);
//        Image img = cv.image().getImage();
//        
////        
////        
////        Image img = mOperation.filter(mOffscreenImage,null);
//        BufferedImage off_Image = new BufferedImage((int) w, (int) h, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g2_black = off_Image.createGraphics();
//        g2_black.setColor(Color.WHITE);
//        g2_black.fillRect(0, 0, (int) w, (int) h);
//        
//        g2_black.drawImage(blackpoint.getImage(), bMouseX - 50, bMouseY - 50, null);
//
//        pimg = new PImage(off_Image);
//        pimg1 = new PImage(off_Image);
//        pimg1.filter(PApplet.INVERT);
//        PImage tmpimg = new PImage(img);
//        tmpimg.mask(pimg);
//
//        PImage tmpimg1 = new PImage(mOffscreenImage);
//        tmpimg1.mask(pimg1);
//        tmpimg.blend(tmpimg1, 0, 0, (int) w, (int) h, 0, 0, (int) w, (int) h, PApplet.BLEND);
//
////        g.drawImage(tmpimg.getImage(), 0, 0, null);
//        
//        opencv_core.IplImage imgA = cvLoadImage(
//                "C:\\Documents and Settings\\coglab\\My Documents\\NetBeansProjects\\JavaApplication4\\image0.png",
//                CV_LOAD_IMAGE_GRAYSCALE);
        Graphics2D g2 = (Graphics2D) g;
//        g2.drawImage(mOffscreenImage, mOperation, 0, 0);
    //        g2.drawImage(tmpimg.getImage(), 0, 0, null);
//        g2.drawImage(imgA.getBufferedImage(), 0, 0, null);
        
        Point mousePoint = MouseInfo.getPointerInfo().getLocation();
        SwingUtilities.convertPointFromScreen(mousePoint, c);
        bMouseX = mousePoint.x;
        bMouseY = mousePoint.y;
        
        System.out.println("drawImage: " + t.diffAndReset());
        g2.drawImage(destOffscreenImage, 0, 0, null);
        g2.setColor(Color.red);
        g2.fillRect(bMouseX, bMouseY, 20, 20);
        System.out.println("fillRect: " + t.diffAndReset());
    }

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        // enable mouse motion events for the layer's subcomponents
        ((JLayer) c).setLayerEventMask(AWTEvent.MOUSE_MOTION_EVENT_MASK);
    }

    @Override
    protected void processMouseMotionEvent(MouseEvent e, JLayer<? extends java.awt.Container> l) {
        super.processMouseMotionEvent(e, l);
//        bMouseX = e.getX();
//        bMouseY = e.getY();
        
//        Point mousePoint = MouseInfo.getPointerInfo().getLocation();
//        SwingUtilities.convertPointFromScreen(mousePoint, l);
//        bMouseX = mousePoint.x;
//        bMouseY = mousePoint.y;
        l.repaint();
    }
    // overridden method which catches MouseMotion events
//    public void eventDispatched(AWTEvent e, JLayer<? extends JComponent> l) {
//        System.out.println("AWTEvent detected: " + e);
//    }

    @Override
    public void eventDispatched(AWTEvent e, JLayer<? extends java.awt.Container> l) {
        super.eventDispatched(e, l);
//        PointerInfo info = MouseInfo.getPointerInfo();
//        bMouseX = info.getLocation().x;
//        bMouseY = info.getLocation().y;
        
    }
    
    

    public void uninstallUI(JComponent c) {
        super.uninstallUI(c);
        // reset the layer event mask
        ((JLayer) c).setLayerEventMask(0);
    }
};


/* [LGPL] Copyright 2010, 2011 Gima

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
/**
 * Differential timer.
 */
class Timer {
        
        private long lastTick;

        public Timer() {
                lastTick = -1;
        }
        
        /**
         * Retrieve the difference from last reset.
         * 
         * @return Difference in milliseconds since last reset. If last call wasn't made, returns 0
         */
        public long diff() {
                
                long retDiff;
                
                if (lastTick == -1) {
                        retDiff = 0;
                }
                else {
                        retDiff = System.currentTimeMillis() - lastTick;
                }
                
                return retDiff;
        }
        
        /**
         * Retrieve the difference from last reset and then reset.
         * 
         * @return Difference in milliseconds since last reset. If last call wasn't made, returns 0
         */
        public long diffAndReset() {
                long retDiff = diff();
                reset();
                return retDiff;
        }
        
        /**
         * Reset to current timer.
         */
        public void reset() {
                lastTick = System.currentTimeMillis();
        }
        
}

/* [LGPL] Copyright 2010, 2011 Gima

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

class ImageUtils {
        
        public static final int[] pixelOffsets_BGR;
        public static final int[] pixelOffsets_BGRA;
        public static final int[] pixelOffsets_GRAY;
        
        public static final int[] bpp_8;
        public static final int[] bpp_24;
        public static final int[] bpp_32;
        
        public static final ColorSpace colorSpace_sRGB;
        public static final ColorSpace colorSpace_GRAY;
        
        public static final ComponentColorModel colorModel_Alpha32BPP;
        public static final ComponentColorModel colorModel_24BPP;
        public static final ComponentColorModel colorModel_8BPP;
        
        static {
                pixelOffsets_BGR = new int[] { 2, 1, 0 };
                pixelOffsets_BGRA = new int[] { 2, 1, 0, 3 };
                pixelOffsets_GRAY = new int[] { 0 };
                
                bpp_8 = new int[] { 8 };
                bpp_24 = new int[] { 8, 8, 8 };
                bpp_32 = new int[] { 8, 8, 8, 8 };
                
                colorSpace_sRGB = ColorSpace.getInstance(ColorSpace.CS_sRGB);
                colorSpace_GRAY = ColorSpace.getInstance(ColorSpace.CS_GRAY);

                colorModel_Alpha32BPP = createComponentColorModel(
                                colorSpace_sRGB,
                                bpp_32,
                                true,
                                DataBuffer.TYPE_BYTE
                                );
                
                colorModel_24BPP = createComponentColorModel(
                                colorSpace_sRGB,
                                bpp_24,
                                false,
                                DataBuffer.TYPE_BYTE
                                );
                
                colorModel_8BPP = createComponentColorModel(
                                colorSpace_GRAY,
                                bpp_8,
                                false,
                                DataBuffer.TYPE_BYTE
                                );
        }
        
        /*
         * common functions
         */
        
        public static ComponentColorModel createComponentColorModel(ColorSpace colosSpace, int[] bits, boolean useAlpha, int dataType) {
                return new ComponentColorModel(
                                colosSpace,
                                bits,
                                useAlpha,
                                useAlpha,
                                useAlpha ? ComponentColorModel.TRANSLUCENT : ComponentColorModel.OPAQUE,
                                dataType
                                );
        }

        public static WritableRaster createInterleavedRaster(int width, int height, int bytesPerPixel, int[] pixelOffsets) {

                return Raster.createInterleavedRaster(
                                DataBuffer.TYPE_BYTE,
                                width,
                                height,
                                width * bytesPerPixel,
                                bytesPerPixel,
                                pixelOffsets,
                                null
                                );
        }

        public static BufferedImage createBufferedImage(ColorModel colorModel, WritableRaster writableRaster) {
                return new BufferedImage(
                                colorModel,
                                writableRaster,
                                false,
                                new Hashtable<Object, Object>()
                                );
        }

}