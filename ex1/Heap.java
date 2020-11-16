package ex1;

import java.util.ArrayList;

public class Heap<T> {
    private class Node{
        T item;
        double priority;

        Node(T item, double priority){
            this.item = item;
            this.priority = priority;
        }
    }

    private ArrayList<Node> items = new ArrayList<>();
    public Heap(){ }

    public void add(T item, double priority){
        items.add(new Node(item, priority));
        heapifyUp(items.size() - 1);
    }

    public Node remove(){
        return null;
    }

    private void heapifyUp(int index){
        if(index == 0){
            return;
        }
        int parent = (index - 1) / 2;

        if(items.get(index).priority < items.get(parent).priority){
            Node temp = items.get(parent);
            items.set(parent, items.get(index));
            items.set(index, temp);

            heapifyUp(index);
        }
    }

}
