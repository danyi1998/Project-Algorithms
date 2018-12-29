import java.util.Random;

//Map of the flights between cities.
public class Map
{
	private int mapLength;
	private int[][] map; 
	
	//Constructor.
	public Map(int noOfCities) 
	{
		mapLength = noOfCities+1;
		map = new int[mapLength][mapLength];
	}
	
	//Add the indexes of cities to the first row and first column of the map.
	public void addCities()
	{	
		for (int i=1; i<mapLength; i++) 
		{
			map[0][i] = i;
			map[i][0] = i;
		}
		
	}
	
	//Add connections between cities.
	public void addMapConnections()
	{	
		//Randomly assign flights between cities.
		for(int i=2; i<mapLength; i++)
		{
			for (int j=1; j<i-1; j++)
			{
				map[i][j] = new Random().nextInt(2); 
				map[j][i] = map[i][j];
			}
		}
	}
	
	//Return the map.
	public int[][] getMap()
	{
		return map;
	}

	//Print the map.
	public void printMap() 
	{
		//Print the first row.
		System.out.print("   #");
		for (int i=1; i<mapLength; i++)
		{
			System.out.printf("%4d", map[0][i]);
		}
		System.out.println("");
		
		//Print all other rows.
		for (int i=1; i<mapLength; i++)
		{
			for (int j=0; j<mapLength; j++)
			{
				System.out.printf("%4d", map[i][j]);
			}
			System.out.println("");
		}
	}
}