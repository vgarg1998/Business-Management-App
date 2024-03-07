package edu.northeastern.bhavyaplasticinventory.view.interfaces;

public interface ShapeChangeAction<E> extends ActionOnView<E>{
    void changeShapeTo(E e, int radius);
}
