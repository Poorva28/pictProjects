package com.example.e_notice_board;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.*;
import android.view.*;
import android.content.*;
import android.net.*;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.*;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.*;

public class TeacherUploadFile extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST=1;
    Spinner year,section,lab;
    FirebaseStorage filestore;
   EditText filename;
   FirebaseDatabase databaseReference;
   ProgressDialog pd;
   Uri file;
   String file_name;
   String sel_year,sel_section,sel_lab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_upload_file);
        year=findViewById(R.id.year);
        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    sel_year=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        section=findViewById(R.id.section);
        section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sel_section=adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        lab=findViewById(R.id.lab);
       lab.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               sel_lab=adapterView.getItemAtPosition(i).toString();
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
        filename=findViewById(R.id.name);
        pd=new ProgressDialog(this);


        ArrayAdapter<String> yearadapter=new ArrayAdapter<String>(TeacherUploadFile.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.year));
        yearadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(yearadapter);

        ArrayAdapter<String> sectionadapter=new ArrayAdapter<String>(TeacherUploadFile.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.section_upload));
        sectionadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        section.setAdapter(sectionadapter);

        ArrayAdapter<String> studlabs=new ArrayAdapter<>(TeacherUploadFile.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.labs_upload));
        studlabs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lab.setAdapter(studlabs);

        filestore=FirebaseStorage.getInstance();
        databaseReference=FirebaseDatabase.getInstance();

    }

    public void uploadFile(View v)
    {
        String upload_year,upload_year_sec,upload_lab_sec;
        openFile();
        upload_year=sel_year;
        upload_year_sec=sel_year.concat(sel_section);
        upload_lab_sec=sel_lab.concat(sel_section);
        file_name=filename.getText().toString();
        pd.setMessage("Uploading");
        if(sel_section.equals("none") && sel_lab.equals("none"))
        {
           // Toast.makeText(this,"Inside",Toast.LENGTH_SHORT).show();
            upload(upload_year,"none","none");
        }
       else if(sel_lab.equals("none") &&!sel_section.equals("none") )
        {
            upload("none",upload_year_sec,"none");
        }
        else if(sel_section.equals("none")&&!sel_lab.equals("none"))
        {
            Toast.makeText(this,"Enter the section",Toast.LENGTH_LONG);
            return;
        }
        else
        {
            upload("none","none",upload_lab_sec);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            file=data.getData();
            //Toast.makeText(TeacherUploadFile.this,"File opened successfully",Toast.LENGTH_LONG).show();
        }
    }

    public void openFile()
    {
        Intent i=new Intent();
        i.setType("file/*");
        i.setAction(i.ACTION_GET_CONTENT);
        startActivityForResult(i,PICK_IMAGE_REQUEST);
       // Toast.makeText(this,"Opened Successfully",Toast.LENGTH_SHORT).show();
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    public void upload(final String upload_year,final String upload_year_sec,final String upload_lab_sec)
    {
        pd.show();
        if(file!=null) {
            if (!upload_year.equals("none")) {
                filestore.getReference(upload_year).child(file_name + "." + getFileExtension(file)).putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        FileData fd = new FileData(taskSnapshot.getStorage().getDownloadUrl().toString());
                        pd.dismiss();
                        Toast.makeText(TeacherUploadFile.this,"file uploaded successfully",Toast.LENGTH_SHORT).show();
                        //Toast.makeText(TeacherUploadFile.this, "created file object ", Toast.LENGTH_SHORT).show();
                     /*   try {
                            databaseReference.getReference("files").child(upload_year).child(file_name + "." + getFileExtension(file)).setValue(fd);
                           // pd.dismiss();
                            Toast.makeText(TeacherUploadFile.this,"file uploaded successfully",Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e)
                        {
                            pd.dismiss();
                            Toast.makeText(TeacherUploadFile.this,"exception occured",Toast.LENGTH_SHORT);
                        }*/

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(TeacherUploadFile.this, "Upload Unsuccessful", Toast.LENGTH_LONG);
                    }
                });
            }
        else if(!upload_year_sec.equals("none"))
        {

            filestore.getReference(upload_year_sec).child(file_name+"."+getFileExtension(file)).putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    FileData fd=new FileData(taskSnapshot.getStorage().getDownloadUrl().toString());
                    databaseReference.getReference("files").child(upload_year_sec).child(file_name+"."+getFileExtension(file)).setValue(fd);
                    pd.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(TeacherUploadFile.this,"Upload Unsuccessful",Toast.LENGTH_LONG);
                }
            });
        }
        else
        {
            filestore.getReference(upload_lab_sec).child(file_name+"."+getFileExtension(file)).putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                    FileData fd=new FileData(taskSnapshot.getStorage().getDownloadUrl().toString());
                    databaseReference.getReference("files").child(upload_lab_sec).child(file_name+"."+getFileExtension(file)).setValue(fd);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(TeacherUploadFile.this,"Upload Unsuccessful",Toast.LENGTH_LONG);
                }
            });
        }
        }
        else
        {
            pd.dismiss();
            Toast.makeText(this,"please select one image",Toast.LENGTH_SHORT);
            return;
        }
    }
}
