import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;


public class GeneticAlgorithm 
{
	public static final int POP_SIZE = 10000;		
	public static final int GENERATION_LIMIT = 250;
		
	private static Random randomGenerator = new Random(System.currentTimeMillis());		
	private static GAEntity rouletteWheelSelection(ArrayList<GAEntity> population)
	{
		GAEntity best = getBest(population);
		double sum = sumPopulation(population);		
		double p = randomGenerator.nextDouble();
		double value = 0;
		for (int i = population.size() - 1; i >= 0; i--)
		{
			value += population.get(i).evaluate();
			if (value/sum > p)
				return population.get(i);
		}				
		return best;
	}
	
	private static GAEntity randomSelection(ArrayList<GAEntity> population)
	{
		return population.get(randomGenerator.nextInt(POP_SIZE));
	}
	
	private static double sumPopulation(ArrayList<GAEntity> population)
	{
		double sum = 0;
		for (int i = 0 ; i < population.size(); i++)
		{
			sum += population.get(i).evaluate();
		}
		return sum;
	}
	
	public static ArrayList<GAEntity> breedNextGeneration(ArrayList<GAEntity> population)
	{
		List<GAEntity> nextGen = population.subList(0, POP_SIZE -1);			
		
		int size = population.size();			
		while (nextGen.size() < POP_SIZE * 2)
		{		
			GAEntity parentA = randomSelection(population);
			GAEntity parentB = randomSelection(population);			
			ArrayList<GAEntity> children = parentA.uniformCrossOver(parentB);												
			for (GAEntity child : children)
			{
				child = child.mutate();
			}										
			nextGen.addAll(children);
			
		}	
		Collections.sort(nextGen, Comparators.ScoreComparatorDescending);
		return new ArrayList<GAEntity>(nextGen);
	}
	
	@SuppressWarnings("unchecked")
	public static GAEntity getBest(ArrayList<GAEntity> population)
	{
		Collections.sort(population, Comparators.ScoreComparatorDescending);
		return population.get(0);
	}
	
	public static double calcuateAverageOfPopulation(ArrayList<GAEntity> population)
	{
		double total = sumPopulation(population);
		return total/population.size();
	}
}
