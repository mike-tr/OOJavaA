package ex1;

public class ArrHeap<T extends INode> {
    //private ArrayList<T> items = new ArrayList<>();
    private T[] items;
    int boundUp = 0;
    int used = 0;
    public ArrHeap() {
        items = (T[])new INode[10000];
    }

    public ArrHeap(int size){
        items = (T[])new INode[size];
    }



    public T lookFirst(){
        return items[0];
        //return items.get(0);
    }

    @Override
    public String toString() {
        return items.toString();
    }

    public int size(){
        return used;
    }

    public T getAt(int index){
        return items[index];
        //return items.get(index);
    }

    public void add(T item, double priority){
        // for now no protection at all
        if(item.getHeap() != null){
            return;
        }

        //item.setHeap(this);
        item.setPriority(priority);
        item.setIndex(used);

        items[size()] = item;
        used++;

        //items.add(item);
        boundUp = (used - 1) / 2 - 1;

        heapifyUp(item);
    }

    public T poll(){
        swap(getAt(0), getAt(used - 1));
        T first = items[used - 1];
        used--;
        if(size() > 0) {
            heapifyDown(getAt(0));
        }
        first.setHeap(null);
        boundUp = (used - 1) / 2 - 1;
        return first;
    }

    public void addOrUpgrade(T target, double priority){
        if(tryUpgrade(target, priority) == -1){
            add(target, priority);
        }
    }

    public int tryUpgrade(T target, double priority){
        if(target.getHeap() != null){
            return -1;
        }else if(target.getPriority() > priority){
            target.setPriority(priority);
            heapifyUp(target);
            return 1;
        }
        return 0;
    }

    private void swap(T node1, T node2){
        int index = node1.getIndex();
        int index2 = node2.getIndex();

        node1.setIndex(index2);
        node2.setIndex(index);

        items[index] = node2;
        items[index2] = node1;
    }

    private void heapifyUp(T target){
        int index = target.getIndex();
        if(index == 0){
            return;
        }

        T parent = getAt((target.getIndex() - 1) / 2);
        if(target.smaller(parent)){
            swap(target, parent);
//            items.set(parent.getIndex(), target);
//            items.set(index, parent);
//            target.setIndex(parent.getIndex());
//            parent.setIndex(index);

            heapifyUp(target);
        }
    }

    private void heapifyDown(T target){
        int index = target.getIndex();
        if(index > boundUp){
            return;
        }


        T left = getAt(index * 2 + 1);
        if(index *2 + 3 > used){
            if(left.smaller(target)){
                swap(target, left);
            }
            return;
        }
        T right = getAt(index * 2 + 2);
        if(left.smaller(target)){
            if(left.smaller(right)){
                swap(target, left);
                heapifyDown(target);
            }else{
                swap(target, right);
                heapifyDown(target);
            }
        }else if(right.smaller(target)){
            swap(target, right);
            heapifyDown(target);
        }
    }
}
