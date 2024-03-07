package edu.northeastern.bhavyaplasticinventory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import android.widget.Toast;

import com.google.android.material.imageview.ShapeableImageView;

import edu.northeastern.bhavyaplasticinventory.view.AddCustomerActivity;

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
        addCustomerButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Set the color or perform any animation when touched
                        addCustomerButton.setColorFilter(R.color.button_order_color, PorterDuff.Mode.SRC_ATOP);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Reset the color or animation when touch released
                        addCustomerButton.clearColorFilter();
                        break;
                }
                return false; // Return true to indicate that the touch event has been consumed
            }
        });
        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCustomerActivity.class);
                startActivity(intent);
            }
        });
    }

}