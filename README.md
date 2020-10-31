# OOJavaA

This is my michael ReadMe File.

Explanations:

FindPath - i am using a helper class, that would handle traversing, and distances,
i can use tag but why? its inconvenient, and its not flexible at all, have no idea what will the future hold, but i would rather, have helper class,
handling path finding, then use a single variable in node to do just that.

the algorithm at first was suppose to be an A* algorithm, but we can't know the "direction" of the dest, and cannot estimate how we are progressing,
it is much more like a bfs algorithm, anyway if we would get more info on nodes like ( real world pos, this can be easily modified into an A* )

we loop over neighbours, and add neighbours into the the Open list ( if they are not there or in Closed ), while creating a Path object,
as we loop over the list, we remove the first element and put it into closed list ( by keys ),

note if object A is in the list its impossible that we find a node B that would add the Node A with a shorter path,
as there is no "difference" in edges length, we assume they are all the same distance apart, meaning, if we loop over all the neighbours of B,
they all would either have a distance of B + 1, or less.

as we always take the first element from the list, it would be of distance Current + 1 at most, hence impossible to find a node with less distance then we currently have.

Anyway if we go over neighbours of the current node and we see that the neighbour is in fact the dest, we stop.

GetPath - 
using the FindPath we then traverse back to the dest from source, if no path was found we return an empty list ( not sure whats better null or empty ).

GetPathDistance -
We call findPath and return .distance.

IsConnected - 
we are using bfs algorithm, we loop over neighbours just as in FindPath,
Except when we put object in Open list, we decrease a counter by 1, counter start value = nodes.size() if counter <= 0, we have seen n different nodes,
hence the graph is connected.

WE also perform mathematical checks, from graph theory, for instance we can calculate the minimum amount of edges
the graph needs to have in order to be connected for sure, given the minimum neighbours per node.

this would reduce the time needed for computations A LOT as graphs with a lot of edges are hard core,
and we can skip a lot of them just by checking if the number of edges is big enough.

this little code reduces the compute time by 25%! ( on tests )
anyway that is a very good optimization.

and we also know that if the number of edges is less then n-1 the graph is not connected

GetNode -
we are using hashmap so we return the node from the key.

O(1) complexity

AddNode -
I create a Copy of the node i was given with the same key,  why a copy?
well in graph theory a vertex A might be in X different graphs, with different edges.

hence its not improbable an edge A will be at two different graphs,
we also call it node_data, hence its just a DATA, not the actual vertex, and those the data is unique to the graph, its a property of a graph not vise versa,
so a graph has a vertex with the Key Y,

but the vertex Y might be in different graphs, hence it would have for each of those different NodeData object.

O(1) complexity

HasEdge -
if the node is in the graph we return HasNi() from the node.

O(HasNi())

HasNi(i) , GetNi -
return by key from hashmap -> O(1)


GetNi, GetNodes -
return map.values() -> O(1)

RemoveEdge(a,b) -
if HasEdge(a,b) -> remove the edge.
O(1)

RemoveNode(node) -
if node in graph,
loop over all node neighbours 
then call
RemoveEdge(node.key,neighbour.key)

Complexity - > O(numOfNeighbours) -> O(n) at max. a node cannot have more then n neighbours.
Average -> O(e) where e is the ratio edges/nodes
