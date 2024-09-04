package edu.northeastern.MrManage.doa.interfaces;

@FunctionalInterface
public interface CallRestApi<U,V> {
    V callApi(U u);
}
