import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;

//This panel is used to draw the balls, which are created by clicking the mouse and save their collision coordinates to a file.

public class Panel extends JPanel {
    private final int DELAY = 16;
    private final ArrayList<Ball> listOfBalls;
    private final ArrayList<Ball.Speed> listOfSpeed;
    private int size = 20;
    private Timer timer;

    //dla 30fps -> 1s/30 = 0,033s
    public Panel() {
        listOfBalls = new ArrayList<>();
        listOfSpeed = new ArrayList<>();
        setBackground(Color.BLACK);
        addMouseListener(new Event());
        addMouseWheelListener(new Event());
        timer = new Timer(DELAY, new Event());
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Ball k : listOfBalls) {
            g.setColor(k.color);
            g.drawOval(k.x, k.y, k.size, k.size);
        }
        g.setColor(Color.YELLOW);
        g.drawString(Integer.toString(listOfBalls.size()), 40, 40);
    }

    private class Event implements MouseListener,
            ActionListener, MouseWheelListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            listOfBalls.add(new Ball(e.getX(), e.getY(), size));
            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            for (Ball k : listOfBalls) {
                k.xspeed = listOfSpeed.get(listOfBalls.indexOf(k)).x;
                k.yspeed = listOfSpeed.get(listOfBalls.indexOf(k)).y;
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            for (Ball k : listOfBalls) {
                k.xspeed = 0;
                k.yspeed = 0;
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            for (Ball k : listOfBalls) {
                k.update();
            }
            repaint();
        }

        @Override
        public void mouseWheelMoved(MouseWheelEvent e) {
            size -= e.getWheelRotation();
        }
    }

    private class Ball {
        File file = new File("plik.txt");
        private final int MAX_SPEED = 5;
        public int x, y, size, xspeed, yspeed;
        public Color color;

        public Ball(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
            color = new Color((float) Math.random(), (float)
                    Math.random(), (float) Math.random());
            do {
                xspeed = (int) (Math.random() * MAX_SPEED * 2 -
                        MAX_SPEED);

                yspeed = (int) (Math.random() * MAX_SPEED * 2 -
                        MAX_SPEED);

                listOfSpeed.add(new Speed(xspeed, yspeed));
            } while (xspeed == 0 && yspeed == 0);

        }

        public void update() {
            x += xspeed;
            y += yspeed;
            if (x <= 0 || x + size >= getWidth()) {
                xspeed = -xspeed;
            }
            if (y <= 0 || y + size >= getHeight()) {
                yspeed = -yspeed;
            }
            //collision
            for (Ball k : listOfBalls) {
                if (k != this) {
                    if (Math.sqrt(Math.pow(x - k.x, 2) + Math.pow(y - k.y, 2)) <= size + k.size) {
                        try {
                            FileWriter writer = new FileWriter(file, true);
                            writer.append(x + " " + y + " " + size + "\n" + k.x + " " + k.y + " " + k.size + "\n");
                            writer.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        xspeed = -xspeed;
                        yspeed = -yspeed;
                        k.xspeed = -k.xspeed;
                        k.yspeed = -k.yspeed;
                    }
                }
            }
        }

        private static class Speed {
            public int x, y;

            public Speed(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
    }
}
