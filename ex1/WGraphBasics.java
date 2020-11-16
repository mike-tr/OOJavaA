package ex1;

import java.io.*;

public abstract class WGraphBasics implements weighted_graph, Serializable {

    WGraphBasics(){

    }

    WGraphBasics(weighted_graph graph){
        init();
        setAsCopy(graph);
    }

    protected abstract void init();

    public abstract WGraphBasics getDeepCopy();

    private void setAsCopy(weighted_graph graph){
        System.out.println("Setting new copy");
        for (node_info node: graph.getV()) {
            addNode(node.getKey());
        }

        for (node_info node: graph.getV()) {
            int key = node.getKey();
            for (node_info ni: graph.getV(node.getKey())) {
                connect(key, ni.getKey(), graph.getEdge(key, ni.getKey()));
            }
        }
        setMC(graph.getMC());
    }

    public WGraphBasics getSerializedCopy(){
        System.out.println("Creating Serialized copy");
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            //De-serialization of object
            ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream in = new ObjectInputStream(bis);
            WGraphBasics copied = (WGraphBasics) in.readObject();

            return copied;
        }catch (Exception ex){
            System.out.println(ex.fillInStackTrace());
        }
        return null;
    }

    protected abstract void setMC(int mc);
}
