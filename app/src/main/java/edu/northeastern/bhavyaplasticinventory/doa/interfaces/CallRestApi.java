package edu.northeastern.bhavyaplasticinventory.doa.interfaces;

@FunctionalInterface
public interface CallRestApi<U,V> {
    V callApi(U u);
}
