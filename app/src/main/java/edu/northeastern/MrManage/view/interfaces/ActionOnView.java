package edu.northeastern.MrManage.view.interfaces;

/**
 * This interface represents the Action applied on a View action
 * E This is a generic interface since view can be of many types so this interface should work with
 * buttons, imageView, and etc.
 */
public interface ActionOnView<E> {

    /**
     * This method is used to return the view id assigned.
     *
     * @param e integer value
     * @return
     */
    int getViewId(E e);
}
