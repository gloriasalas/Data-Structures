package friends;

import java.util.ArrayList;

import structures.Queue;
import structures.Stack;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null or empty array list if there is no
	 *         path from p1 to p2
	 */

	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		
		/** COMPLETE THIS METHOD **/
		//You have a target 
		//you want to find the shortest path to target
		//if no way to get to target, return null
		if (g == null || p1 == null || p2 == null) {
			return null;
		}
		ArrayList<String> shortlist = new ArrayList<String>();
		boolean[] visited = new boolean[g.members.length];
		Person [] currentlyVisited = new Person [g.members.length];
		Queue<Person> queue = new Queue<Person>(); //taking the person class and putting 
		
		queue.enqueue(g.members[g.map.get(p1)]); //adding
		visited[g.map.get(p1)] = true; //where p1 is current at we make true for visiting
		
		//loop
		while (queue.isEmpty() == false) {
			Person next = queue.dequeue();
			int nextIndex = g.map.get(next.name);
			visited[nextIndex] = true;
			
			//neighbor of next
			Friend neighbor = next.first;
			if (neighbor == null) {
				return null;
			}
			while (neighbor != null) {
				if(visited[neighbor.fnum] == false) {
					visited[neighbor.fnum] = true;
					currentlyVisited[neighbor.fnum] = next;
					queue.enqueue(g.members[neighbor.fnum]);
					
					//check if p2 matches the neighbor
					if(g.members[neighbor.fnum].name.equals(p2)) {
						next = g.members[neighbor.fnum];
						
						while (next.name.equals(p1) == false) {
							shortlist.add(0, next.name);
							next = currentlyVisited[g.map.get(next.name)];
						}
						shortlist.add(0, p1);
						return shortlist;
					}
				}
				neighbor = neighbor.next;
			}
		}
		return null;
	}
	
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null if there is no student in the
	 *         given school
	 */
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {
		
		/** COMPLETE THIS METHOD **/
		//find people in the same school
		//remove those without a school
		//put each island into an array list
		//return array list 
		
		//FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		//CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		ArrayList<ArrayList<String>> answer = new ArrayList<>();
		if (g == null || school == null || school.length() == 0)
		{
		   return null;
		}
		school = school.toLowerCase();
		boolean[] visited = new boolean[g.members.length];
		for (int i = 0; i < visited.length; i++)
		{
		   visited[i] = false;
		}
		for (Person member : g.members)
		{
		   if (!visited[g.map.get(member.name)] && member.school != null && member.school.equals(school))
		   {
		    Queue<Integer> queue = new Queue<>();
		    ArrayList<String> clique = new ArrayList<>();
		    int startIndex = g.map.get(member.name);
		    visited[startIndex] = true;
		    queue.enqueue(startIndex);
		    clique.add(member.name);
		    while (!queue.isEmpty())
		    {
		     int v = queue.dequeue(); 
		     Person p = g.members[v];
		     for (Friend ptr = p.first; ptr != null; ptr = ptr.next)
		     {
		      int fnum = ptr.fnum;
		      Person f = g.members[fnum];
		      if (!visited[fnum] && f.school != null && f.school.equals(school))
		      {
		       visited[fnum] = true;
		       queue.enqueue(fnum);
		       clique.add(g.members[fnum].name);
		      }
		     }
		    }
		    answer.add(clique);
		   }
		}
		return answer;
	}
	

	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		
		/** COMPLETE THIS METHOD **/
		//vertex v - connector 
		//vertex x and w - connected to v
		//finding connectors = DFS (depth first search)
		
		if (g == null) {
			return null;
		}
		
		ArrayList<String> connect = new ArrayList<String>();
		ArrayList<String> fred = new ArrayList<String>();
		boolean[] visited = new boolean[g.members.length];
		
		int [] DFS = new int[g.members.length];
		int [] before = new int[g.members.length];
		
		for (int i = 0; i < g.members.length; i++) {
			if (visited[i] == false) {
				connect = calcDFS(connect, g, g.members[i], visited, new int[] { 0, 0 }, DFS, before,
						fred, true);
			}
		}
		//sent to other method
		return connect;
	}
	
	private static ArrayList<String> calcDFS(ArrayList<String> connectors, Graph g, Person start, boolean[] visitas, 
			int[] count, int[] numbersOfDFS, int[] back, ArrayList<String> backward, boolean started) {
		
		//sets the index visited to true
		visitas[g.map.get(start.name)] = true;
		Friend neighbor = start.first;
		numbersOfDFS[g.map.get(start.name)] = count [0];
		back[g.map.get(start.name)] = count[1];
		
		while (neighbor != null) {
			
			if(visitas[neighbor.fnum] == false) {
				
				count[0]++;
				count[1]++;
				
				connectors = calcDFS(connectors, g, g.members[neighbor.fnum], visitas, count, numbersOfDFS, back, backward, false);
				
				if (numbersOfDFS[g.map.get(start.name)] <= back[neighbor.fnum]) {
					
					if ((connectors.contains(start.name) == false && backward.contains(start.name))
							|| (connectors.contains(start.name) == false && started == false)) {
						connectors.add(start.name);
					}
				} else {
					
					int first = back[g.map.get(start.name)];
					int second = back[neighbor.fnum];
					
					if (first < second) {
						back[g.map.get(start.name)] = first;
					} else {
						back[g.map.get(start.name)] = second;
					}
				}
				backward.add(start.name);
			} else {
				
				int third = back[g.map.get(start.name)];
				int fourth = numbersOfDFS[neighbor.fnum];
				
				if (third < fourth) {
					back[g.map.get(start.name)] = third;
				} else {
					back[g.map.get(start.name)] = fourth;
				}
			}
			neighbor = neighbor.next;
			}
			return connectors;
		}	
	}

