import java.util.*;

class ratMaze{
 
static int max = 1000000;   
 
//returns numbers of ways destination can be reached
static int mazeObstacles(int n, int m, int[][] maze, int[][] dp) {
 for(int i=0; i<n ;i++){
      for(int j=0; j<m; j++){
          
          //base conditions
          if(i>0 && j>0 && maze[i][j]==-1){
            dp[i][j]=0;
            continue;
          }
          if(i==0 && j==0){
              dp[i][j]=1;
              continue;
          }
          
          //As we start from the destination we can either go left or upwards
          int up=0;
          int left = 0;
          
          if(i>0) 
            up = dp[i-1][j];
          if(j>0)
            left = dp[i][j-1];
            
          dp[i][j] = up+left;
      }
  }
  
  return dp[n-1][m-1];

}


//returns least path value to reach the destination
static int minSumPath(int n, int m, int[][] matrix, int[][] dp){
    
    for(int i=0; i<n ; i++){
        for(int j=0; j<m; j++){
            if(i>0 && j>0 && matrix[i][j]==-1){
            dp[i][j]= max;
            continue;
          }
            if(i==0 && j==0) dp[i][j] = matrix[i][j];
            else{
                
                int up = matrix[i][j];
                if(i>0) up += dp[i-1][j];
               else up += max;
                
                int left = matrix[i][j];
                if(j>0) left+=dp[i][j-1];
                else left += max;
                
                dp[i][j] = Math.min(up,left);
            }
        }
    }
    
    return dp[n-1][m-1];
    
}
public static void main(String args[]) {
    
  //maze initialisation where -1's indicate traps
  int[][] maze = { { 2, 5, 7, 3, 1},
                   { 1,-1, 1, 1,-1},
                   {-1, 1, -1, 2, 1},
                   { 3,-1, 1, -1, 2},
                   {-1,-1,-1,-1, 0}};
                            
  int n = maze.length;
  int m = maze.length;
  
 //initialising dp array to -1
  int dp[][]=new int[n][m];
    for(int row[]: dp)
    Arrays.fill(row,-1);
    
  //prints number of ways to reach the destination
  System.out.println(" Number of ways to reach the destination is: "+mazeObstacles(n,m,maze,dp));
  
  //prints the least cost to reach the destination
  System.out.println(" Minimum path cost to reach the destination is: "+minSumPath(n,m,maze,dp));
}
}
