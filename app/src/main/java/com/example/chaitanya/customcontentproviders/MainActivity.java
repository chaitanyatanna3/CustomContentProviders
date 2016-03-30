package com.example.chaitanya.customcontentproviders;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText userName, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userName = (EditText) findViewById(R.id.username);
        address = (EditText) findViewById(R.id.address);

    }

    public void addUser(View view) {

        ContentValues values = new ContentValues();
        values.put(UserProvider.NAME, userName.getText().toString());
        values.put(UserProvider.ADDRESS, address.getText().toString());

        Uri uri = getContentResolver().insert(UserProvider.CONTENT_URI, values);
        Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();
    }

    public void viewDetails(View view) {
        Cursor c = getContentResolver().query(UserProvider.CONTENT_URI, null, null, null, null);
        if(c.moveToFirst()){
            do{
                Toast.makeText(this,
                        " " +  c.getString(c.getColumnIndex( UserProvider.NAME)) +
                                ", " +  c.getString(c.getColumnIndex( UserProvider.ADDRESS)),
                        Toast.LENGTH_LONG).show();
            } while(c.moveToNext());

        }
    }

    public void getAddress(View view) {
        String[] proj = {UserProvider.ADDRESS};
        String[] args = {userName.getText().toString()};
        Cursor c = getContentResolver().query(UserProvider.CONTENT_URI, proj, UserProvider.NAME+" = ?", args, null);
        if(c.moveToFirst()){
            do{
                Toast.makeText(this,
                        " " +  c.getString(c.getColumnIndex( UserProvider.ADDRESS)),
                        Toast.LENGTH_LONG).show();
            } while(c.moveToNext());

        }
    }

    public void getUserId(View view) {
        String[] proj = {UserProvider.UID};
        String[] args = {userName.getText().toString(), address.getText().toString()};
        Cursor c = getContentResolver().query(UserProvider.CONTENT_URI, proj, UserProvider.NAME+" = ? AND "+ UserProvider.ADDRESS+" = ?", args, null);
        if(c.moveToFirst()){
            do{
                Toast.makeText(this,
                        " " +  c.getString(c.getColumnIndex( UserProvider.UID)),
                        Toast.LENGTH_LONG).show();
            } while(c.moveToNext());

        }

    }

    public void updateUser(View view) {
        String currentName = userName.getText().toString();
        String newName = address.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserProvider.NAME, newName);


        String[] whereArgs = {currentName};
        int count = getContentResolver().update(UserProvider.CONTENT_URI, contentValues, UserProvider.NAME + " =? ", whereArgs);

        Toast.makeText(this, ""+count+" users Updated", Toast.LENGTH_LONG).show();

    }

    public void updateUserAddress(View view) {
        String theUser = userName.getText().toString();
        String newAddress = address.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserProvider.ADDRESS, newAddress);

        String[] whereArgs = {theUser};
        int count = getContentResolver().update(UserProvider.CONTENT_URI, contentValues, UserProvider.NAME + " =? ", whereArgs);

        Toast.makeText(this, ""+count+" users Updated", Toast.LENGTH_LONG).show();
    }

    public void deleteUser(View view) {

        String theUser = userName.getText().toString();

        String[] whereArgs = {theUser};
        int count = getContentResolver().delete(UserProvider.CONTENT_URI, UserProvider.NAME + "= ?", whereArgs);

        Toast.makeText(this, ""+count+" users Deleted", Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
