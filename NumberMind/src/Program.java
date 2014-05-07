import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;


public class Program {

	public static ArrayList<Guess> data = new ArrayList<Guess>();	
	public static Random numberGen = new Random(System.currentTimeMillis());
	//Data Tables
	
	public static ArrayList<GAEntity> createPopulation()
	{
		Set<GAEntity> aSet = new TreeSet<GAEntity>();
		ArrayList<GAEntity> population = new ArrayList<GAEntity>();
		while (aSet.size() < GeneticAlgorithm.POP_SIZE)
		{
			GAEntity entity = new Sequence();
			entity.generateNew();			
			aSet.add(entity);
		}
		
		population.addAll(aSet);
		
		return population;		
	}
	
	public static boolean isASolution(GAEntity best)
	{
		int maxScore = 16 * data.size();		
		return (maxScore == best.evaluate());	
	}
			
	public static void loadData()
	{	
		
		data.add(new Guess(2321386104303845L ,0));
		data.add(new Guess(3847439647293047L ,1));
		data.add(new Guess(3174248439465858L ,1));
		data.add(new Guess(4895722652190306L ,1));		
		data.add(new Guess(6375711915077050L ,1));
		data.add(new Guess(6913859173121360L ,1));
		data.add(new Guess(8157356344118483L ,1));		
		data.add(new Guess(2615250744386899L ,2));
		data.add(new Guess(2326509471271448L ,2));
		data.add(new Guess(2659862637316867L ,2));		
		data.add(new Guess(4513559094146117L ,2));
		data.add(new Guess(5251583379644322L ,2));
		data.add(new Guess(5616185650518293L ,2));
	    data.add(new Guess(6442889055042768L ,2));		
		data.add(new Guess(1748270476758276L ,3));
		data.add(new Guess(1841236454324589L ,3));		
		data.add(new Guess(3041631117224635L ,3));
		data.add(new Guess(4296849643607543L ,3));
		data.add(new Guess(5855462940810587L ,3));
		data.add(new Guess(7890971548908067L ,3));
		data.add(new Guess(8690095851526254L ,3));		
		data.add(new Guess(9742855507068353L ,3));
		
		/*
		data.add(new Guess(90342 ,2));
		data.add(new Guess(70794 ,0));
		data.add(new Guess(39458 ,2));
		data.add(new Guess(34109 ,1));
		data.add(new Guess(51545 ,2));
		data.add(new Guess(12531 ,1));
		*/
		
	}
		
	@SuppressWarnings("unchecked")
	public static void main(String[] args)
	{		
		loadData();
		Sequence.setData(data);		
				
		GAEntity generationalBest = null;
		long startTime = System.currentTimeMillis();
		while (generationalBest == null || !isASolution(generationalBest))
		{	
			ArrayList<GAEntity> population = createPopulation();
			GAEntity best = GeneticAlgorithm.getBest(population);			
			generationalBest = best;
			int counter = 0;
			
			while (!isASolution(generationalBest) && best.compareTo(population.get(GeneticAlgorithm.POP_SIZE - 1)) != 0  && counter < GeneticAlgorithm.GENERATION_LIMIT)
			{
				//population.set(0, generationalBest);				
				population = GeneticAlgorithm.breedNextGeneration(population);	
				best = GeneticAlgorithm.getBest(population);	
				if (best.evaluate() >= generationalBest.evaluate())
				{
					generationalBest = best;
				}
				counter ++;
			}
			System.out.println(best + " " + counter);			
		}				
		System.out.println(System.currentTimeMillis() - startTime + " ms");
	}
}
