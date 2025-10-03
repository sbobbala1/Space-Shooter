import javax.swing.JFrame;
public class GameLauncher {
	public static void main(String[] args) {
       JFrame frame = new JFrame("Space Bubble Shooter");
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(600, 800);
       frame.add(new GamePanel());
       frame.setVisible(true);
   }
}
