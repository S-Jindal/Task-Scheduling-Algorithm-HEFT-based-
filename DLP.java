
import java.util.*;
import java.io.*;	
// The class is named DLP (Dynamically Longest Path)
 class DLP {

	public static void main(String[] args) {
		/* INPUT - OUTPUT SECTION IS IN MAIN METHOD
		 * OTHER STUFF IS DEFINED IN NON STATIC METHODS AND
		   CALLED USING CLASS OBJECT 
		 * Time Complexity of solution is O(n*log(n) + n*k + m),
		 *  n = task nodes,  k = servers, m = edges in DAG
		 */
		
		Random r = new Random();
		int nT = 10, nS = 3, i;
		I re = new I();				// CLASS OBJECT TO GET INPUTS
		//nT = re.in_i(); int nE = re.in_i();
		List<edge> adj[] = new ArrayList[nT+1];
		List<edge> par[]= new ArrayList[nT+1];
		for(i = 1; i <= nT; i++){				// Initialise the adjacent list of graph
			adj[i] = new ArrayList<edge>();
			par[i] = new ArrayList<edge>();
		}	
		// THIS IS JUST THE SAMPLE INPUT HARDCODED, IF REQUIRED TAKE THE INPUT ACCORDINGLY 
		adj[1].add(new edge(2, 18));
		adj[1].add(new edge(3, 12));
		adj[1].add(new edge(4, 9));
		adj[1].add(new edge(5, 11));
		adj[1].add(new edge(6, 14));
		adj[2].add(new edge(8, 19));
		adj[2].add(new edge(9, 16));
		adj[3].add(new edge(7, 23));
		adj[4].add(new edge(8,27));
		adj[4].add(new edge(9, 28));
		adj[5].add(new edge(9, 13));
		adj[6].add(new edge(8, 15));
		adj[7].add(new edge(10, 17));
		adj[8].add(new edge(10, 11));
		adj[9].add(new edge(10, 13));
		
		for(i=1; i<=nT; i++){
			int sz = adj[i].size(), x=0;
			for(; x < sz; x++){
				edge e = adj[i].get(x);
				par[e.to].add(new edge(i, e.weight));
			}
		}
		// COST OF EXECUTION OF TASK I ON SERVER J, I - ROW | J - COLUMN
		int cost[][]={ {0,0,0},
					   {0,14,16,9},
					   {0,13,19,18},
					   {0,11,13,19},
					   {0,13,8,17},
					    {0,12,13,10},
					    {0,13,16,9},
					    {0,7,15,11},
					    {0,5, 11, 14}, 
					    {0,18, 12, 20},
					    {0, 21, 7, 16}
		
		};
		
		/*nS = re.in_i();
		int cost[][] = new int[nT+1][nS+1];
		/*System.out.println("  The costs of execution of Task_i on Server_j is as follows : ");
		for(i = 1; i <= nT; i++){
			//System.out.printf(" Task %d : ",i);
			for(int j = 1; j <= nS; j++){
				cost[i][j] = re.in_i();
				//System.out.print(cost[i][j]+"  ");
			}
			//System.out.println();
		}*/
		
		
		DLP o = new DLP(adj, par, nT, nS, cost, null);
		o.run();
	}
	 
	int num_tasks, num_servers;
	List<edge> adj[];		// Graph structure
	List<edge> adj_par[];
	int task_sz[];			// task node size in terms of MB/GB(space required to execute it)
	int cost[][];			// cost(in terms of time) for task i on server j
	
	DLP(List<edge> adj[], List<edge> p[], int nt, int ns, int c[][], int tsz[])
	{
		this.adj = adj.clone();
		adj_par = p;
		num_tasks=nt;
		num_servers = ns;
		cost = c;
		task_sz = tsz;
	}
	
	task_node arr[];
	int max_par[];
	 
	void run(){
		
		arr=new task_node[num_tasks];				
		int i,j;
		for(i=0; i<num_tasks; i++)
			arr[i] = new task_node(i+1);
		for(i=0; i<num_tasks; i++)
			if(arr[i].dlp==0)
				dfs(arr[i].id);			// CALL DFS FOR NON VISITED NODE 
		
		Arrays.sort(arr);
		
		int curr_state[] = new int[num_servers+1];
		int finish[] = new int[num_tasks + 1];
		
		for(i = 0; i < num_tasks; i++)
		{
			//System.out.println(arr[i].dlp);
			int id = arr[i].id, min = Integer.MAX_VALUE, min_j=-1, max = 0;
			
			for(j = 0; j<adj_par[id].size(); j++){
				edge e = adj_par[id].get(j);
				if(  e.weight + finish[e.to] > max){					 
					 max = e.weight + finish[e.to];
				}
			}
			for(j = 1; j <= num_servers; j++)
			{
				if( Math.max(curr_state[j],max) + cost[id][j] < min){
					min = Math.max(curr_state[j],max) + cost[id][j];
					min_j = j;	
				}
			}
			
			curr_state[min_j]  = min;
			finish[id] = curr_state[min_j];
			System.out.printf("---- Adding Node %d to server %d at time %d ----\n",
											id, min_j, min-cost[id][min_j]);
			
		}
		int max = 0;
		for(i = 0; i < curr_state.length; i++)
			if(curr_state[i] > max)
				max = curr_state[i];
		
		System.out.printf("\n   Total time taken : %d", max);
	}
	
	float dfs(int node_id){
		if(arr[node_id-1].dlp!=0)
			return arr[node_id-1].dlp;
		
		if(adj[node_id].size()==0){		// base case of Q(exit)
			float avg=0;
			for(int j=1; j<=num_servers; j++){
				
					avg+=cost[node_id][j];				
			}
			return arr[node_id-1].dlp = avg/num_servers;
		}
		
		int i,sz=adj[node_id].size();
		float max=-1;
		for(i=0; i<sz; i++){
			edge e= adj[node_id].get(i);
			max = Math.max(dfs(e.to)+e.weight, max);			
		}
		arr[node_id-1].dlp = max; 
		float avg = 0;
		for(int j=1; j<=num_servers; j++)			
				avg+=cost[node_id][j];				
		
		return arr[node_id-1].dlp += (avg/num_servers);		
	}

}
//  SUPPORTING CLASS TO REPRESENT EDGES
class edge{
	int to, weight;
	public edge(int x, int y){
		to=x; 
		weight = y;
	}
}

