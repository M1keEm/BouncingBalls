import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;


//This panel is used to display the balls, which are read from the collision file.

public class PanelTwo extends JPanel {
    private final int DELAY = 16;
    private ArrayList<Kula> listaKul;
    private ArrayList<Kula.Predkosc> listaPredkosci;
    private ArrayList<Kula> listaKolizji;
    private int size = 20;
    private Timer timer;

    //dla 30fps -> 1s/30 = 0,033s
    public PanelTwo() {
        listaKul = new ArrayList<>();
        listaKolizji = new ArrayList<>();
        listaKolizji = odczytKul();
        listaPredkosci = new ArrayList<>();
        setBackground(Color.BLACK);
        addMouseListener(new Event());
        timer = new Timer(DELAY, new Event());
        timer.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Kula k : listaKolizji) {
            g.setColor(new Color((float) Math.random(), (float)
                    Math.random(), (float) Math.random()));
            g.drawOval(k.x, k.y, k.size, k.size);
        }
        g.setColor(Color.YELLOW);
        g.drawString(Integer.toString(listaKolizji.size()), 40, 40);
    }

    private class Event implements MouseListener,
            ActionListener {
        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //listaKul.add(new Kula(e.getX(), e.getY(), size));
            //repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //for (Kula k : listaKul) {
            //k.xspeed = listaPredkosci.get(listaKul.indexOf(k)).x;
            //k.yspeed = listaPredkosci.get(listaKul.indexOf(k)).y;
            //}
        }

        @Override
        public void mouseExited(MouseEvent e) {
//            for (Kula k : listaKul) {
//                k.xspeed = 0;
//                k.yspeed = 0;
//            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//            for (Kula k : listaKul) {
//                k.update();
//            }
//            repaint();
        }

//        @Override
//        public void mouseWheelMoved(MouseWheelEvent e) {
//            size += e.getWheelRotation();
//        }
    }

    File plik = new File("plik.txt");

    public ArrayList<Kula> odczytKul() {
        ArrayList<String> linie = new ArrayList();
        ArrayList<Kula> listaKolizji = new ArrayList<>();
        try {
            String linia;
            BufferedReader fileReader = new BufferedReader(new FileReader(plik));
            while ((linia = fileReader.readLine()) != null) {
                linie.add(linia);
                String[] kuleczki = linia.split(" ");
                listaKolizji.add(new Kula(Integer.parseInt(kuleczki[0]), Integer.parseInt(kuleczki[1]), Integer.parseInt(kuleczki[2])));
            }
            fileReader.close();
            return listaKolizji;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private class Kula {

        private final int MAX_SPEED = 5;
        public int x, y, size, xspeed, yspeed;
        public Color color;

        public Kula(int x, int y, int size) throws FileNotFoundException {
            this.x = x;
            this.y = y;
            this.size = size;
            color = new Color((float) Math.random(), (float)
                    Math.random(), (float) Math.random());
            xspeed = 0;
            yspeed = 0;
        }

        private static class Predkosc {
            public int x, y;

            public Predkosc(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
    }
}
