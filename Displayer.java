/* A class that handles all the printing of
 * the game. */
public class Displayer {
   public Displayer() {
   }
   
   /* Prints the grid and its content on the screen
    * @param grid - The Block object 2D array. */
   public void PrintGrid( Block[][] grid ) {
     System.out.println("\n----------------------------");
     for(int row = 0;row<grid.length;row++){
       for(int col = 0; col<grid[row].length;col++){
         if(grid[row][col]==null){
           System.out.print("      |");
         }
         else if(grid[row][col].GetIsNew()==true){//only the newest block will have a curly bracket around it
           System.out.printf("{%3d} |",grid[row][col].GetVal());
         }
         else{
           System.out.printf("%5d |",grid[row][col].GetVal());
         }
       }
       System.out.println("\n----------------------------");
     }
   }
   
   /* Prints the scores of the two players
    * @param p1 - First player's Player object
    * @param p2 - Second player's Player object */
   public void PrintScores( Player p1, Player p2 ) {
     System.out.println("============== SCORE ===============");
     System.out.printf("|PLAYER 0:%5d  |  PLAYER 1:%5d |",p1.GetScore(),p2.GetScore());
     System.out.println("\n====================================");
   }
}