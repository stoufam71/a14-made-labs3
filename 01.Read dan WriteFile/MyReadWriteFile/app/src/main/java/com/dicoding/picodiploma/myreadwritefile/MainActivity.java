package com.dicoding.picodiploma.myreadwritefile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnNew;
    Button btnOpen;
    Button btnSave;
    EditText editContent;
    EditText editTitle;

    File path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNew = (Button) findViewById(R.id.button_new);
        btnOpen = (Button) findViewById(R.id.button_open);
        btnSave = (Button) findViewById(R.id.button_save);
        editContent = (EditText) findViewById(R.id.edit_file);
        editTitle = (EditText) findViewById(R.id.edit_title);

        btnNew.setOnClickListener(this);
        btnOpen.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        path = getFilesDir();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button_new:
                newFile();
                break;
            case R.id.button_open:
                openFile();
                break;
            case R.id.button_save:
                saveFile();
                break;
        }
    }

    /**
     * Clear semua data yang sudah ditampilkan
     */
    public void newFile() {

        editTitle.setText("");
        editContent.setText("");

        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method untuk load data
     *
     * @param title nama file
     */
    private void loadData(String title) {
        String text = FileHelper.readFromFile(this, title);
        editTitle.setText(title);
        editContent.setText(text);
        Toast.makeText(this, "Loading " + title + " data", Toast.LENGTH_SHORT).show();
    }

    public void openFile() {
        showList();
    }

    /**
     * Method untuk menampilkan semua file yang ada
     */
    private void showList() {
        final ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, path.list());

        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih file yang diinginkan");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                loadData(items[item].toString());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Method untuk save data, nama file akan diambil dari editTitle
     */
    public void saveFile() {
        if (editTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Title harus diisi terlebih dahulu", Toast.LENGTH_SHORT).show();
        } else {
            String title = editTitle.getText().toString();
            String text = editContent.getText().toString();
            FileHelper.writeToFile(title, text, this);
            Toast.makeText(this, "Saving " + editTitle.getText().toString() + " file", Toast.LENGTH_SHORT).show();
        }
    }

}