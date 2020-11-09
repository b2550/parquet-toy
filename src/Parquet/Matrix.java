package Parquet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class Matrix extends Sketch {
    public Logger logger = LogManager.getLogger(this.getClass().getName());

    Drawing drawing;

    int x;
    int y;
    int size;
    int width;
    int height;

    private Frame[][] matrix;

    public Matrix(Drawing drawing, int x, int y, int size, int width, int height) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.drawing = drawing;
        this.width = width;
        this.height = height;
        this.matrix = new Frame[width][height];
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                matrix[i][j] = new Frame(drawing, x + size * i, y + size * j, size);
            }
        }
    }

    public void draw() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                matrix[i][j].draw();
            }
        }
    }

    public void reset() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                s.stroke(255);
                matrix[i][j].reset();
            }
        }
    }

    public void setF1() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                matrix[i][j].setF1();
            }
        }
    }

    public void setF2() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                matrix[i][j].setF2();
            }
        }
    }

    public void setOffsetHorz(float p) {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
//                matrix[i][j].setInterpolation((p+(1f/width)*i)%1f);
                matrix[i][j].setInterpolation((1f/width) * (width - s.abs((i*p) % (2*width) - width)));
            }
        }
    }

    public void setOffsetVert(float p) {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
//                matrix[i][j].setInterpolation((p+(1f/height)*j)%1f);
                matrix[i][j].setInterpolation((1f/height) * (height - s.abs((j*p) % (2*height) - height)));
            }
        }
    }

    public void setOffsetOrdered(float p) {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
//                matrix[i][j].setInterpolation((p+(1f/(width*height))*(count))%1f);
                matrix[i][j].setInterpolation((1f/(width*height)) * ((width*height) - s.abs(((i*j)*p) % (2*(width*height)) - (width*height))));
            }
        }
    }
}
