import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Simple Sudoku Solver
 *
 * @author chrisjaimes
 * @since 12/26/2017
 */

public class SudokuSolver {

	static int calls = 0;
	int[][] sudoku = new int[9][9];

	private boolean isFilled() {
		for(int row = 0; row < 9; row++)
			for(int col = 0; col < 9; col++)
				if(this.sudoku[row][col] == 0)
					return false;

		return true;
	}

	private boolean checkRow(int value, int row) {

		int found = 0;

		for(int i = 0; i < 9; i++) {
			if(sudoku[row][i] == value) {
				found++;
			}
		}

		if(found > 0)return false;
		else return true;
	}

	private boolean checkColumn(int value, int col) {

		int found = 0;

		for(int i = 0; i < 9; i++) {
			if(sudoku[i][col] == value) {
				found++;
			}
		}

		if(found > 0) return false;
		else return true;
	}

	private boolean checkGrid(int value, int row, int col) {
		int x = row - row%3;
		int y = col - col%3;

		for(int i = 0; i < 3 && x + i < 9; i++) {
			for(int j = 0; j < 3 && y+j < 9; j++) {
				if(sudoku[x+i][y+j] == value)
					return false;
			}
		}

		return true;
	}

	private boolean solve(int row, int col) {

		//print the current number of calls to this function
		//System.out.println(++calls);

		if(isFilled()) {
			return true;
		}

		if(sudoku[row][col] != 0) {
			if(col + 1 > 8)
				return solve(row+1, 0);
			else
				return solve(row, col+1);
		} else {

			for(int number = 1; number < 10; number++) {

				if(checkRow(number, row) && checkColumn(number,col) && checkGrid(number, row, col)) {
					sudoku[row][col] = number;

					// print grid every time a guess is taken, including final solution
					// for (int[] r : sudoku)
					// {
					//     System.out.println(Arrays.toString(r));
					// }

					if(col + 1 > 8) {
						if(solve(row+1, 0))
							return true;
						else continue;
					} else {
						if (solve(row, col+1))
							return true;
						else continue;
					}
				}
			}

			sudoku[row][col] = 0;
			return false;
		}
	}

	private boolean populate_array(int[][] sudoku) {

		try {

			int number = 0;

			File file = new File("./src/input-sudoku.txt");
			Scanner scanner = new Scanner(file);

			for(int row = 0; row < 9; row++) {
				for(int col = 0; col < 9; col++) {

					if(!scanner.hasNextInt())
						return false;

					number = scanner.nextInt();
					sudoku[row][col] = number;

				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("could not open file " + e);
			return false;
		}

		return true;
	}

	public static void main(String[] args) {

		SudokuSolver ss = new SudokuSolver();

		if(!ss.populate_array(ss.sudoku)) {
			System.exit(0);
		}

		System.out.println("Sudoku:");
		for (int[] r : ss.sudoku)
		{
			System.out.println(Arrays.toString(r));
		}

		if(ss.solve(0, 0)) {

			System.out.println("\nSolved!");

			//print out solution
			for (int[] r : ss.sudoku)
			{
				System.out.println(Arrays.toString(r));
			}
		} else {
			System.out.println("\nNo solution :(\nMake sure your Sudoku is solvable.");
		}
	}
}
