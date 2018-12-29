import java.util.Scanner;

//Control class
public class Control
{
	public static void main(String[] args) 
	{
		int i;
		int noOfCities;
		int source, destination;
		Scanner sc = new Scanner(System.in);
		
		//Print out the cities and their corresponding indexes.
		System.out.println("The cities and their corresponding indexes: ");
		System.out.println("1-Atlanta, 2-Beijing, 3-Dubai, 4-Los Angeles, 5-Tokyo\n"
				+ "6-Chicago, 7-London, 8-Hong Kong, 9-Shanghai, 10-Delhi\n"
				+ "11-Paris, 12-Amsterdam, 13-Dallas, 14-Guangzhou, 15-Frankfurt\n"
				+ "16-Istanbul, 17-Jakarta, 18-Singapore, 19-Incheon, 20-Denver\n"
				+ "21-Bangkok, 22-New York, 23-Kuala Lumpur, 24-San Francisco, 25-Charlotte\n"
				+ "26-Las Vegas, 27-Madrid, 28-Miami, 29-Phoenix, 30-Houston\n");
		
		//Read in the number of cities.
		do
		{
			System.out.println("Please input the number of cities (from 5 to 30 cities): ");
			noOfCities = sc.nextInt();
		} while ((noOfCities<5)||(noOfCities>30));
		
		//Create a map of all flights between cities.
		Map map = new Map(noOfCities);
		map.addCities();
		map.addMapConnections();
		System.out.println("Instructions on how to read the flight map.");
		System.out.println("First row and first column: Indexes of the cities shown above.");
		System.out.println("Rest of the map: 0 means a direct flight between the 2 cities does not exist."
				+ "1 means a direct flight between the 2 cities exists.");
		System.out.println("Flight map: ");
		map.printMap();
		
		//Read in the departure city.
		do
		{
			System.out.println("Take note that the departure city should be one of the cities shown on the map.");
			System.out.println("Departure from (enter index of city): ");
			source = sc.nextInt(); 
		} while ((source<1)||(source>noOfCities));
		
		//Read in the arrival city.
		do
		{
			System.out.println("Take note that the departure and arrival cities should be different.");
			System.out.println("Also take note that the arrival city should be one of the cities shown on the map.");
			System.out.println("Arrival to (enter index of city): ");
			destination = sc.nextInt();
		} while ((destination==source)||(destination<1)||(destination>noOfCities)); 
		
		//BFS begins.
		
		//Create a bfsMap.
		Map bfsMap = new Map(noOfCities);
		bfsMap.addCities();
		
		//Get mapOfFlights and bFSMap.
		int[][] mapOfFlights = map.getMap();
		int[][] bFSMap = bfsMap.getMap();
		
		//Create an integer array 'reached' to keep track of whether a city has been reached or not in the BFS process.
		int[] reached = new int[noOfCities];
		//Indicate that the source has been reached.
		reached[source-1] = 1;
		
		//BFS requires a queue.
		int[] queue = new int[noOfCities];
		//queueIndex is the running index for the queue.
		int queueIndex = 0;
		//processedIndex indicates the index of the element in the queue currently being processed.
		int processedIndex = 0;
		
		//currentCity is the city currently being processed.
		int currentCity;
		
		//Initialize queue with the source.
		queue[queueIndex] = source;
		queueIndex++;
		//While there is a city to be processed.
		while (queue[processedIndex]>0)
		{
			//currentCity takes the city to be processed.
			currentCity = queue[processedIndex];
			//For every city.
			for (i=1; i<=noOfCities; i++)
			{
				//Check that there is a direct flight between currentCity and city i, and city i has not been reached.
				if ((mapOfFlights[currentCity][i]==1) && (reached[i-1]==0))
				{
					//Indicate that city i has been reached.
					reached[i-1] = 1;
					//Add city i to the queue.
					queue[queueIndex] = i;
					queueIndex++;
					//Indicate on the bFSMap that a direct flight from currentCity to city i is possible.
					bFSMap[currentCity][i] = 1;
				}
				
				//Check if destination is reached.
				if (reached[destination-1]==1)
				{
					break;
				}
			}
			if (reached[destination-1]==1)
			{
				break;
			}
			//Move on to the next city to be processed.
			processedIndex++;
		}
		
		//Check if the destination cannot be reached.
		if (reached[destination-1]==0)
		{
			System.out.println("The destination cannot be reached.");
			return;
		}
		
		//Backtracking begins.
		
		//Initialize backtrackCurrentCity to the destination.
		int backtrackCurrentCity = destination;
		//Create an integer array 'reversedShortestPath' to store the shortest path in reversed order.
		int[] reversedShortestPath = new int[noOfCities];
		//reversedShortestPathIndex is the running index for reversedShortestPath.
		int reversedShortestPathIndex = 0;
		//Add destination to reversedShortestPath.
		reversedShortestPath[reversedShortestPathIndex] = destination;
		reversedShortestPathIndex++;
		
		//While backtracking has not reached the source.
		while (backtrackCurrentCity!=source)
		{
			//For every city.
			for (i=1; i<=noOfCities; i++)
			{
				//Check that there is a direct flight from city i to backtrackCurrentCity.
				if (bFSMap[i][backtrackCurrentCity]==1)
				{
					//City i is the previous city.
					//Add i to reversedShortestPath.
					reversedShortestPath[reversedShortestPathIndex] = i;
					reversedShortestPathIndex++;
					break;
				}
			}
			//backtrackCurrentCity takes the previous city.
			backtrackCurrentCity = i;
		}
		
		//Print the shortest path.
		System.out.println("Here is the shortest path: ");
		for (i=reversedShortestPathIndex-1; i>=0; i--)
		{
			System.out.print(reversedShortestPath[i] + " ");
		}
	}
}