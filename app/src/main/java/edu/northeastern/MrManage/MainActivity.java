package edu.northeastern.MrManage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.imageview.ShapeableImageView;

import dagger.hilt.android.AndroidEntryPoint;

import edu.northeastern.MrManage.view.activity.ActiveOrderActivity;
import edu.northeastern.MrManage.view.activity.DeliveryHistoryActivity;
import edu.northeastern.MrManage.view.activity.ManageUserActivity;
import edu.northeastern.MrManage.view.activity.ReceivedOrderActivity;
import edu.northeastern.MrManage.view.activity.StocksHistoryActivity;
import edu.northeastern.MrManage.view.dialogs.AddDeliveryDialog;
import edu.northeastern.MrManage.view.dialogs.AddOrderDialog;
import edu.northeastern.MrManage.view.dialogs.AddStockHistoryDialog;
import edu.northeastern.MrManage.view.dialogs.AddUserDialog;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setOnClickListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnClickListener() {
        CardView customerLayout = findViewById(R.id.card_view_buyer);
        ShapeableImageView addCustomerButton = customerLayout.findViewById(R.id.shapeAble_image_add_purchaser_main);
        setOnTouchAnimation(addCustomerButton);
        addCustomerButton.setOnClickListener(v -> {
            View rootView = getWindow().getDecorView().getRootView();
            AddUserDialog.showAddUserDialog(this, rootView.getWidth(), true);
        });

        ShapeableImageView manageCustomerButton = customerLayout.findViewById(R.id.shapeAble_image_in_manage_customer_main);
        setOnTouchAnimation(manageCustomerButton);
        manageCustomerButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ManageUserActivity.class);
            intent.putExtra("isCustomer", true);
            startActivity(intent);
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setOnTouchAnimation(ShapeableImageView shapeableImageView) {
        shapeableImageView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Set the color or perform any animation when touched
                        shapeableImageView.setColorFilter(R.color.button_order_color, PorterDuff.Mode.SRC_ATOP);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Reset the color or animation when touch released
                        shapeableImageView.clearColorFilter();
                        break;
                }
                return false; // Return true to indicate that the touch event has been consumed
            }
        });
    }

    public void createOrderClicked(View view) {
        setOnTouchAnimation((ShapeableImageView) view);
        View rootView = getWindow().getDecorView().getRootView();
        AddOrderDialog addOrderDialog = new AddOrderDialog(this);
        addOrderDialog.showAddOrderDialog(this, rootView.getWidth());
    }

    public void createDeliveryClicked(View view) {
        setOnTouchAnimation((ShapeableImageView) view);
        View rootView = getWindow().getDecorView().getRootView();
        AddDeliveryDialog addDeliveryDialog = new AddDeliveryDialog(this);
        AddDeliveryDialog.addDeliveryDialog(MainActivity.this, rootView.getWidth());
    }


    public void activeOrderActivityOnClick(View view) {
        setOnTouchAnimation((ShapeableImageView) view);
        Intent intent = new Intent(MainActivity.this, ActiveOrderActivity.class);
        startActivity(intent);
    }

    public void openAddManufacturerDialog(View view) {
        setOnTouchAnimation((ShapeableImageView) view);
        View rootView = getWindow().getDecorView().getRootView();
        AddUserDialog.showAddUserDialog(this, rootView.getWidth(), false);
    }

    public void manageManufacturerButton(View view) {
        Intent intent = new Intent(MainActivity.this, ManageUserActivity.class);
        intent.putExtra("isCustomer", false);
        startActivity(intent);
    }

    public void viewDeliveryHistoryClicked(View view){
        Intent intent = new Intent(MainActivity.this, DeliveryHistoryActivity.class);
        startActivity(intent);
    }

    public void startReceivedOrderActivity(View view){
        Intent intent = new Intent(MainActivity.this, ReceivedOrderActivity.class);
        startActivity(intent);
    }

    public void addOldStockClicked(View view) {
        setOnTouchAnimation((ShapeableImageView) view);
        View rootView = getWindow().getDecorView().getRootView();
        AddStockHistoryDialog addStockHistoryDialog = new AddStockHistoryDialog(this);
        addStockHistoryDialog.addStockHistoryDialog(this,rootView.getWidth());
    }

    public void onClickOldStockUpdateHistory(View view) {
        Intent intent = new Intent(MainActivity.this, StocksHistoryActivity.class);
        startActivity(intent);
    }


}