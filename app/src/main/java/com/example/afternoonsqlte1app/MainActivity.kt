package com.example.afternoonsqlte1app

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    lateinit var edtName:EditText
    lateinit var edtEmail:EditText
    lateinit var edtidNumber:EditText
    lateinit var btnSave:Button
    lateinit var btnView:Button
    lateinit var btnDelete:Button
    lateinit var db:SQLiteDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtName = findViewById(R.id.mEdtName)
        edtEmail = findViewById(R.id.mEdtEmail)
        edtidNumber = findViewById(R.id.mEdtIdNumber)
        btnSave = findViewById(R.id.mBtnSave)
        btnView = findViewById(R.id.mBtnView)
        btnDelete = findViewById(R.id.mBtnDelete)
        // Create a database called eMobilisDB
        db = openOrCreateDatabase("eMobilisDB", Context.MODE_PRIVATE,null)
        // Create a table inside the database
        db.execSQL("CREATE TABLE IF NOT EXISTS Users(jina VARCHAR, arafa VARCHAR, kitambulisho VARCHAR)")
        // Set onclick listeners to the buttons
        btnSave.setOnClickListener {
            var name = edtName.text.toString().trim()
            var email = edtName.text.toString().trim()
            var idNumber = edtName.text.toString().trim()
            // Check if the user is submitting empty fields
            if (name.isEmpty() || email.isEmpty() || idNumber.isEmpty()){
                // Use the message function to display the message
                message("EMPTY FIELDS!", "Please fill all inputs")
            }else{
                // Procede to save data
                db.execSQL("INSERT INTO Users VALUES('"+name+"','"+email+"','"+idNumber+"')")
                clear()
                message("SUCCESS!!","User saved successfully!!!")
            }

        }
        btnView.setOnClickListener {
            var cursor = db.rawQuery("SELECT * FROM Users ", null)
            // Check if there's any users in the database
            if (cursor.count == 0){
                message("NO RECORDS!!!", "Sorry, no users were found!!!")
            }else{
                // Use a string buffer to append all users retrieved using a loop
                var buffer = StringBuffer()
                while (cursor.moveToNext()){
                    var retrievedName = cursor.getString(0)
                    var retrievedEmail = cursor.getString(1)
                    var retrievedIdNumber = cursor.getString(2)
                    buffer.append(retrievedName+"\n")
                    buffer.append(retrievedEmail+"\n")
                    buffer.append(retrievedIdNumber+"\n\n")
                }
                message("SUERS",buffer.toString())
            }

        }
        btnDelete.setOnClickListener {
            var idNumber = edtidNumber.text.toString().trim()
            if (idNumber.isEmpty()){
                message("EMPTY FIELD!!!", "Please fill the ID field")
            }else{
                var cursor = db.rawQuery("SELECT * FROM Users WHERE kitambulisho='"+idNumber+"'", null)
                if (cursor.count == 0){
                    message("NO RECORD!", "Sorry, no user found!!!")
                }else{
                    db.execSQL("DELETE FROM Users WHERE kitambulisho='"+idNumber+"'")
                    clear()
                    message("SUCCESS!!!", "User deleted succesfully!!!")
                }
            }

        }
    }
    fun message(title:String, message:String){
        var alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("Cancel",null)
        alertDialog.create().show()

    }
    fun clear(){
        edtName.setText("")
        edtEmail.setText("")
        edtidNumber.setText("")
    }
}