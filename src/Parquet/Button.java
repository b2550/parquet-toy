package Parquet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Button extends Sketch {
    public Logger logger = LogManager.getLogger(this.getClass().getName());

    private int x;
    private int y;
    private float width;
    private int height = 30;
    private int lrMargin = 10;
    private int baselineOffset = 20;
    private String name;

    public Button(int x, int y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.width = s.textWidth(name) + this.lrMargin*2;
    }

    public void setName(String name) {
        this.name = name;
        this.width = s.textWidth(name) + this.lrMargin*2;
    }

    public void draw() {
        if(mouseOver() && s.mousePressed) {
            s.fill(25);
        } else if(mouseOver()) {
            s.fill(50);
        } else {
            s.fill(0);
        }
        s.stroke(255);
        s.rect(x, y, width, height);
        s.textSize(12);
        s.fill(255);
        s.text(name, x + lrMargin, y + baselineOffset);
    }

    public boolean mouseOver() {
        return s.mouseX > x && s.mouseY > y && s.mouseX < x + width && s.mouseY < y + height;
    }

    public void mouseReleased(Supplier callback) {
        // Java 8 Only
        if(mouseOver()) {
            callback.get();
        }
    }
}
