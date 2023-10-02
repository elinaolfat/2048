import java.util.Scanner;

public class Main2048 {
   public static void main(String[] args){
      GameSystem gs = new GameSystem();
      Displayer disp = new Displayer();
      Scanner input = new Scanner(System.in);
      int directionNum=0;
      gs.SetCurrPlayer(0);//starts with player 0
      
      System.out.println("DIRECTION GUIDE:\n  a - left\n  w - up\n  d - right\n  s - down\n");
      //loop makes sure game continues unless no moves left or winning value has been reached
      while(true){
        gs.RandBlock();//randomizes a new block
        disp.PrintGrid(gs.GetGrid());//prints grid with latest move and newest randomized block
        disp.PrintScores(gs.GetPlayer(0),gs.GetPlayer(1));//prints total score of the players
        
        //after moving, checks if the winning value is among the blocks, if yes, declares winner and ends game
        if(gs.CheckWinner()==true){
          gs.SwitchPlayer();
          System.out.println("Winning value has been reached! Player "+gs.GetCurrPlayer().GetId()+" wins.");
          break;
        }
        System.out.print("Player "+gs.GetCurrPlayer().GetId()+" turn: ");
        
        //checks validity of the entered direction, turns the directions into strings
        while(true){
          String direction = input.nextLine();
          direction = direction.toLowerCase();//to not be case sensitive
          if(direction.equals("a")){
            directionNum = 1;
            break;
          }
          else if(direction.equals("d")){
            directionNum = 2;
            break;
          }
          else if(direction.equals("w")){
            directionNum = 3;
            break;
          }
          else if(direction.equals("s")){
            directionNum = 4;
            break;
          }
          else{
            System.out.println("Please enter a valid direction: ");
          }
        }
        
        //if grid is full and no blocks were combined in the last move (resulting in value of zero), it declares the player as loser and ends game
        if(gs.IsGridFull()==true){
          if(gs.Move(directionNum)==0){
            System.out.println("The grid is full! Player "+gs.GetCurrPlayer().GetId()+" loses.");
            break;
          }
        }
        
        //moves the blocks and says how many points were received
        System.out.println("Player "+gs.GetCurrPlayer().GetId()+" got "+gs.Move(directionNum)+" in this round.");
        gs.SwitchPlayer();
      }
   }
}