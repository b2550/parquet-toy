package Parquet;

import com.sun.tools.doclets.formats.html.resources.standard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import processing.core.PApplet;

import java.util.ArrayList;

public class Drawing extends Sketch {
    public Logger logger = LogManager.getLogger(this.getClass().getName());

    public ArrayList<Line> lines = new ArrayList<>();
    private Line.Point handle;

    private int x;
    private int y;
    private int size;

    private boolean terminate;
    private int startX;
    private int startY;
    private int endX;
    private int endY;

    public boolean lock = false;

    public Drawing(int x, int y, int size) {
        this.terminate = false;
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public void draw() {
        s.stroke(255, 0 , 0);
        s.fill(0);
        s.rect(x, y, size, size);
        for(Line line : lines) {
            s.stroke(255);
            line.draw();
        }
        if(mouseOver()) {
            s.fill(255);
            s.text(String.format("%.3f, %.3f", (float)(s.mouseX - x) / size, (float)(s.mouseY - y) / size), s.mouseX, s.mouseY);

            if(terminate && !lock) {
                s.stroke(255, 0, 255);
                if(s.keyPressed && s.keyCode == s.SHIFT) {
                    float angle = s.degrees(s.atan2(s.mouseY - startY, s.mouseX - startX));
                    if((angle < 30 && angle > 0) || (angle < 180 && angle > 150) || (angle > -30 && angle < 0) || (angle > -180 && angle < -150)) {
                        endX = s.mouseX;
                        endY = startY;
                    }
                    else if((angle > 60 && angle < 120) || (angle < -60 && angle > -120)) {
                        endX = startX;
                        endY = s.mouseY;
                    }
                    else if(angle > 30 && angle < 60) {
                        endX = startX + s.max(s.mouseX - startX, s.mouseY - startY);
                        endY = startY + s.max(s.mouseX - startX, s.mouseY - startY);
                    }
                    else if(angle > 120 && angle < 150) {
                        endX = startX - s.max(s.mouseX - startX, s.mouseY - startY);
                        endY = startY + s.max(s.mouseX - startX, s.mouseY - startY);
                    }
                    else if(angle < -30 && angle > -60) {
                        endX = startX + s.max(s.mouseX - startX, s.mouseY - startY);
                        endY = startY - s.max(s.mouseX - startX, s.mouseY - startY);
                    }
                    else if(angle < -120 && angle > -150) {
                        endX = startX + s.max(s.mouseX - startX, s.mouseY - startY);
                        endY = startY + s.max(s.mouseX - startX, s.mouseY - startY);
                    }
                } else {
                    endX = s.mouseX;
                    endY = s.mouseY;
                }
                s.line(startX, startY, endX, endY);
            }
        }
    }

    public boolean mouseOver() {
        return s.mouseX > x && s.mouseY > y && s.mouseX < x + size && s.mouseY < y + size;
    }

    public void mousePressed() {
        if(mouseOver()) {
            switch (s.mouseButton) {
                case PApplet.LEFT:
                    for (Line line : lines) {
                        if (line.dragCheck() != null) {
                            logger.debug("Dragging handle");
                            this.handle = line.dragCheck();
                            break;
                        }
                    }
                    if (!terminate && handle == null && !lock) {
                        logger.debug("Drawing line");
                        startX = s.mouseX;
                        startY = s.mouseY;
                        terminate = true;
                    }
                    break;
                case PApplet.RIGHT:
                    if (terminate) {
                        logger.debug("Cancel drawing line");
                        terminate = false; // Cancel drawing a new boundary
                    } else if (lines.size() > 0 && !lock) {
                        logger.debug("Removing last line");
                        lines.remove(lines.size() - 1);
                    }
                    break;
            }
        }
    }

    public void mouseDragged() {
        if(mouseOver()) {
            if (handle != null) {
                handle.setX(s.mouseX);
                handle.setY(s.mouseY);
            }
        }
    }

    public void mouseReleased() {
        if(terminate && !lock) {
            logger.debug("Add new line ({}, {}) -> ({}, {})", startX, startY, startX + size, startY + size);
            lines.add(new Line(startX, startY, endX, endY));
            terminate = false;
        }
        else if (handle != null) {
            logger.debug("End dragging handle");
            handle = null;
        }
    }

    public void keyPressed() {
        if (mouseOver() && s.keyCode == s.BACKSPACE) {
            reset();
        }
    }

    public void reset() {
        logger.debug("Cleared drawing");
        lines.clear();
        lock = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }
}
