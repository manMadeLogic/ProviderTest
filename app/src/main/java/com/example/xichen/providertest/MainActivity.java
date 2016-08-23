package com.example.xichen.providertest;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private String newId;
    private String uri = "content://com.example.xichen.databasetest.provider/book";
//    private ContentResolver resolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button add = (Button) findViewById(R.id.add_data);
        Button update = (Button) findViewById(R.id.update_data);
        Button delete = (Button) findViewById(R.id.delete_data);
        Button query = (Button) findViewById(R.id.query_data);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("name", "A Clash of Kings");
                values.put("author", "GeMartinorge Tolkin");
                values.put("pages", 1046);
                values.put("price", 22.86);
                Uri newUri = getContentResolver().insert(Uri.parse(uri), values);
                if(newUri!=null) {
                    newId = newUri.getPathSegments().get(1);
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put("name", "A Storm of Swords");
                values.put("price", 24.05);
                getContentResolver().update(Uri.parse(uri+newId), values, null, null);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContentResolver().delete(Uri.parse(uri + newId), null, null);
            }
        });

        if (query != null) {
            query.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = getContentResolver().query(Uri.parse(uri), null, null, null, null);
                    if(cursor != null){
                        while (cursor.moveToNext()) {
                            String name = cursor.getString(cursor.getColumnIndex("name"));
                            String author = cursor.getString(cursor.getColumnIndex("author"));
                            int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                            double price = cursor.getDouble(cursor.getColumnIndex("price"));
                            Log.d("MainActivity", "book name is " + name);
                            Log.d("MainActivity", "book author is " + author);
                            Log.d("MainActivity", "book pages is " + pages);
                            Log.d("MainActivity", "book price is " + price);
                        }
                        cursor.close();
                    }
                }
            });
        }
    }
}
