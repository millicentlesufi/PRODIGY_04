//package PRODIGY_04;
public class SudokuSolver
{

	//size of 2D matrix
	static int size = 9;

	//solving partially filled sudoku grid
	static boolean solve(int grid[][], int row, int column)
	{
		//if we have reached the 8th row and 9th column to avoid further backtracking
		if (row == size - 1 && column == size)
			return true;

		// column value is 9 move to next row, and column start from zero
		if (column == size)
        {
			row++;
			column = 0;
		}

		// Check if the current position
		// of the grid already
		// contains value >0, we iterate
		// for next column
		if (grid[row][column] != 0)
			return solve(grid, row, column + 1);

		for (int numberToBeAdded = 1; numberToBeAdded< 10; numberToBeAdded++) {

			// Check if number can be added that is in the range of 1-9 in the given row, if not move to next column
			if (canAddToGrid(grid, row, column, numberToBeAdded))
            {
                // number added to the current column and row
				grid[row][column] = numberToBeAdded;

				// check if can be added to the next column
				if (solve(grid, row, column + 1))
					return true;
			}
			//resetting the assumption of numberToBeAdded
			grid[row][column] = 0;
		}
		return false;
	}

	/* function to print grid */
	static void showGrid(int[][] grid)
	{
		for (int i = 0; i < size; i++)
        {
			for (int j = 0; j < size; j++)
				System.out.print(grid[i][j] + " ");
			System.out.println();
		}
	}

	//check whether a number can be added at a specific location(row,col)
	static boolean canAddToGrid(int[][] grid, int row, int column,
						int numberToAdd)
	{

		//same number to be added found in the similar row
		for (int x = 0; x <= 8; x++)
			if (grid[row][x] == numberToAdd)
				return false;
        //same number to be added found in the similar column       
		for (int x = 0; x <= 8; x++)
			if (grid[x][column] == numberToAdd)
				return false;

		//same number found within 3x3 matrix 
		int startingRow = row - row % 3;
        int startingColumn = column - column % 3;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (grid[i + startingRow][j + startingColumn] == numberToAdd)
					return false;

		return true;
	}

	
	public static void main(String[] args)
	{
		int grid[][] = { { 3, 0, 6, 5, 0, 8, 4, 0, 0 },
						{ 5, 2, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 8, 7, 0, 0, 0, 0, 3, 1 },
						{ 0, 0, 3, 0, 1, 0, 0, 8, 0 },
						{ 9, 0, 0, 8, 6, 3, 0, 0, 5 },
						{ 0, 5, 0, 0, 9, 0, 6, 0, 0 },
						{ 1, 3, 0, 0, 0, 0, 2, 5, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 7, 4 },
						{ 0, 0, 5, 2, 0, 6, 3, 0, 0 } };

		if (solve(grid, 0, 0)){
			System.out.println("Solved Sudoku game:");
			System.err.println("*****************");
			showGrid(grid);
			System.err.println("*****************");
		}

			
		else
			System.out.println("No Solution exists");
	}
	
}
