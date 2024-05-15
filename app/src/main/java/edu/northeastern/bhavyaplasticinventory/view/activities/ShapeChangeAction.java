package edu.northeastern.bhavyaplasticinventory.view.activities;

public interface ShapeChangeAction<E> extends ActionOnView<E>{
    void changeShapeTo(E e, int radius);
}
