package mazes;

import java.util.LinkedList;
import java.util.Stack;

public class DFSMaze {

	public static int[][] makeMaze(int width, int height) {
		
		/*
		 * The final grid will actually be of size [2*width + 1]x[2*height + 1].
		 * The width and height passed to makeMaze represent the number of nodes the maze has
		 * on each side, where a node is a 3x3 square with an empty space in the middle
		 * and 8 optional walls around it. Adjacent nodes will share these walls.
		 */
		int[][] maze = new int[2*height + 1][2*width + 1];
		int totalCells = width * height;
		int visitedCells = 1;
		
		int i = 0;
		int j = 0;
		
		/*
		 * The 2D grid is initialised with a uniform grid of walls and spaces.
		 * Walls are set to 1 and spaces set to 0.
		 */
		for(j = 0; j < 2*height + 1; j++) {
			for(i = 0; i < 2*width + 1; i++) {
				if (i%2 == 1 && j%2 == 1) {
					maze[j][i] = 0;
				} else {
					maze[j][i] = 1;
				}
			}
		}
		
		//return maze;
		
		/*
		 * The stack of cells to be considered is initialised and the first cell is added to it.
		 */
		Stack<Cell> cellStack = new Stack<Cell>();
		Cell currentCell = new Cell(1, 1);
		cellStack.push(currentCell);
		
		//The algorithm loops until all cells have been visited.
		while (visitedCells < totalCells) {
			
			//The walled neighbours of the currentCell are found
			LinkedList<Cell> neighbours = getWalledNeighbours(maze, currentCell);
			if (!neighbours.isEmpty()) {
				//A random walled neighbour is chosen
				int cellIndex = (int)Math.floor(Math.random() * neighbours.size());
				Cell newCell = neighbours.get(cellIndex);
				
				/*
				 * The wall between the currentCell and its chosen neighbour
				 *is broken, forming a gap between them.
				 */
				maze[(currentCell.getY() + newCell.getY())/2][(currentCell.getX() + newCell.getX())/2] = 0;
				
				/*
				 * the currentCell is pushed onto the cellStack so it may be reconsidered later.
				 * For now, the newly selected cell is set as currentCell and the algorithm continues with it.
				 */
				cellStack.push(currentCell);
				currentCell = newCell;
				visitedCells++;
			} else {
				//No neighbours to consider, a previously considered node must be reconsidered.
				currentCell = cellStack.pop();
			}
			
		}
		
		//Optional Modification
		for(j = 0; j < 2*height + 1; j++) {
			for(i = 0; i < 2*width + 1; i++) {
				if ((j == 1 || j == 2*height - 1) && (i != 0 && i != 2 * width)) {
					//maze[j][i] = 0;
				}
			}
		}
		
		return maze;
		
	}
	
	public static void printMaze(int[][] maze) {
		
		int i = 0;
		int j = 0;
		
		for(j = 0; j < maze.length; j++) {
			String line = "";
			for(i = 0; i < maze[j].length; i++) {
				if (maze[j][i] == 1) {
					line = line.concat("#");
				} else {
					line = line.concat("-");
				}
			}
			System.out.println(line);
		}
		
	}
	
	private static LinkedList<Cell> getWalledNeighbours(int[][] maze, Cell cell) {
		
		/*
		 * Taking a maze and a cell within it, all adjacent cells of the initial cell who
		 * have all four adjacent walls intact (excluding diagonal walls) are returned in a list.
		 */
		
		LinkedList<Cell> result = new LinkedList<Cell>();
		int x = cell.getX();
		int y = cell.getY();
		
		//Top Neighbour
		if (y > 2) {
			if(maze[y - 3][x] == 1 && maze[y - 1][x] == 1 && maze[y - 2][x - 1] == 1 && maze[y - 2][x + 1] == 1) {
				result.add(new Cell(x, y - 2));
			}
		}
		//Bottom Neighbour
		if (y < maze.length - 3) {
			if(maze[y + 3][x] == 1 && maze[y + 1][x] == 1 && maze[y + 2][x - 1] == 1 && maze[y + 2][x + 1] == 1) {
				result.add(new Cell(x, y + 2));
			}
		}
		//Left Neighbour
		if (x > 2) {
			if(maze[y][x - 3] == 1 && maze[y][x - 1] == 1 && maze[y - 1][x - 2] == 1 && maze[y + 1][x - 2] == 1) {
				result.add(new Cell(x - 2, y));
			}
		}
		//Right Neighbour
		if (x < maze[0].length - 3) {
			if(maze[y][x + 3] == 1 && maze[y][x + 1] == 1 && maze[y - 1][x + 2] == 1 && maze[y + 1][x + 2] == 1) {
				result.add(new Cell(x + 2, y));
			}
		}
		
		return result;
		
	}

}
