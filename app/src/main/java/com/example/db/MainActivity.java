package com.example.db;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    myDBHelper myHelper;
    EditText edtName, edtNumber, edtNameResult, edtNumberResult;
    Button btnInit, btnInsert, btnSelect;
    SQLiteDatabase sqIDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = (EditText) findViewById(R.id.edtName);
        edtNumber = (EditText) findViewById(R.id.edtNumber);
        edtNameResult = (EditText) findViewById(R.id.edtNameResult);
        edtNumberResult = (EditText) findViewById(R.id.edtNumberResult);

        btnInit = (Button) findViewById(R.id.btnInit);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnSelect = (Button) findViewById(R.id.btnSelect);

        myHelper = new myDBHelper(this);
        btnInit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                sqIDB = myHelper.getWritableDatabase();
                myHelper.onUpgrade(sqIDB,1,2);

                sqIDB.close();
            }
        });

        btnInsert.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                sqIDB = myHelper.getWritableDatabase();
                sqIDB.execSQL("INSERT INTO groupTBL VALUES('"+edtName.getText().toString()+"',"+edtNumber.getText().toString()+");");

                edtName.setText("");
                edtNumber.setText("");
                sqIDB.close();
                Toast.makeText(getApplicationContext(),"입력됨",Toast.LENGTH_SHORT).show();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                sqIDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqIDB.rawQuery("SELECT * FROM groupTBL;",null);

                String strNames = "그룹이름"+"\r\n"+"----------"+"\r\n";
                String strNumbers = "인원"+"\r\n"+"----------"+"\r\n";

                while(cursor.moveToNext()){
                    strNames += cursor.getString(0)+"\r\n";
                    strNumbers += cursor.getString(1)+"\r\n";
                }
                edtNameResult.setText(strNames);
                edtNumberResult.setText(strNumbers);

                cursor.close();
                sqIDB.close();
            }
        });
    }
}