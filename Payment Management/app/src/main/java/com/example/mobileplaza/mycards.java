package com.example.mobileplaza;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileplaza.database.DBHelper;

import java.util.List;


public class mycards extends AppCompatActivity {

    EditText et_cardname6;
    EditText et_cardnumber6;     //make references for input data,refernce name for any
    EditText et_cardexp6;
    EditText et_cardcvv6;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycards);


        et_cardname6=findViewById(R.id.et_cardname6);       //find by id,right side et_cardname original one other is varibale
        et_cardnumber6=findViewById(R.id.et_cardnumber6);
        et_cardexp6=findViewById(R.id.et_cardexp6);
        et_cardcvv6=findViewById(R.id.et_cardcvv6);

    }

    public void saveCard(View view){      //insert function in frontend
        String cardName = et_cardname6.getText().toString();
        String cardNum = et_cardnumber6.getText().toString();      //creating string variable
        String cardExp = et_cardexp6.getText().toString();
        String cardCvv = et_cardcvv6.getText().toString();
        DBHelper dbHelper = new DBHelper(this);        //create object from DBhelper class

        if(cardName.isEmpty()||cardNum.isEmpty()||cardExp.isEmpty()||cardCvv.isEmpty()){    //check if someones data are the empty
            Toast.makeText(this, "Enter values", Toast.LENGTH_LONG).show();       //yes
        }else{
            long inserted = dbHelper.addCard(cardName,cardNum,cardExp,cardCvv);      //add data, getting how many rows are iserted

            if(inserted>0){
                Toast.makeText(this, "Data Inserted successfully", Toast.LENGTH_LONG).show();   //if not adeed 0 rows
                et_cardname6.setText("");
                et_cardnumber6.setText("");
                et_cardexp6.setText("");
                et_cardcvv6.setText("");
            }else{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG).show();   //adde 0 rows
            }
        }
    }

                                                //view data,gets data to alert box
    public void viewAll(View view){
        DBHelper dbHelper = new DBHelper(this);

        List info = dbHelper.readAll();        //before gets data
                                                                             //this will convert in to the infor array
        String[] infoArray = (String[]) info.toArray(new String[0]);               //string array displaying data one by one

        AlertDialog.Builder builder = new AlertDialog.Builder(this);    //create alert box
        builder.setTitle("Cards Details");     //set title

        builder.setItems(infoArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {            //click index is i

                String cardName = infoArray[i].split(":")[0];
                String cardNum = infoArray[i].split(":")[1];          //split values auto by when click
                String cardExp = infoArray[i].split(":")[2];
                String cardCvv = infoArray[i].split(":")[3];

                et_cardname6.setText(cardName);
                et_cardnumber6.setText(cardNum);             //split values auto by when click
                et_cardexp6.setText(cardExp);
                et_cardcvv6.setText(cardCvv);

            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();        //deafuult dialog box, this like tosat msg output


    }

                                      //Delete in FN
    public void deleteCard(View view){
        DBHelper dbHelper = new DBHelper(this);
        String cardName = et_cardname6.getText().toString();   //search by cardname

        if(cardName.isEmpty()){                                             //if user did not select card
            Toast.makeText(this, "Select a Card", Toast.LENGTH_SHORT).show();
        }else{
            dbHelper.deleteInfo(cardName);                 //delete records
            Toast.makeText(this, cardName+"Card Deleted", Toast.LENGTH_SHORT).show();

        }
    }
                                    //update in FN
    public void updateCard(View view){
        DBHelper dbHelper = new DBHelper(this);


        String cardName = et_cardname6.getText().toString();
        String cardNum= et_cardnumber6.getText().toString();
        String cardExp = et_cardexp6.getText().toString();
        String cardCvv = et_cardcvv6.getText().toString();

        if(cardName.isEmpty()||cardNum.isEmpty()||cardExp.isEmpty()||cardCvv.isEmpty()){
            Toast.makeText(this, "Select or add card", Toast.LENGTH_SHORT).show();
        }else{
            dbHelper.updateInfo(view,cardName, cardNum, cardExp, cardCvv);

            et_cardname6.setText("");
            et_cardnumber6.setText("");
            et_cardexp6.setText("");
            et_cardcvv6.setText("");
        }
    }

}