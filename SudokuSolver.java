
public class SudokuSolver {

	//size of 2D matrix
	static int size = 9;

	/* Takes a partially filled-in grid and attempts
	to assign values to all unassigned locations in
	such a way to meet the requirements for
	Sudoku solution (non-duplication across rows,
	columns, and boxes) */
	static boolean solveSudoku(int grid[][], int row, int column)
	{

		/*if we have reached the 8th
		row and 9th column (0
		indexed matrix) ,
		we are returning true to avoid further
		backtracking	 */
		if (row == size - 1 && column == size)
			return true;

		// Check if column value becomes 9 ,
		// we move to next row
		// and column start from 0
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
			return solveSudoku(grid, row, column + 1);

		for (int number = 1; number < 10; number++) {

			// Check if it is safe to place
			// the num (1-9) in the
			// given row ,col ->we move to next column
			if (canAddToGrid(grid, row, column, number))
            {

				/* assigning the num in the current
				(row,col) position of the grid and
				assuming our assigned num in the position
				is correct */
				grid[row][column] = number;

				// Checking for next
				// possibility with next column
				if (solveSudoku(grid, row, column + 1))
					return true;
			}
			/* removing the assigned num , since our
			assumption was wrong , and we go for next
			assumption with diff num value */
			grid[row][column] = 0;
		}
		return false;
	}

	/* A utility function to print grid */
	static void print(int[][] grid)
	{
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++)
				System.out.print(grid[i][j] + " ");
			System.out.println();
		}
	}

	// Check whether it will be legal
	// to assign num to the
	// given row, col
	static boolean canAddToGrid(int[][] grid, int row, int column,
						int numberToAdd)
	{

		// Check if we find the same num
		// in the similar row , we
		// return false
		for (int x = 0; x <= 8; x++)
			if (grid[row][x] == numberToAdd)
				return false;

		// Check if we find the same num
		// in the similar column ,
		// we return false
		for (int x = 0; x <= 8; x++)
			if (grid[x][column] == numberToAdd)
				return false;

		// Check if we find the same num
		// in the particular 3*3
		// matrix, we return false
		int startRow = row - row % 3, startCol
									= column - column % 3;
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				if (grid[i + startRow][j + startCol] == numberToAdd)
					return false;

		return true;
	}

	// Driver Code
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

		if (solveSudoku(grid, 0, 0))
			print(grid);
		else
			System.out.println("No Solution exists");
	}
	
}
