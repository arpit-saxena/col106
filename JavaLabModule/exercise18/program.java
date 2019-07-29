public class program
{
	public int[][] test(int M1[][], int M2[][])
	{
		/*
		Exercise 18: Matrix addition- Given two matrices M1 and M2, the objective to
		add them. Each matrix is provided as an int[][], a 2 dimensional integer array.
		The expected output is also 2 dimensional integer array.
		*/
		int[][] result = new int[M1.length][M1[0].length];
		
		for (int i = 0; i < result.length; i++)
		{
			for (int j = 0; j < result[i].length; j++)
			{
				result[i][j] = M1[i][j] + M2[i][j];
			}
		}

		return result;
	}
}
