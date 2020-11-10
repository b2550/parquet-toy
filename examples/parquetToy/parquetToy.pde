public void settings() {
  Sketch.s = this;
  size(1480, 720);
}

boolean editMode = true;
int interpolateMode = 0;

Drawing d = new Drawing(10, 10, 700);
Matrix m;
Button setFrameBtn;
Button interpolateModeBtn;

float speed = 100;

int state = 0;

public void setup() {
  setFrameBtn = new Button(720, 10, String.format("Set Frame %d", state + 1));
  interpolateModeBtn = new Button(720, 50, String.format("Interpolation Mode %d", interpolateMode + 1));
  //        frame = new Frame(d, 320, 10, 100);
  m = new Matrix(d, 0, 0, 40, 37, 18);
}

public void draw() {
  background(255);
  if (interpolateMode == 0) {
    m.setOffsetHorz((1/speed) * (speed - abs(frameCount % (2*speed) - speed)));
  } else if (interpolateMode == 1) {
    m.setOffsetVert((1/speed) * (speed - abs(frameCount % (2*speed) - speed)));
  } else if (interpolateMode == 2) {
    m.setOffsetOrdered((1/speed) * (speed - abs(frameCount % (2*speed) - speed)));
  }
  stroke(0);
  m.draw();
  if (editMode) {
    d.draw();
    setFrameBtn.draw();
    interpolateModeBtn.draw();
  }
}

public void mousePressed() {
  if (editMode) {
    d.mousePressed();
  }
}

public void mouseDragged() {
  if (editMode) {
    d.mouseDragged();
  }
}

public void mouseReleased() {
  if (editMode) {
    d.mouseReleased();
    if (setFrameBtn.mouseOver()) {
      d.lock = true;
      if (state == 0) {
        m.setF1();
        state++;
      } else if (state == 1) {
        m.setF2();
        state--;
      }
      setFrameBtn.setName(String.format("Set Frame %d", state + 1));
    }
    if (interpolateModeBtn.mouseOver()) {
      interpolateMode = (interpolateMode + 1) % 3;
      interpolateModeBtn.setName(String.format("Interpolation Mode %d", interpolateMode + 1));
    }
  }
}

public void keyPressed() {
  if (editMode) {
    if (keyCode == BACKSPACE) {
      d.reset();
      m.reset();
      state = 0;
    }
  }
  if (key == ' ') {
    editMode = !editMode;
  }
}
