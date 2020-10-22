package com.company;

import ex0.node_data;

public interface CopyableNode extends node_data {
    CopyableNode getDeepCopy();
}
