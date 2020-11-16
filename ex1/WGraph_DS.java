package ex1;

public class WGraph_DS extends WGraph_DS4 {
    // nothing in here, this is just a fast way to switch between GRAPHS.


    @Override
    public boolean equals(Object obj) {
        if(obj == this){
            return true;
        }

        if(obj == null){
            return false;
        }

        if(obj instanceof weighted_graph){
            return obj.toString().equals(this.toString());
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
