package io.jatoms.jmh;

public interface Receiver {
    public int receive(int value, int calldepth);
}