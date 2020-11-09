package Parquet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Line extends Sketch {
    public Logger logger = LogManager.getLogger(this.getClass().getName());

    private Point p1;
    private Point p2;

    private boolean handles = true;

    public Line(float x1, float y1, float x2, float y2) {
        this.p1 = new Point(x1, y1);
        this.p2 = new Point(x2, y2);
    }

    public Line(float x1, float y1, float x2, float y2, boolean handles) {
        this.p1 = new Point(x1, y1);
        this.p2 = new Point(x2, y2);
        this.handles = handles;
    }

    public void draw() {
        s.line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        if(handles) {
            p1.draw();
            p2.draw();
        }
    }

    public Point dragCheck() {
        if(p1.dragCheck() != null) {
            return p1.dragCheck();
        }
        if(p2.dragCheck() != null) {
            return p2.dragCheck();
        }
        return null;
    }

    public Point getP1() {
        return p1;
    }

    public Point getP2() {
        return p2;
    }

    public class Point {
        private float x;
        private float y;
        private final float size = 5;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public void setX(float x) {
            this.x = x;
        }

        public void setY(float y) {
            this.y = y;
        }

        public Point scale(Drawing drawing) {
            return new Point((x - drawing.getX())/drawing.getSize(), (y - drawing.getY())/drawing.getSize());
        }

        public void draw() {
            s.noStroke();
            if(s.mouseX > x - size && s.mouseX < x + size && s.mouseY > y - size && s.mouseY < y + size) {
                s.fill(100, 100, 255);
            }
            else {
                s.fill(50, 50, 255);
            }
            s.rect(x - size / 2, y - size / 2, size, size);
        }

        public Point dragCheck() {
            if(s.mouseX > x - size && s.mouseX < x + size && s.mouseY > y - size && s.mouseY < y + size) {
                return this;
            }
            return null;
        }
    }
}