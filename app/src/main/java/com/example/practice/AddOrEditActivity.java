package com.example.practice;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practice.database.DatabaseHelper;
import com.example.practice.model.Student;

public class AddOrEditActivity extends Activity {
    private TextView tvAddOrEdit;
    private EditText txtId;
    private EditText txtName;
    private EditText txtAge;
    private EditText txtAddress;
    private Button btnAddOrEdit;
    private DatabaseHelper dbHelper;
    private String action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit);
        tvAddOrEdit = (TextView)findViewById(R.id.tvAddOrEdit);
        txtId = (EditText)findViewById(R.id.txtId);
        txtName = (EditText)findViewById(R.id.txtName);
        txtAge = (EditText)findViewById(R.id.txtAge);
        txtAddress = (EditText)findViewById(R.id.txtAddress);
        btnAddOrEdit = (Button)findViewById(R.id.btnAddOrEdit);
        //Get action pass by main activity
        action =  getIntent().getExtras().getString("Action");
        tvAddOrEdit.setText(action);
        btnAddOrEdit.setText(action);
        dbHelper = new DatabaseHelper(getApplicationContext());
        txtId.setEnabled(true);

        if("Edit".equals(action)){
            int productId = getIntent().getExtras().getInt("Id");
            Student product = dbHelper.getProductById(productId);
            txtId.setText(String.valueOf(product.getId()));
            txtId.setEnabled(false);
            txtName.setText(product.getName());
            txtAge.setText(String.valueOf(product.getAge()));
            txtAddress.setText(product.getAddress());
        }
        btnAddOrEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Student product = new Student(
                        Integer.parseInt(txtId.getText().toString()),
                        txtName.getText().toString(),
                        Integer.parseInt(txtAge.getText().toString()),
                        txtAddress.getText().toString()
                );
                if("Edit".equals(action)) {
                    long result = dbHelper.updateProduct(product);
                    if(result>0){
                        Toast.makeText(getApplicationContext(),"Updated", Toast.LENGTH_SHORT).show();
                        //back to main activity
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"Update failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    long result = dbHelper.addProduct(product);
                    if(result>0){
                        Toast.makeText(getApplicationContext(),"Added", Toast.LENGTH_SHORT).show();
                        //back to main activity
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"Add failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
