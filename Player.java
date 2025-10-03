public class Player {
   private int x, y;
   public Player(int x, int y) {
       this.x = x;
       this.y = y;
   }
   public void move(int dx) {
       x += dx;
       if (x < 0)
       	x = 0;
       if (x > 560)
       	x = 560;
   }
   public int getX() {
   	return x;
   }
   public int getY() {
   	return y;
   }
}

