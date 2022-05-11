package com.mis.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        listView.setAdapter(new MyBaseAdapter(this));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0)
                {
                    startActivity(new Intent(MainActivity.this,FlightsAvailableActivity.class));
//                    startActivity(new Intent(MainActivity.this, MapActivity.class));
                }
                if(position == 1)
                {
                    startActivity(new Intent(MainActivity.this, SelectedFlightsActivity.class));
                }

            }
        });
    }

    public class MyBaseAdapter extends BaseAdapter {
        private Context context;

        public MyBaseAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = LayoutInflater.from(context).inflate(R.layout.item_main, parent, false);

            TextView textViewTitle = (TextView) convertView.findViewById(R.id.title);
            ImageView imageView = convertView.findViewById(R.id.image);

            if (position==0)
            {
                textViewTitle.setText("Viajes Disponibles");
                imageView.setImageResource(R.drawable.ic_plane);
            }
            if (position==1)
            {
                textViewTitle.setText("Viajes Seleccionados");
                imageView.setImageResource(R.drawable.ic_dart);
            }
            return convertView;
        }
    }
}





