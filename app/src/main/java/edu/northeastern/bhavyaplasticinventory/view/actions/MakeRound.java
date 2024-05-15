package edu.northeastern.bhavyaplasticinventory.view.actions;

import edu.northeastern.bhavyaplasticinventory.view.activities.ShapeChangeAction;

public class MakeRound<ImageView extends android.widget.ImageView> implements ShapeChangeAction<ImageView> {

    @Override
    public int getViewId(ImageView imageView) {

        return 0;
    }

    @Override
    public void changeShapeTo(ImageView imageView, int radius) {

    }
}
