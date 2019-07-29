public class program
{
	private boolean endsWith(String test, String end)
	{
		if (test.length() < end.length())
		{
			return false;
		}

		for(int i = 0; i < end.length(); i++)
		{
			if (test.charAt(test.length() - i - 1) != end.charAt(end.length() - i - 1)) 
			{
				return false;
			}
		}

		return true;
	}

	public String[] test(String fileNames[])
	{
		/*
		Exercise 16: Java files- You have been given the list of the names
		of the files in a directory.
		You have to select Java files from them.
		A file is a Java file if it’s name ends with ”.java”.
		For e.g. ”File-Names.java” is a Java file, ”FileNames.java.pdf” is not.
		If the input is {”can.java”,”nca.doc”,”and.java”,”dan.txt”,”can.java”,”andjava.pdf”} 
		the expected output is {”can.java”,”and.java”,”can.java”}
		*/

		int length = 0;
		boolean[] ends = new boolean[fileNames.length];
		for (int i = 0; i < fileNames.length; i++)
		{
			if (endsWith(fileNames[i], ".java"))
			{
				ends[i] = true;
				length++;
			}
			else
			{
				ends[i] = false;
			}
		}

		String javaFiles[] = new String[length];

		int javaFileIndex = 0;
		for(int i = 0; i < ends.length; i++)
		{
			if (ends[i])
			{
				javaFiles[javaFileIndex] = fileNames[i];
				javaFileIndex++;
			}
		}

		return javaFiles;
	}
}
