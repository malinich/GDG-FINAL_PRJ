package com.hycorie.dev.gdg_final_prj;

import java.util.ArrayList;

public class FixedSizeList<T> extends ArrayList<T> {
    private int mCapacity;

    public FixedSizeList(int capacity) {
        super(capacity);
        mCapacity = capacity;

        for (int i = 0; i < capacity; i++) {
            super.add(null);
        }
    }

    public FixedSizeList(T[] initialElements) {
        super(initialElements.length);
        for (T loopElement : initialElements) {
            super.add(loopElement);
        }
    }

    @Override
    public boolean add(T o) {
        if (mCapacity < this.size()){
            throw new UnsupportedOperationException("Elements may not be added to a fixed size List, use set() instead.");
        }
        super.add(o);
        return true;
    }

    @Override
    public void add(int index, T element) {
        if (mCapacity < this.size()){
            throw new UnsupportedOperationException("Elements may not be added to a fixed size List, use set() instead.");
        }
        super.add(index, element);
    }
}