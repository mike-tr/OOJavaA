package ex0;

import java.util.Collection;

/**
 * This interface represents the set of operations applicable on a 
 * node (vertex) in an (undirectional) unweighted graph.
 * @author boaz.benmoshe
 *
 */
public interface node_data {
	/**
	 * Return the key (id) associated with this node.
	 * Note: each node_data should have a unique key.
	 * @return
	 */
	int getKey();

    /**
	 * This method returns a collection with all the Neighbor nodes of this node_data */
	Collection<node_data> getNi();

    /**
     * return true iff this<==>key are adjacent, as an edge between them.
	 * @param key
     * @return
     */
	boolean hasNi(int key);
	/** This method adds the node_data (t) to this node_data.*/
	void addNi(node_data t);

    /**
     * Removes the edge this-key,
	 * @param node
	 */
	void removeNode(node_data node);
	/**
	 * return the remark (meta data) associated with this node.
	 * @return
	 */
	String getInfo();
	/**
	 * Allows changing the remark (meta data) associated with this node.
	 * @param s
	 */
	void setInfo(String s);
	/**
	 * Temporal data (aka color: e,g, white, gray, black) 
	 * which can be used be algorithms 
	 * @return
	 */
	int getTag();
	/** 
	 * Allow setting the "tag" value for temporal marking an node - common 
	 * practice for marking by algorithms.
	 * @param t - the new value of the tag
	 */
	void setTag(int t);
}
