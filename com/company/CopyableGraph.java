package com.company;

import ex0.graph;

public interface CopyableGraph extends graph {
    CopyableGraph getDeepCopy();
}
