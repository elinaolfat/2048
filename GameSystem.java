/* Manages the game and all of its attributes */
public class GameSystem {
   public final static int DEFAULT_GRID_HEIGHT =    4;
   public final static int DEFAULT_GRID_WIDTH  =    4;
   public final static int DEFAULT_WINNING_VAL = 2048;
   public final static int LEFT = 1;
   public final static int RIGHT = 2;
   public final static int UP = 3;
   public final static int DOWN = 4;
   
   public Block[][] grid;                              // The grid of the game
   private Player[] allPlayer;                          // To keep track of the two players
   
   private int winningVal;                              // Value that must be reached to win the game.
   private Player currPlayer;                           // Keeps track of the current player
   
   //=================== CONSTRUCTOR =====================//
   /* The constructor to initialize the grid. */
   public GameSystem() {
     grid = new Block[DEFAULT_GRID_HEIGHT][DEFAULT_GRID_WIDTH];
     allPlayer = new Player[2];
     allPlayer[0] = new Player(0);
     allPlayer[1] = new Player(1);
     winningVal = DEFAULT_WINNING_VAL;
     currPlayer = null;
   }
  
   /* The constructor to customize the winning value.
    * @param winningVal - The value that the player must reach 
    *                     to win the game. */
   public GameSystem(int winningVal) {
     this.winningVal = winningVal;
   }
   
   //================== PRIVATE METHODS ====================//
   /* Method randomizes between the two given numbers only
    * uses Math.random to get numbers,
    * if the number matches either one of the entered numbers, it will return it
    * if not, it will enter loop again and repeat the procedure */
   private int GetTwoRandNum(int num1, int num2){
     while(true){
       int rand = (int)(Math.random()*100);
       if(rand==num1 || rand ==num2){
         return rand;
       }
     }
   }
   /* Method randomizes from the set of interval given to it
    * if the found number is between the range of the given numbers, it returns it
    * if not, enters loop and repeats procedure*/
   private int GetRandNumInterval(int min, int max){
     while(true){
       int rand = (int)(Math.random()*100);
       if(rand>=min && rand<=max){
         return rand;
       }
     }
   }
   /* Method is used in the Move emthod,
    * adds the new score to the players score, returns the amount added
    * type parameter is used to determine the direction of move, since it effects which block should turn null
    * saves value of block, so that it can return the value it before adding*/
   private int AddReturnScore(int row, int col, int val, String type){
     int temp = grid[row][col].GetVal();
     grid[row][col].SetVal(temp+temp);
     if(type.equals("LR")){ //for left/right
       grid[row][val] = null;
     }
     else{ //for up/down
       grid[val][col] = null;
     }
     this.currPlayer.SetScore(temp);
     return temp;
   }
   //================== PUBLIC METHODS ====================//
   /* Get the grid of the game
    * @return - The Block object 2D array */
   public Block[][] GetGrid() {
     return grid;
   }
   
   /* Randomize a block and its value (between 2 or 4) and place it 
    * in the grid */
   public void RandBlock() {
     for(int row = 0;row<grid.length;row++){
       for(int col = 0; col<grid[row].length;col++){
         //turns the IsNew value of all old blocks to false so only the newest one that's about to be created is true
         if(grid[row][col]!=null){ 
           grid[row][col].SetIsNew(false);
         }
       }
     }
     while(true){
       int row = this.GetRandNumInterval(0, DEFAULT_GRID_WIDTH-1);
       int col = this.GetRandNumInterval(0, DEFAULT_GRID_HEIGHT-1);
       if((grid[row][col]==null)){ //if randomized block is empty, it gives it a new randomized value
         grid[row][col] = new Block(this.GetTwoRandNum(2,4));
         break;
       }
     }
   }
   
