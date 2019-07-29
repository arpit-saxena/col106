public class program
{
	// num has to be able to be represented
	// by 4 bits. Otherwise, only last 4 bits
	// are returned.
	// last char is LSB
	private String getZeroFilledBinary(int num)
	{
		char[] ans = new char[4];
		for(int i = 0; i < 4; i++)
		{
			ans[4-1-i] = (char)('0' + (num % 2));
			num /= 2;
		}
		return new String(ans);
	}

	private String hexCharacterToBinary(char hex) 
	{
		if (hex >= '0' && hex <= '9')
		{
			return getZeroFilledBinary(hex - '0');
		}
		else if (hex >= 'A' && hex <= 'F')
		{
			return getZeroFilledBinary(hex - 'A' + 10);
		}
		
		return "";
	}

	public String test(String hex)
	{
		/*
		Exercise 15: Hex to binary- Given a string representing a number in hexadecimal
		format, convert it into its equivalent binary string. For e.g. if the input if ”1F1”
		then its binary equivalent is ”111110001”. If the input is ”13AFFFF”, the output
		should be ”1001110101111111111111111”.
		*/
		String binary = new String();

		for (int i = 0; i < hex.length(); i++)
		{
			binary += hexCharacterToBinary(hex.charAt(i));
		}

		int firstNonZero = -1;
		for (int i = 0; i < binary.length(); i++)
		{
			if (binary.charAt(i) != '0')
			{
				firstNonZero = i;
				break;
			}
		}

		if (firstNonZero == -1) // Whole binary string is zero
		{
			return "0";
		}

		return binary.substring(firstNonZero);
	}
}
