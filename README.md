# HEFT-based Task-Scheduling-Algorithm
Implementation of algorithm to solve task scheduling problem. Algorithm is heterogeneous Earliest Finish Time(Greedy).

Problem Statement : Given a set of Tasks T, set of resources/servers S and the graph structure to represent the dependencies of tasks (essentially a Directed Acyclic graph[DAG]), we need to find the most optimal way of scheduling tasks i.e. determine the order of execution of tasks on servers so that it takes least time. Moreover, the duration of execution of each task on each server will be preknown and could be given as Input data.  

Task Scheduling problem is an NP-Hard problem. Polynomial Time solutions to solve it perfectly are not known as of now. Heuristic techniques are used to find near optimal solutions. HEFT is one of them which is used commonly. 

This is an implementation in JAVA.


EXPLANATION OF CODE : 
(A) Supporting classes : -  
1.  edge : attributes of an edge are the other end of edge, weight of edge

2. task_node : contains attributes to define a task node completely. 
It implements compareTo function which is required for sorting as a comparator.

3. I : for fast Input

(B) Attributes of the DLP class :  
1. num_task, num_servers (Self explanatory)
2. List<> adj[] : Adjacent List of the DAG
3. List<> par[] : Adjacent List of the DAG if all the edges are reversed. In other words, if there is an egde (u,v,w) in adj[], then an edge (v,u,w) will be present in par[]
4. cost[][] : executions costs for any task on any server. 
5. task_sz[] : denotes the memory required for each task. This is required only when server's capacity limitation is taken into account.
6. arr[] : array of type task nodes.

(C) FUNCTIONS OF THE CLASS : 
1. run() : this is where we call dfs to calculate DLP values for each node,  sort the nodes according to their DLP values and then process them to get the final sequence of execution.
2. dfs() : recursive function to call DLP values.
3. main() : Here I/O is handled and DLP class object is initialised and then run function is called.