// ABSTRACTION OF TASK NODE. ADDITIONAL ATTRIBUTES CAN BE ADDED AS PER REQUIREMENT
class task_node implements Comparable<task_node>{
	int id;
	float dlp;
	task_node(int x){
		id=x;
		dlp=0;
	}
	@Override
	public int compareTo(task_node arg) {
		
		if(this.dlp < arg.dlp)
			return 1;
		if(this.dlp > arg.dlp)
			return -1;
		
		return 0;
	}
}

// UTILITY CLASS TO TO TAKE INPUTS
class I{
	private InputStream stream;
	private byte[] buf = new byte[1024];
	private int curChar;
	private int numChars;
	private SpaceCharFilter filter;
	
	public I() {
		this.stream = System.in;
	}
	
	public int read() {
		if (numChars == -1) {
			throw new InputMismatchException();
		}
		if (curChar >= numChars) {
			curChar = 0;
			try {
				numChars = stream.read(buf);
			} catch (IOException e) {
				throw new InputMismatchException();
			}
			if (numChars <= 0) {
				return -1;
			}
		}
		return buf[curChar++];
	}
	
	public int in_i() {
		int c = read();
		while (isSpaceChar(c)) {
			c = read();
		}
		int sgn = 1;
		if (c == '-') {
			sgn = -1;
			c = read();
		}
		int res = 0;
		do {
			if (c < '0' || c > '9') {
				throw new InputMismatchException();
			}
			res *= 10;
			res += c - '0';
			c = read();
		} while (!isSpaceChar(c));
		return res * sgn;
	}
	
	public String readString() {
		int c = read();
		while (isSpaceChar(c)) {
			c = read();
		}
		StringBuilder res = new StringBuilder();
		do {
			res.appendCodePoint(c);
			c = read();
		} while (!isSpaceChar(c));
		return res.toString();
	}
	
	public double in_d() {
		int c = read();
		while (isSpaceChar(c)) {
			c = read();
		}
		int sgn = 1;
		if (c == '-') {
			sgn = -1;
			c = read();
		}
		double res = 0;
		while (!isSpaceChar(c) && c != '.') {
			if (c == 'e' || c == 'E') {
				return res * Math.pow(10, in_i());
			}
			if (c < '0' || c > '9') {
				throw new InputMismatchException();
			}
			res *= 10;
			res += c - '0';
			c = read();
		}
		if (c == '.') {
			c = read();
			double m = 1;
			while (!isSpaceChar(c)) {
				if (c == 'e' || c == 'E') {
					return res * Math.pow(10, in_i());
				}
				if (c < '0' || c > '9') {
					throw new InputMismatchException();
				}
				m /= 10;
				res += (c - '0') * m;
				c = read();
			}
		}
		return res * sgn;
	}
	
	public long in_l() {
		int c = read();
		while (isSpaceChar(c)) {
			c = read();
		}
		int sgn = 1;
		if (c == '-') {
			sgn = -1;
			c = read();
		}
		long res = 0;
		do {
			if (c < '0' || c > '9') {
				throw new InputMismatchException();
			}
			res *= 10;
			res += c - '0';
			c = read();
		} while (!isSpaceChar(c));
		return res * sgn;
	}
	
	public boolean isSpaceChar(int c) {
		if (filter != null) {
			return filter.isSpaceChar(c);
		}
		return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
	}
	
	public String next() {
		return readString();
	}
	
	public interface SpaceCharFilter {
		public boolean isSpaceChar(int ch);
	}
}

