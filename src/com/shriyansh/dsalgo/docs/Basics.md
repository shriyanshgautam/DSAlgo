# Basics

## Time Complexity


### Input Constraints
[Video](https://www.youtube.com/watch?v=3etzME4DNXg)

#### Time Constraints
Constraints given in the problem give indication of the time complexity that we need to match.
In general any problem involve 10^8 ops/sec or TLE of 1 second which means max of 10^8 ops for a problem

So 
* if input constraints are of order 10^8 then it should be solved in O(n)
* if input constraints are ot order 10^4 then it should be solved in O(n^2)
Following table summarizes constraint of question with required time complexity of solution

| Order      | Constraint | Example                                   | 
|------------|------------|-------------------------------------------|
| $O(logn)$  | $10^9$     | Binary Search, Mathematical, Greedy       |
| $O(n)$     | $10^8$     | Mathematical, Greedy                      |
| $O(nlogn)$ | $10^6$     | Sorting, Binary Search, DnC               |
| $O(n^2) $  | $10^4$     | Dynamic Programming, Graphs, Trees        | 
| $O(n^3)$   | $500$      | Dynamic Programming                       | 
| $O(n^4)$   | $100$      | Dynamic Programming                       | 
| $O(2^n)$   | $25$       | Recursion, Backtracking, Bit Manipulation | 
| $O(n!)$    | $12$       | Recursion, Backtracking                   | 

#### Data size constraints

