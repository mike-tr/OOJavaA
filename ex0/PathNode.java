package ex0;

public class PathNode implements Comparable<PathNode> {
    private node_data node;
    private PathNode neighbour;
    private int distance;
    private int key;

    PathNode(node_data node){
        this.node = node;
        this.key = node.getKey();
        distance = 0;
    }

    PathNode(node_data node, PathNode root){
        this.node = node;
        this.key = node.getKey();
        this.neighbour = root;
        distance = root.distance + 1;
    }

    public boolean tryNewPath(PathNode neighbour){
        // this method returns true if the path from the given node is shorter
        // actually this never can happen but whatever

        if(distance > neighbour.distance + 1){
            System.out.println("from : " + distance + " to " + neighbour.distance + 1);
            distance = neighbour.distance + 1;
            this.neighbour = neighbour;
            return true;
        }
        return false;
    }

    public PathNode getParent(){
        return neighbour;
    }

    public node_data getNode(){
        return node;
    }

    public int getKey(){
        return key;
    }

    public void setDistance(int distance){
        this.distance = distance;
    }

    public int getDistance(){
        return distance;
    }

    @Override
    public boolean equals(Object other){
        if (other == this) {
            return true;
        }

        if(other instanceof Integer){
            return this.key == (Integer) other;
        }

        if (!(other instanceof PathNode)) {
            return false;
        }
        return this.key == ((PathNode) other).key;
    }

    @Override
    public int compareTo(PathNode o) {
        return this.distance - o.distance;
    }
}
