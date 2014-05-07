import java.util.ArrayList;


public interface GAEntity extends Comparable{

	public long key();
	public double evaluate();
	public GAEntity crossOverBreeding(GAEntity a);
	public ArrayList<GAEntity> onePointCrossOverBreeding(GAEntity a);
	public ArrayList<GAEntity> uniformCrossOver(GAEntity a);
	public GAEntity mutate();	
	public void generateNew();	
}
