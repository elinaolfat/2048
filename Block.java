/* Represents each block in the grid */
public class Block {
  private int val;
  private boolean isNew;
  
  /* Constructing a Block object with a specified
   * value.
   * @param val - The value of the block */
  public Block(int val) {
    this.val = val;
    this.isNew = true; //as soon as block is created, it sets the IsNew to true
  }
  
  /* Get the value of the block
   * @return - The value of the block it contains */
  public int GetVal() {
    return this.val;
  }
  
  /* Set the value of the block
   * @param val - The new value that the block will            
   *              use. */
  public void SetVal(int val) {
    this.val = val;
  }
  
  public boolean GetIsNew(){
    return this.isNew;
  }
  
 /* @param val - The value you want the block to be
  *              Set to true to indicate that the block is new
  *              Set to false to indicate that the block is old */
  public void SetIsNew(boolean val){
    this.isNew = val;
  }
}