package com.league.otto;

/**
 * Created by pfy on 2016/1/19.
 */
public class DeleteIndexEvent {
    private int index;

    public DeleteIndexEvent(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
