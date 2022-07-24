#include <stdio.h>
#include<limits.h>
#define smax 100
int top=-1;
char possiblePaths[smax];  
char path[smax];
int n, count = 0;
int min_dist = INT_MAX;
void push(char x)
{  
      if(top == smax-1)
          printf("stack overflow");  
      else
          possiblePaths[++top]=x;  
} 

void pop()
{    
      possiblePaths[top--];
}

int isValidCell(int x, int y)
{
    if (x < 0 || y < 0 || x >= n || y >= n) 
        return 0;
 
    return 1;
}

int isSafe(int row, int col, int m[][n], int n, int visited[][n])
{
	if (row == -1 || row == n || col == -1 || col == n || visited[row][col] || m[row][col] == 0)
		return 0;
	return 1;
}

int safe(int mat[n][n], int visited[n][n], int x, int y)
{
    if (mat[x][y] == 0 || visited[x][y]) {
        return 0;
    }
 
    return 1;
}

void findShortestPath(int mat[n][n], int visited[n][n], int i, int j, int x, int y, int dist)
{
    if (i == x && j == y)
    {
        if(dist < min_dist)
            min_dist = dist;
        return;
    }
    visited[i][j] = 1;
    if (isValidCell(i + 1, j) && safe(mat, visited, i + 1, j)) {
        findShortestPath(mat, visited, i + 1, j, x, y, dist + 1);
    }
    if (isValidCell(i, j + 1) && safe(mat, visited, i, j + 1)) {
        findShortestPath(mat, visited, i, j + 1, x, y, dist + 1);
    }
    if (isValidCell(i - 1, j) && safe(mat, visited, i - 1, j)) {
        findShortestPath(mat, visited, i - 1, j, x, y, dist + 1);
    }
    if (isValidCell(i, j - 1) && safe(mat, visited, i, j - 1)) {
        findShortestPath(mat, visited, i, j - 1, x, y, dist + 1);
    }
    visited[i][j] = 0;
}

void countPaths(int maze[n][n], int x, int y, int visited[n][n])
{
    if (x == n - 1 && y == n - 1)
    {
        count++;
        return;
    }
    visited[x][y] = 1;
    if (isValidCell(x, y) && maze[x][y])
    {
        if (x + 1 < n && !visited[x + 1][y]) {
            countPaths(maze, x + 1, y, visited);
        }
        if (x - 1 >= 0 && !visited[x - 1][y]) {
            countPaths(maze, x - 1, y, visited);
        }
        if (y + 1 < n && !visited[x][y + 1]) {
            countPaths(maze, x, y + 1, visited);
        }
        if (y - 1 >= 0 && !visited[x][y - 1]) {
            countPaths(maze, x, y - 1, visited);
        }
    }
    visited[x][y] = 0;
}

void printPathUtil(int row, int col, int m[][n], int n, int visited[][n])
{
	if (row == -1 || row == n || col == -1 || col == n || visited[row][col] || m[row][col] == 0)
		return;

	if (row == n - 1 && col == n - 1) {
	    
		for(int i=0; i<=top; i++){
		    printf("%c", possiblePaths[i]);
		}
		if(top == min_dist-1)
	        printf(" --> Shortest path");
	    printf("\n");
		int sol[n][n];
		for(int i = 0; i < n; i++){
		    for(int j = 0; j < n; j++){
		        sol[i][j] = 0;
		    }
		}
		int i = 0;
		int j = 0;
		sol[i][j] = 1;
		for(int k = 0; k <= top; k++){
		    if(possiblePaths[k] == 'D'){
		        sol[++i][j] = 1;
		    }
		    if(possiblePaths[k] == 'R'){
		        sol[i][++j] = 1;
		    }
		    if(possiblePaths[k] == 'L'){
		        sol[i][--j] = 1;
		    }
		    if(possiblePaths[k] == 'U'){
		        sol[--i][j] = 1;
		    }
		}
		for(int i = 0; i < n; i++){
		    for(int j = 0; j < n; j++){
		        printf("%d ", sol[i][j]);
		    }
		    printf("\n");
		}
		printf("\n");
		return;
	}
	visited[row][col] = 1;
	
	if (isSafe(row + 1, col, m, n, visited)){
		push('D');
		printPathUtil(row + 1, col, m, n, visited);
		pop();
	}
	
	if (isSafe(row, col - 1, m, n, visited)){
		push('L');
		printPathUtil(row, col - 1, m, n, visited);
		pop();
	}
	
	if (isSafe(row, col + 1, m, n, visited)){
		push('R');
		printPathUtil(row, col + 1, m, n, visited);
		pop();
	}
	
	if (isSafe(row - 1, col, m, n, visited)){
		push('U');
		printPathUtil(row - 1, col, m, n, visited);
		pop();
	}
	visited[row][col] = 0;
}

void printPath(int m[n][n], int n)
{
	char path[smax];
	int visited[n][n];
	for(int i = 0; i < n; i++){
	    for(int j = 0; j < n; j++){
	        visited[i][j] = 0;
	    }
	}
	printPathUtil(0, 0, m, n, visited);
}

int main()
{
    printf("Enter the size of the maze : ");
    scanf("%d", &n);
    int m[n][n];
    printf("Enter the maze\n");
    for(int i = 0 ; i < n; i++){
        for(int j = 0; j < n; j++){
            scanf("%d", &m[i][j]);
        }
    }
    int visited[n][n];
    for(int i = 0; i < n; i++){
	    for(int j = 0; j < n; j++){
	        visited[i][j] = 0;
	    }
	}
	countPaths(m, 0, 0, visited);
	printf("\nThe number of unique paths for the given maze is %d\n", count);
    findShortestPath(m, visited, 0, 0, n-1, n-1, 0);
    for(int i = 0; i < n; i++){
	    for(int j = 0; j < n; j++){
	        visited[i][j] = 0;
	    }
	}
	printf("All possible paths are :\n");
	printPath(m, n);
	return 0;
}
