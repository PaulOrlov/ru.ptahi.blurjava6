/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.ptahi.blurjava;

import com.sun.source.tree.ClassTree;
import com.sun.source.util.TreePath;
import com.sun.source.util.TreePathScanner;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import org.netbeans.api.editor.EditorRegistry;
import org.netbeans.api.java.source.CompilationInfo;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Exceptions;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

/**
 *
 * @author paulorlov
 */
public class MemberVisitor extends TreePathScanner<Void, Void> {

    private CompilationInfo info;
    JTextComponent editor;
    String fileName = "log11.txt";

    public MemberVisitor(CompilationInfo info, JTextComponent editor) {
        this.info = info;
        this.editor = editor;
    }

    public synchronized void mOne(Point point) {
        try {
            if (editor.getDocument() == info.getDocument()) {
                int dot = editor.viewToModel(point);
                
                TreePath tp = info.getTreeUtilities().pathFor(dot);
                Element el = info.getTrees().getElement(tp);

                String textLog = "";
                if (el == null) {
                    String text = "";
                    int k = 0, t = 0;
                    try {
                        while (!editor.getText(dot - k, 1).contains(" ")) {
                            k++;
                        }
                        while (!editor.getText(dot + t, 1).contains(" ")) {
                            t++;
                        }
                        text = editor.getText(dot - k, t + k);

                    } catch (BadLocationException ex) {
                        Exceptions.printStackTrace(ex);
                    }
                    textLog = "dot : " + dot + "; NoneEl : " + text + "; Kind : none; Time : " ;//+ me.getWhen();
                } else {
                    textLog = "dot : " + dot + " SimpleName : " + el.getSimpleName() + "; Kind : " + el.getKind() + "; Time : ";// + me.getWhen();
                }
                FileWriter out;
                try {
                    out = new FileWriter(fileName, true);
                    out.write(textLog + "\n");
                    out.close();
                } catch (IOException ex) {
                    Exceptions.printStackTrace(ex);
                }
            }
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
