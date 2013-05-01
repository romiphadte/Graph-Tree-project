
public class Edge implements Comparable {
	
	public int weight;
	public Object v1; //vertex 1
	public Object v2; //vertex 2

	@Override
	public int compareTo(Object o) {
		if(this.weight>((Edge) o).weight)
		{
			return 1;
		}
		else if (this.weight<((Edge) o).weight)
		{
			return -1;
		}
		return 0;
	}
}
