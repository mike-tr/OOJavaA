package ex1;

public abstract class INode {
    private int index = -1;
    private Heap heap;

    public Heap getHeap() {
        return heap;
    }

    public void setHeap(Heap heap) {
        this.heap = heap;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public boolean smaller(INode node){
        return getPriority() < node.getPriority();
    }

    @Override
    public String toString() {
        return "" + getPriority();
    }

    public abstract double getPriority();
    public abstract void setPriority(double priority);
}
