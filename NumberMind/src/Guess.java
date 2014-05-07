import java.util.Random;


public class Guess {

	private static final int SEQUENCELENGTH = 16;
	private static Random generator = new Random();
	
	private int[] sequence;
	private int numberOfCorrectDigits;
	
	public Guess(long aSequence, int correctDigits)
	{
		int[] sequenceArray = new int[SEQUENCELENGTH];
		long sequenceCopy = aSequence;
		for (int i = 0 ; i < SEQUENCELENGTH ; i++)
		{
			sequenceArray[SEQUENCELENGTH - 1 - i] = (int) (sequenceCopy % 10);
			sequenceCopy /= 10;
		}			
		this.sequence = sequenceArray;
		this.numberOfCorrectDigits = correctDigits;
	}
	
	public int[] getSequence()
	{
		return sequence;
	}
	
	public int getCorrectDigits()
	{
		return numberOfCorrectDigits;
	}
	
	public int gradeGuessSequence(int[] aSequence)
	{		
		int matches = 0;
		for (int i = 0 ; i < SEQUENCELENGTH; i++)
		{
			if (this.sequence[i] == aSequence[i])
			{
				matches ++;
			}			
		}
		return SEQUENCELENGTH - Math.abs(numberOfCorrectDigits - matches);		 	
	}
	
}
