import java.util.Comparator;


public class Comparators {

	public static Comparator<GAEntity> ScoreComparator = new Comparator<GAEntity>()
			{
				public int compare(GAEntity a, GAEntity b)
				{
					return Double.compare(a.evaluate(), b.evaluate());
				}
			};
	
	public static Comparator<GAEntity> ScoreComparatorDescending = new Comparator<GAEntity>()
			{
				public int compare(GAEntity a, GAEntity b)
				{
					return Double.compare(b.evaluate() ,a.evaluate());
				}
			};	

	
}
