public class program
{
	public float[] test(int b, int c)
	{
		/*
		Exercise 12: Roots of polynomial- Write a Java program that given b and c,
		computes the roots of the polynomial x*x+b*x+c. You can assume that the
		roots are real valued and need to be return in an array.
		Return the result in an array [p,q] where p<=q meaning the smaller 
		element should be the first element of the array
		*/

		float det = b * b - 4 * c;
		float rootDet = (float) Math.sqrt(det);
		float root1 = (-b - rootDet) / 2.0f;
		float root2 = (-b + rootDet) / 2.0f;

		float[] roots = { root1, root2 };
		return roots;
	}
}
