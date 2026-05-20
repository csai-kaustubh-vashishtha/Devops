package Capg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class App extends JPanel implements ActionListener, KeyListener {

    int width = 600, height = 600;
    int unit = 25;
    int totalUnits = (width * height) / (unit * unit);

    int[] x = new int[totalUnits];
    int[] y = new int[totalUnits];

    int bodyParts = 3;
    int appleX, appleY;
    char direction = 'R';
    boolean running = false;

    Timer timer;
    Random random = new Random();

    public App() {

        setPreferredSize(new Dimension(width, height));
        setBackground(Color.black);
        setFocusable(true);
        addKeyListener(this);

        startGame();
    }

    public void startGame() {

        newApple();

        running = true;

        timer = new Timer(100, this);

        timer.start();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        draw(g);
    }

    public void draw(Graphics g) {

        if (running) {

            g.setColor(Color.red);

            g.fillOval(appleX, appleY, unit, unit);

            for (int i = 0; i < bodyParts; i++) {

                if (i == 0)
                    g.setColor(Color.green);
                else
                    g.setColor(new Color(45, 180, 0));

                g.fillRect(x[i], y[i], unit, unit);
            }

            g.setColor(Color.white);

            g.setFont(new Font("Arial", Font.BOLD, 25));

            g.drawString("Score: " + (bodyParts - 3), 20, 30);

        } else {

            gameOver(g);
        }
    }

    public void newApple() {

        appleX = random.nextInt(width / unit) * unit;
        appleY = random.nextInt(height / unit) * unit;
    }

    public void move() {

        for (int i = bodyParts; i > 0; i--) {

            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        switch (direction) {

            case 'U':
                y[0] -= unit;
                break;

            case 'D':
                y[0] += unit;
                break;

            case 'L':
                x[0] -= unit;
                break;

            case 'R':
                x[0] += unit;
                break;
        }
    }

    public void checkApple() {

        if (x[0] == appleX && y[0] == appleY) {

            bodyParts++;

            newApple();
        }
    }

    public void checkCollisions() {

        for (int i = bodyParts; i > 0; i--) {

            if ((x[0] == x[i]) && (y[0] == y[i])) {

                running = false;
            }
        }

        if (x[0] < 0)
            running = false;

        if (x[0] >= width)
            running = false;

        if (y[0] < 0)
            running = false;

        if (y[0] >= height)
            running = false;

        if (!running) {

            timer.stop();
        }
    }

    public void gameOver(Graphics g) {

        g.setColor(Color.red);

        g.setFont(new Font("Arial", Font.BOLD, 50));

        g.drawString("Game Over", 160, 280);

        g.setColor(Color.white);

        g.setFont(new Font("Arial", Font.BOLD, 25));

        g.drawString("Score: " + (bodyParts - 3), 240, 330);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {

            move();

            checkApple();

            checkCollisions();
        }

        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {

            case KeyEvent.VK_LEFT:

                if (direction != 'R')
                    direction = 'L';

                break;

            case KeyEvent.VK_RIGHT:

                if (direction != 'L')
                    direction = 'R';

                break;

            case KeyEvent.VK_UP:

                if (direction != 'D')
                    direction = 'U';

                break;

            case KeyEvent.VK_DOWN:

                if (direction != 'U')
                    direction = 'D';

                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Snake Game");

        App game = new App();

        frame.add(game);

        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }
}