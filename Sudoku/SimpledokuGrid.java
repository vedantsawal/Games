package simpledoku;

import java.util.*;


public class SimpledokuGrid {

	private int				nCellsPerSide;
	private int[][]			values;
	
	
	public SimpledokuGrid(int nCellsPerSide)
	{
		this.nCellsPerSide = nCellsPerSide;
		values = new int[nCellsPerSide][nCellsPerSide];
	}
	
	
	// This is called a "copy constructor". A copy constructor has 1 argument, which is the same type as
	// the current class. The constructor should make the new instance look just like the "source"
	// instance. CAUTION: The source and the current grid should each have their own "values"
	// array. So don't say "this.values = source.values". You have to create a new values
	// array, and copy numbers from source.values into the new array, one at a time.
	public SimpledokuGrid(SimpledokuGrid source)
	{
		int sideLength = source.nCellsPerSide;
		this.nCellsPerSide = sideLength;
		values = new int[sideLength][sideLength];
		for (int i = 0; i < sideLength; i++)
		{
			for (int j = 0; j < sideLength; j++)
			{
				values [i][j] = source.values [i][j];
			}
		}
	}
	
	// This is a new custom constructor that allows you to create a SimpledokuGrid object with a custom made grid.
	public SimpledokuGrid(int n, int [][] matrix)
	{
		this.nCellsPerSide = n;
		values = new int[n][n];
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				values [i][j] = matrix [i][j];
			}
		}
	}
	
	
	//
	// Returns true if the input list contains a repeated value that isn't zero.
	// Call this from isLegal(). DON’T CHANGE THIS METHOD, BUT UNDERSTAND HOW IT WORKS.
	//
	private boolean containsNonZeroRepeats(ArrayList<Integer> checkUs)
	{
		HashSet<Integer> set = new HashSet<>();
		for (Integer i: checkUs)
		{
			if (i != 0)
			{
				//could I write this a different way? DON'T CHANGE THE CODE, but THINK about it.
				if (set.contains(i))
					return true;
				else
					set.add(i);
			}
		}
		return false;
	}
	
	
	public boolean isLegal()
	{
		// Check all rows. For each row, put the corresponding numbers from
		// the "values" array into an ArrayList<Integer>. Then pass the array
		// list to containsNonZeroRepeats(). If containsNonZeroRepeats() return true,
		// then this grid isn't legal, so return false.
		for (int i = 0; i < this.nCellsPerSide; i++)
		{
			ArrayList <Integer> currentRow = new ArrayList <Integer>();
			for (int j = 0; j < this.nCellsPerSide; j++)
			{
				int currentElement = this.values [i][j];
				currentRow.add(currentElement);
			}
			if (containsNonZeroRepeats(currentRow) == true)
			{
				// System.out.println("Row " + (i + 1) + " has a repeated digit. Illegal Grid.");
				return false;
			}
		}
		


		// Check all columns. For each column, put the corresponding numbers from
		// the "values" array into an ArrayList<Integer>. Then pass the array
		// list to containsNonZeroRepeats(). If containsNonZeroRepeats() return true,
		// then this grid isn't legal, so return false.
		for (int i = 0; i < this.nCellsPerSide; i++)
		{
			ArrayList <Integer> currentColumn = new ArrayList <Integer>();
			for (int j = 0; j < this.nCellsPerSide; j++)
			{
				int currentElement = this.values [j][i];
				currentColumn.add(currentElement);
			}
			if (containsNonZeroRepeats(currentColumn) == true)
			{
				// System.out.println("Column " + (i + 1) + " has a repeated digit. Illegal Grid.");
				return false;
			}
		}
		


		
		// Check top-left to bottom-right diagonal. 
		ArrayList <Integer> currentDownRightDiagonal = new ArrayList <Integer>();
		for (int i = 0; i < this.nCellsPerSide; i++)
		{
			for (int j = 0; j < this.nCellsPerSide; j++)
			{
				if (i == j)
				{
					int currentElement = this.values [i][j];
					currentDownRightDiagonal.add(currentElement);
				}
			}
		}
		if (containsNonZeroRepeats(currentDownRightDiagonal) == true)
		{
			// System.out.println("Top-left to bottom-right diagonal has a repeated digit. Illegal Grid.");
			return false;
		}
		
		
	
		// Check top-right to bottom-left diagonal.
		ArrayList <Integer> currentDownLeftDiagonal = new ArrayList <Integer>();
		for (int i = 0; i < this.nCellsPerSide; i++)
		{
			for (int j = 0; j < this.nCellsPerSide; j++)
			{
				if (i + j == this.nCellsPerSide - 1)
				{
					int currentElement = this.values [i][j];
					currentDownLeftDiagonal.add(currentElement);
				}
			}
		}
		if (containsNonZeroRepeats(currentDownLeftDiagonal) == true)
		{
			// System.out.println("Top-right to bottom-left diagonal has a repeated digit. Illegal Grid.");
			return false;
		}
		
		
		// If we haven't returned yet, this grid must be legal.
		return true;
		
	}
	
	
	// Returns true if all members of the values[][] array are non-zero.
	public boolean isFull()
	{
		int zeroCounter = 0;
		for (int i = 0; i < this.nCellsPerSide; i++)
		{
			for (int j = 0; j < this.nCellsPerSide; j++)
			{
				if (this.values [i][j] == 0)
				{
					zeroCounter = zeroCounter + 1;
				}
			}
		}
		if (zeroCounter == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}	
	
	
	
	// Returns the Evaluation corresponding to the state of this grid. The return will be
	// Evaluation.ABANDON, Evaluation.ACCEPT, or Evaluation.CONTINUE. Abandon if illegal,
	// accept if legal and the grid is full, continue if legal and incomplete.
	public Evaluation evaluate()
	{
		if (this.isLegal() == true && this.isFull() == true)
		{
			return Evaluation.ACCEPT;
		}
		else if (this.isLegal() == true && this.isFull() == false)
		{
			return Evaluation.CONTINUE;
		}
		else
		{
			return Evaluation.ABANDON;
		}
	}
	
	
	// Returns a size=2 array of ints that index the next empty cell in the values[][] array.
	// The 2 ints in the returned array are the first and second indices into the values[][] array.
	// Returns null if this grid is full.  DON’T CHANGE THIS METHOD, BUT UNDERSTAND HOW IT WORKS.
	private int[] getIndicesOfNextEmptyCell()
	{
		for (int x=0; x<nCellsPerSide; x++)
			for (int y=0; y<nCellsPerSide; y++)
				if (values[x][y] == 0)
					return new int[] {x, y};
		
		return null;
	}
	
	
	//
	// COMPLETE THIS
	//
	//
	// Finds an empty member of "values". Returns an array list containing nCellsPerSide grids that look 
	// like the current grid, except the empty member contains 1, 2, 3 .... nCellsPerSide. 
	// 
	//
	// Example: if this grid = 12300
	//                         00000
	//                         00000
	//                         00000
	//                         00000
	//
	// Then the returned array list contains:
	//
	//      12310        12320        12330        12340        12350
	//      00000        00000        00000        00000        00000
	//      00000        00000        00000        00000        00000
	//      00000        00000        00000        00000        00000
	//      00000        00000        00000        00000        00000
	//
	// To create each new grids, use the copy constructor and pass in "this" grid. Then
	// change one member of the "values" of the new grid.
	// CAUTION: Make sure you are generating new grids!
	public ArrayList<SimpledokuGrid> generateAllNextGrids()
	{		
		int[] indicesOfNext = getIndicesOfNextEmptyCell();
		ArrayList<SimpledokuGrid> nextGrids = new ArrayList<SimpledokuGrid>();
		// Generate some grids and put them in nextGrids. Be sure that every
		// grid is a separate object.
		for (int value = 1; value <= this.nCellsPerSide; value++)
		{
			SimpledokuGrid newGrid = new SimpledokuGrid(this);
			int emptyRow = indicesOfNext [0];
			int emptyColumn = indicesOfNext [1];
			newGrid.values [emptyRow][emptyColumn] = value;
			nextGrids.add(newGrid);
		}
		return nextGrids;
	}
	
	
	// Use this for debugging if it's helpful.  DON’T CHANGE THIS METHOD, BUT UNDERSTAND HOW IT WORKS.
	public String toString()
	{
		String s = "";
		for (int j=0; j<nCellsPerSide; j++)
		{
			for (int i=0; i<nCellsPerSide; i++)
			{
				if (values[j][i] == 0)
					s += ".";
				else
					s += ("" + values[j][i]);
			}
			s += "\n";
		}
		return s;
	}
	
	//You can add a main method here for debugging. 
	public static void main (String [] args)
	{
		int [][] matrix = {{3, 1, 4, 2}, {2, 4, 1, 3}, {1, 3, 2, 4}, {4, 2, 3, 1}};
		SimpledokuGrid testGrid = new SimpledokuGrid(4, matrix);
		System.out.println(testGrid.evaluate());
	}
}