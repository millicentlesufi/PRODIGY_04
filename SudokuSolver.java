import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SudokuSolver extends JFrame
{

    private static final int SIZE = 9; // Size of the Sudoku grid (9x9)
    private JTextField[][] cells = new JTextField[SIZE][SIZE]; // Array to hold text fields for the grid
    private int[][] grid = {
        { 3, 0, 6, 5, 0, 8, 4, 0, 0 },
        { 5, 2, 0, 0, 0, 0, 0, 0, 0 },
        { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
        { 0, 0, 3, 0, 1, 0, 0, 8, 0 },
        { 9, 0, 0, 8, 6, 3, 0, 0, 5 },
        { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
        { 1, 3, 0, 0, 0, 0, 2, 5, 0 },
        { 0, 0, 0, 0, 0, 0, 0, 7, 4 },
        { 0, 0, 5, 2, 0, 6, 3, 0, 0 }
    };

    public SudokuSolver() {
        setTitle("Sudoku Solver"); 
        setSize(600, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null); 

        JPanel panel = new JPanel(); //panel to hold the grid
        panel.setLayout(new GridLayout(SIZE, SIZE)); 

        // Initializing the grid with text fields
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                cells[row][column] = new JTextField();
                cells[row][column].setHorizontalAlignment(JTextField.CENTER); 
                cells[row][column].setFont(new Font("Arial", Font.BOLD, 20)); 

                // If the grid cell is not empty, set the text field value and make it non-editable
                if (grid[row][column] != 0) {
                    cells[row][column].setText(String.valueOf(grid[row][column]));
                    cells[row][column].setEditable(false);
                }
                panel.add(cells[row][column]); 

                // Alternate background color for 3x3 blocks
                if ((row / 3 + column / 3) % 2 == 0) {
                    cells[row][column].setBackground(Color.LIGHT_GRAY);
                } else {
                    cells[row][column].setBackground(Color.WHITE);
                }

                cells[row][column].setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add a black border to each cell
            }
        }

        JButton solveButton = new JButton("Solve"); // button to solve the Sudoku
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Attempt to solve the Sudoku and update the grid if successful
                if (solve(grid, 0, 0)) {
                    updateGrid();
                } else {
                    
                    JOptionPane.showMessageDialog(null, "No Solution exists");
                }
            }
        });

        getContentPane().add(panel, BorderLayout.CENTER); 
        getContentPane().add(solveButton, BorderLayout.SOUTH); // Add the solve button to the bottom of the window
    }

    // Update the text fields with the solved grid values
    private void updateGrid() {
        for (int row = 0; row < SIZE; row++) {
            for (int column = 0; column < SIZE; column++) {
                cells[row][column].setText(String.valueOf(grid[row][column]));
            }
        }
    }

    // Solving using backtracking
    private boolean solve(int[][] grid, int row, int column) {
        // If we have reached the last cell, the Sudoku is solved
        if (row == SIZE - 1 && column == SIZE) return true;

        // Move to the next row if we have reached the end of the current row
        if (column == SIZE) {
            row++;
            column = 0;
        }

        // Skip the cells that are already filled
        if (grid[row][column] != 0) return solve(grid, row, column + 1);

        // Try placing numbers 1-9 in the current cell
        for (int numberToBeAdded = 1; numberToBeAdded <= SIZE; numberToBeAdded++) {
            // Check if the number can be placed in the current cell
            if (canAddToGrid(grid, row, column, numberToBeAdded)) {
                grid[row][column] = numberToBeAdded; // Place the number

                // Recursively try to solve the rest of the grid
                if (solve(grid, row, column + 1)) return true;

                grid[row][column] = 0; // Reset the cell if the number does not lead to a solution
            }
        }
        return false; // no number can be placed in the current cell
    }

    // Check if a number can be placed in a specific cell
    private boolean canAddToGrid(int[][] grid, int row, int col, int numberToBeAdded) {
        // Check the row
        for (int x = 0; x < SIZE; x++) if (grid[row][x] == numberToBeAdded) return false;

        // Check the column
        for (int x = 0; x < SIZE; x++) if (grid[x][col] == numberToBeAdded) return false;

        // Check the 3x3 subgrid
        int startRow = row - row % 3, startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i + startRow][j + startCol] == numberToBeAdded) return false;
            }
        }
        return true; //the number can be placed in the cell
    }

    public static void main(String[] args) {
        // Create and display the Sudoku Solver GUI
        SwingUtilities.invokeLater(new Runnable() 
		{
            @Override
            public void run() 
			{
                new SudokuSolver().setVisible(true);
            }
        });
    }
}