   /* Move all the blocks to the specified direction
    * @param direction - The direction that the user wants to move to.
    *                    Refer to GameSystem global variables for direction
    *                    value. 
    * @return          - Returns the score (or points) that this move accumulated. 
    *                    (So the total score from this current move)*/
   public int Move(int direction) {
     int score = 0; //scores gained from this move will be added to this variable 
     
     if(direction == LEFT){
       //adding
       for(int row = 0; row<grid.length; row++){
         for(int repeate = 0; repeate<grid[row].length; repeate++){//goes over the same row until no more values can be added together
           //since each block is being checked with the block infront of it (in the direction of move), no need to start from the leftest bloc
           for(int col = 1; col<grid[row].length;col++){
             //val is the variable that will be constantly changing in order to make the checking of one block with other blocks in it's same row possible
             int val = col;
             while(grid[row][col-1]!=null && val<grid[row].length ){//checks if wall value is null, and if val is still within bounds
               //if current block isnt null, checks with the one infront of it, if match adds, if not it checks same block with another block infront of it
               if(grid[row][val]!=null && grid[row][col-1].GetVal()==grid[row][val].GetVal()){
                 score += this.AddReturnScore(row,col-1,val,"LR");
               }
               else if(grid[row][val]!=null){
                 break;
               }
               val++;
             }
           }
         }
       }
       
       //moving
       for(int row = 0; row<grid.length; row++){
         for(int col = 1; col<grid[row].length;col++){//starting from 1, because position 0 is the leftest value already and doesn't need to be moved
           int val = col;
           while(grid[row][val]!=null && grid[row][val-1]==null && val>=1){
             grid[row][val-1]= new Block(grid[row][val].GetVal()); //gives the block infront of it the value of the one behind it
             grid[row][val]=null;//makes the currrent block empty because its value has been moved forward
             if(val>1){
               val--;
             }
           }
         }
       }
     }
       
   //////////////////////////////////////////////////////////////////////////////////////////////////
     else if(direction == RIGHT){
       //adding
       for(int row = 0; row<grid.length; row++){
         for(int repeate = 0; repeate<grid[row].length; repeate++){
           for(int col = grid[row].length-2; col>=0;col--){
             int val = col;
             while(grid[row][col+1]!=null && val>=0){
               if(grid[row][val]!=null && grid[row][col+1].GetVal()==grid[row][val].GetVal()){
                 score += this.AddReturnScore(row,col+1,val,"LR");
               }
               else if(grid[row][val]!=null){
                 break;
               }
               val--;
             }
           }
         }
       }
       //moving
       for(int row = 0; row<grid.length; row++){
         for(int col = grid[row].length-2; col>=0;col--){
           int val = col;
           while(grid[row][val]!=null && grid[row][val+1]==null && val<grid[row].length-1){
             grid[row][val+1]= new Block(grid[row][val].GetVal());
             grid[row][val]=null;
             if(val<grid[row].length-2){
               val++;
             }
           }
         }
       }
     }
     //////////////////////////////////////////////////////////////////////////////////////////////////
     else if(direction == UP){
       //adding
       for(int repeate = 0; repeate<grid.length; repeate++){
         for(int row = 1; row<grid.length; row++){
           for(int col = 0; col<grid[row].length;col++){
             int val = row;
             while(grid[row-1][col]!=null && val<grid.length){
               if(grid[val][col]!=null && grid[row-1][col].GetVal()==grid[val][col].GetVal()){
                 score += this.AddReturnScore(row-1,col,val,"UD");
               }
               else if(grid[val][col]!=null){
                 break;
               }
               val++;
             }
           }
         }
       }
       //moving
       for(int row = 1; row<grid.length; row++){
         for(int col = 0; col<grid[row].length;col++){   
           int val = row;
           while(grid[val][col]!=null && grid[val-1][col]==null && val>=1){
             grid[val-1][col]= new Block(grid[val][col].GetVal());
             grid[val][col]=null;
             if(val>1){
               val--;
             }
           }
         }
       }
     }
    ////////////////////////////////////////////////////////////////////////////////////////////////// 
     else if(direction == DOWN){
       //adding
       for(int repeate = 0; repeate<grid.length; repeate++){
         for(int row = grid.length-2; row>=0; row--){
           for(int col = grid[row].length-1; col>=0;col--){
             int val = row;
             while(grid[row+1][col]!=null && val>=0){
               if(grid[val][col]!=null && grid[row+1][col].GetVal()==grid[val][col].GetVal()){
                 score += this.AddReturnScore(row+1,col,val,"UD");
               }
               else if(grid[val][col]!=null){
                 break;
               }
               val--;
             }
           }
         }
       }
       //moving
       for(int row = grid.length-2; row>=0; row--){
         for(int col = grid[row].length-1; col>=0;col--){
           int val = row;
           while(grid[val][col]!=null && grid[val+1][col]==null && val<grid.length-1){
             grid[val+1][col]= new Block(grid[val][col].GetVal());
             grid[val][col]=null;
             if(val<grid.length-2){
               val++;
             }
           }
         }
       }
     }
     return score;
   }
     
   /* Set who will be the player turn
    * @param playerId - The ID of the player */
   public void SetCurrPlayer(int playerId ) {
     this.currPlayer = allPlayer[playerId];
   }
   
   /* Get the Player who is currently his/her turn
    * @return - The Player object of the player who is currently
    *           his/her turn */
   public Player GetCurrPlayer() {
      return currPlayer;
   }
   
   /* Get the Player with the indicated player ID
    * @param playerId - The ID of the player 
    * @return         - The Player object of the player */
   public Player GetPlayer(int playerId ) {
      return allPlayer[playerId];
   }
   
   /* Switch player's turn. If it is player 0 turn, then it will
    * switch to player 1, and vice versa */
   public void SwitchPlayer() {
     switch(currPlayer.GetId()){
       case 0:
         currPlayer = allPlayer[1];
         break;
       case 1:
         currPlayer = allPlayer[0];
         break;
     }
   }
   
   /* Check if the player wins or not by reaching the 
    * specified winning value.
    * @return - True if at least one of the blocks is equals to the wining value. 
    *           False otherwise. */
   public boolean CheckWinner() {
     //checks all block to see if any is equal to winningVal
     for(int row = 0; row<grid.length; row++){
       for(int col = 0; col<grid[row].length; col++){
         if(grid[row][col]!= null && grid[row][col].GetVal() == this.winningVal){
           return true;
         }
       }
     }
     return false;
   }
   
   /* Check if the grid is full or not
    * @return - True if the grid has no more empty blocks
    *           False otherwise. */
   public boolean IsGridFull() {
     boolean result = true;
     for(int row = 0; row<grid.length; row++){
       for(int col = 0; col<grid[row].length; col++){
         if(grid[row][col]==null){
           result = false;
         }
       }
     }
     return result;
   }
}