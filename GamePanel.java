import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener {
    private Player player;
    private ArrayList<Rectangle> bullets;
    private ArrayList<Rectangle> bubbles;
    private ArrayList<Point> stars;
    private Timer timer;
    private int score = 0;
    private int bubbleSpawnTimer = 0;

    public GamePanel() {
        setBackground(Color.BLACK);
        setFocusable(true);
        player = new Player(280, 750);
        bullets = new ArrayList<>();
        bubbles = new ArrayList<>();

        stars = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            int x = (int)(Math.random() * 600);
            int y = (int)(Math.random() * 800);
            stars.add(new Point(x, y));
        }

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) player.move(-15);
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) player.move(15);
                if (e.getKeyCode() == KeyEvent.VK_SPACE)
                    bullets.add(new Rectangle(player.getX() + 17, player.getY(), 6, 12));
            }
        });

        timer = new Timer(20, this);
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        for (Point star : stars) {
            star.y += 1;
            if (star.y > 800) {
                star.y = 0;
                star.x = (int)(Math.random() * 600);
            }
        }

        for (Rectangle b : bullets) b.y -= 10;
        bullets.removeIf(b -> b.y < 0);

        for (Rectangle bubble : bubbles) 
        	bubble.y += 3;

        bubbleSpawnTimer++;
        if (bubbleSpawnTimer > 50) {
            int x = (int) (Math.random() * 560);
            bubbles.add(new Rectangle(x, 0, 30, 30));
            bubbleSpawnTimer = 0;
        }

        ArrayList<Rectangle> hitBubbles = new ArrayList<>();
        for (Rectangle b : bullets) {
            for (Rectangle bubble : bubbles) {
                if (b.intersects(bubble)) {
                    hitBubbles.add(bubble);
                    score += 10;
                }
            }
        }
        bubbles.removeAll(hitBubbles);

        for (Rectangle bubble : bubbles) {
            if (bubble.intersects(new Rectangle(player.getX(), player.getY(), 40, 20))) {
                timer.stop();
                JOptionPane.showMessageDialog(this, "Game Over! Final Score: " + score);
                return;
            }
        }

        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        for (Point star : stars) {
            g.fillRect(star.x, star.y, 2, 2);
        }

        g.setColor(Color.CYAN);
        g.fillRect(player.getX(), player.getY(), 40, 20);

        g.setColor(Color.YELLOW);
        for (Rectangle b : bullets)
            g.fillRect(b.x, b.y, b.width, b.height);

        g.setColor(Color.GREEN);
        for (Rectangle bubble : bubbles)
            g.fillOval(bubble.x, bubble.y, bubble.width, bubble.height);

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 10, 20);
    }
}

