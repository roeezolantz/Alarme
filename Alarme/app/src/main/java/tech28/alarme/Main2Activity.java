package tech28.alarme;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final com.github.clans.fab.FloatingActionButton floatingActionButton =
                (com.github.clans.fab.FloatingActionButton) (findViewById(R.id.main_menu_fab));

        final Context cnt = this;

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(cnt, "LOL", Toast.LENGTH_LONG).show();
            }
        });
    }
}
