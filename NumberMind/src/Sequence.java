import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;


public class Sequence implements GAEntity{

	private static ArrayList<Guess> data = null;
	public static final int SEQUENCE_LENGTH = 16;
	private static Random numberGenerator = new Random(System.currentTimeMillis());	
	private int[] sequence;
	private double score;; 
		
	public Sequence()
	{
		score = -1;
	}
	
	public Sequence(long aSequence)
	{
		sequence = convertToArray(aSequence);
		score = -1;
	}
	
	public Sequence(int[] aSequence)
	{
		sequence = aSequence;
		score = -1;
	}
	
	public static void setData(ArrayList<Guess> dataset)
	{
		data = dataset;
	}
	
	public double evaluate()
	{			
		if (this.score < 0)
		{
			double score = 0;
			for (Guess g : data)
			{
				score += g.gradeGuessSequence(sequence);
			}
			this.score = score;
		}		
		return this.score ;
	}

	private int[] convertToArray(long aSequence)
	{		
		int[] sequenceArray = new int[SEQUENCE_LENGTH];
		long sequenceCopy = aSequence;
		for (int i = 0 ; i < SEQUENCE_LENGTH ; i++)
		{
			sequenceArray[SEQUENCE_LENGTH - 1 - i] = (int) (sequenceCopy % 10);
			sequenceCopy /= 10;
		}	
		return sequenceArray;
	}
		
	private long convertToLong(int[] sequenceArray)
	{		
		long sequence = 0;
		for (int i = 0 ; i < SEQUENCE_LENGTH; i++)
		{
			sequence += sequenceArray[i];
			sequence *= 10;			
		}
		return sequence;
	}
	
	public GAEntity crossOverBreeding(GAEntity a) 
	{
						
		int[] sequence1 = new int[SEQUENCE_LENGTH]; 
		int[] sequence2 = new int[SEQUENCE_LENGTH];
		
		System.arraycopy(this.sequence, 0, sequence1, 0, SEQUENCE_LENGTH);		 		
		System.arraycopy(((Sequence)(a)).sequence, 0, sequence2, 0, SEQUENCE_LENGTH);
		
		
		for (int i = 0 ; i < SEQUENCE_LENGTH; i++)
		{
			double p = numberGenerator.nextDouble();
			if (p < .2)
			{		int index = numberGenerator.nextInt(SEQUENCE_LENGTH);
					int toIndex = numberGenerator.nextInt(SEQUENCE_LENGTH);
					sequence1[index] = sequence2[index];
			}
		}
		
		return new Sequence(sequence1);
	}
	
	public ArrayList<GAEntity> onePointCrossOverBreeding(GAEntity a) 
	{
						
		int[] sequence1 = new int[SEQUENCE_LENGTH]; 
		int[] sequence2 = new int[SEQUENCE_LENGTH];
		
		System.arraycopy(this.sequence, 0, sequence1, 0, SEQUENCE_LENGTH);		 		
		System.arraycopy(((Sequence)(a)).sequence, 0, sequence2, 0, SEQUENCE_LENGTH);
		
		int index = numberGenerator.nextInt(SEQUENCE_LENGTH);			
		for (int i = index ; i < SEQUENCE_LENGTH; i++)
		{					
			sequence1[i] = sequence2[i];
			sequence2[i] = this.sequence[i];
		}		
		ArrayList<GAEntity> children = new ArrayList<GAEntity>();
		children.add(new Sequence(sequence1));
		children.add(new Sequence(sequence2));
		
		return children;
	}
	
	public GAEntity mutate() 
	{				
		
		if (numberGenerator.nextDouble() < 0.01)
		{
			ArrayList<ArrayList<Integer>> mutationTable = generateMutationTable();
			for (int i = 0 ; i < SEQUENCE_LENGTH; i++)
			{
				
				if (numberGenerator.nextDouble() < 1/SEQUENCE_LENGTH)
				{
					if (mutationTable.get(i).size() > 0)			
					{
						sequence[i] = mutationTable.get(i).get(numberGenerator.nextInt(mutationTable.get(i).size()));
						
					}
					else 
					{
						sequence[i] = numberGenerator.nextInt(10);
					}			
				}
			}			
		}
	
		return this;
	}
	
	private ArrayList<ArrayList<Integer>> generateMutationTable()
	{
		ArrayList<ArrayList<Integer>> dataTable = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < SEQUENCE_LENGTH; i++)
		{
			ArrayList<Integer> possibleDigits = new ArrayList<Integer>();			
			dataTable.add(possibleDigits);
		}
		
		for (Guess g : data)
		{
			for (int i = 0 ; i < SEQUENCE_LENGTH; i++)
			{
				if (numberGenerator.nextDouble() < (double)(g.getCorrectDigits())/(double)(SEQUENCE_LENGTH))
				{
					if (!dataTable.get(i).contains(g.getSequence()[i]))
						dataTable.get(i).add(g.getSequence()[i]);
				}
			}
		}		
		return dataTable;
	}
	
	public int compareTo(Object o) {
		Sequence s = (Sequence)(o);
		return Double.compare(convertToLong(sequence), s.convertToLong(s.sequence));
	}
	
	public String toString()
	{
		String s =  evaluate() + " " ;
		for (int i = 0 ; i < SEQUENCE_LENGTH; i++)
		{
			s += sequence[i];
		}
		return s;
	}
	
	public void generateNew() 
	{
		ArrayList<ArrayList<Integer>> mutationTable = generateMutationTable();
		sequence = new int[SEQUENCE_LENGTH];
		for (int i = 0 ; i < SEQUENCE_LENGTH ; i ++)
		{
			if (mutationTable.get(i).size() > 0)
			sequence[i] = mutationTable.get(i).get(numberGenerator.nextInt(mutationTable.get(i).size()));
			else
			sequence[i] = numberGenerator.nextInt(10);
		}
	}

	public long key() {
		// TODO Auto-generated method stub
		return convertToLong(sequence);
	}

	public ArrayList<GAEntity> uniformCrossOver(GAEntity a) {
		// TODO Auto-generated method stub
		int[] sequence1 = new int[SEQUENCE_LENGTH]; 
		int[] sequence2 = new int[SEQUENCE_LENGTH];
		
		System.arraycopy(this.sequence, 0, sequence1, 0, SEQUENCE_LENGTH);		 		
		System.arraycopy(((Sequence)(a)).sequence, 0, sequence2, 0, SEQUENCE_LENGTH);
		
		for (int i = 0 ; i < SEQUENCE_LENGTH; i++)
		{			
			if (numberGenerator.nextDouble() < .5)
			{
				sequence1[i] = sequence2[i];
				sequence2[i] = this.sequence[i];
			}
		}		
		ArrayList<GAEntity> children = new ArrayList<GAEntity>();
		children.add(new Sequence(sequence1));
		children.add(new Sequence(sequence2));
		
		return children;
	}
	
}
