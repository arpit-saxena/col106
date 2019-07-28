public class program
{
	public float test(int numbers[])
	{
		/*
		Exercise 13: Average of numbers- Given an array of integers finds the average
		of all the elements. For e.g. for {4,7,9,4} the average is 6 and for {1,3,8,5} the
		average is 4.25. Please note that if the array has no elements, the average should
		be 0.
		*/
		if (numbers.length == 0) {
			return 0.0f;
		}

		float sum = 0.0f;
		for (int number: numbers) {
			sum += number;
		}
		return sum / numbers.length;
	}
}
