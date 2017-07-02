# HEFT-based Task-Scheduling-Algorithm
Implementation of algorithm to solve task scheduling problem. Algorithm is heterogeneous Earliest Finish Time(Greedy).

Problem Statement : Given a set of Tasks T, set of resources/servers S and the graph structure to represent the dependencies of tasks (essentially a Directed Acyclic graph[DAG]), we need to find the most optimal way of scheduling tasks i.e. determine the order of execution of tasks on servers so that it takes least time. Moreover, the duration of execution of each task on each server will be preknown and could be given as Input data.  

Task Scheduling problem is an NP-Hard problem. Polynomial Time solutions to solve it perfectly are not known as of now. Heuristic techniques are used to find near optimal solutions. HEFT is one of them which is used commonly. 

This is an implementation in JAVA.

