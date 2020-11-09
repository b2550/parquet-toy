package Parquet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Frame extends Sketch {
    public Logger logger = LogManager.getLogger(this.getClass().getName());

    Drawing drawing;

    int x;
    int y;
    int size;

    private ArrayList<Line> f1 = new ArrayList<>();
    private ArrayList<Line> f2 = new ArrayList<>();

    private float interpolation = 0;
    private boolean renderable;

    public Frame(Drawing drawing, int x, int y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.renderable = false;
        this.drawing = drawing;
    }

    public void reset() {
        renderable = false;
        f1.clear();
        f2.clear();
    }

    public void setF1() {
        f1.clear();
        for(Line line : drawing.lines) {
            f1.add(rescale(drawing, line));
        }
        renderable = f2.size() > 0;
    }

    public void setF2() {
        f2.clear();
        for(Line line : drawing.lines) {
            f2.add(rescale(drawing, line));
        }
        renderable = f1.size() > 0;
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

    public void setInterpolation(float p) {
        if(p >= 0 && p <= 1) {
            interpolation = p;
        } else {
            logger.error("Interpolation out of bounds");
        }
    }

    public void drawDebug() {
        if(renderable) {
            s.stroke(0, 255 , 0);
            s.fill(0);
            s.rect(x, y, size, size);
            for (int i = 0; i < f1.size(); i++) {
                Line l1 = f1.get(i);
                Line l2 = f2.get(i);
                new Line(
                        s.lerp(l1.getP1().getX(), l2.getP1().getX(), interpolation),
                        s.lerp(l1.getP1().getY(), l2.getP1().getY(), interpolation),
                        s.lerp(l1.getP2().getX(), l2.getP2().getX(), interpolation),
                        s.lerp(l1.getP2().getY(), l2.getP2().getY(), interpolation), false
                        ).draw();
            }
        } else {
            s.stroke(255, 0 , 0);
            s.fill(0);
            s.rect(x, y, size, size);
        }
    }

    public void draw() {
        if(renderable) {
            for (int i = 0; i < f1.size(); i++) {
                Line l1 = f1.get(i);
                Line l2 = f2.get(i);
                new Line(
                        s.lerp(l1.getP1().getX(), l2.getP1().getX(), interpolation),
                        s.lerp(l1.getP1().getY(), l2.getP1().getY(), interpolation),
                        s.lerp(l1.getP2().getX(), l2.getP2().getX(), interpolation),
                        s.lerp(l1.getP2().getY(), l2.getP2().getY(), interpolation), false
                ).draw();
            }
        }
    }

    public Line rescale(Drawing drawing, Line line) {
        Line.Point p1 = line.getP1().scale(drawing);
        Line.Point p2 = line.getP2().scale(drawing);
        return new Line(p1.getX() * size + x,
                 p1.getY() * size + y,
                 p2.getX() * size + x,
                 p2.getY() * size + y);
    }
}
