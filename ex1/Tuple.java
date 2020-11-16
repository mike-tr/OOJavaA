package ex1;

import java.io.Serializable;

public class Tuple implements Serializable {
    int x;
    int y;

    public Tuple(int first, int second){
        this.x = first;
        this.y = second;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + this.x;
        hash = 31 * hash + this.y;
        return hash;
    }

    @Override
    public boolean equals(Object other){
        if (other == this) {
            return true;
        }

        if(other == null){
            return false;
        }

        if (!(other instanceof Tuple)) {
            return true;
        }

        Tuple that =(Tuple) other;
        return (that.x == x) && (that.y == y);
    }
}
