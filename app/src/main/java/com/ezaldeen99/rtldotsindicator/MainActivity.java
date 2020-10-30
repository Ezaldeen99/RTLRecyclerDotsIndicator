package com.ezaldeen99.rtldotsindicator;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ezaldeen99.recyclerdotsdecortation.CirclePagerIndicatorDecoration;

public class MainActivity extends AppCompatActivity {
    boolean rightOrLeft = false;
    RecyclerView recyclerView;
    CirclePagerIndicatorDecoration itemDecoration;
    Button changeDirection;
    ConstraintLayout layout;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//       layout binding
        recyclerView = findViewById(R.id.recycler_view);
        changeDirection = findViewById(R.id.layout_direction);
        layout = findViewById(R.id.main_layout);

//      init views
        itemDecoration = new CirclePagerIndicatorDecoration(View.LAYOUT_DIRECTION_RTL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(new Adapter(this));

//     change layout direction
        changeDirection.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                changeLang();
            }
        });
    }

    public void changeLang() {
        int layoutDirection = rightOrLeft ? View.LAYOUT_DIRECTION_RTL : View.LAYOUT_DIRECTION_LTR;
        changeDirection.setText(rightOrLeft ? "RTL" : "LTR");

        layout.setLayoutDirection(layoutDirection);
        //set new decoration
        recyclerView.removeItemDecoration(itemDecoration);
        itemDecoration = new CirclePagerIndicatorDecoration(layoutDirection);
        recyclerView.addItemDecoration(itemDecoration);
        rightOrLeft = !rightOrLeft;
    }
}
