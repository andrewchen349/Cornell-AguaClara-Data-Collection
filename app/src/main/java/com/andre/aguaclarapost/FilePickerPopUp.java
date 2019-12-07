package com.andre.aguaclarapost;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class FilePickerPopUp extends Activity {
    private ImageView addfilesstor;
    private ImageView addfilesdriv;
    private int PICK_IMAGE_REQUEST_CODE = 1;
    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_popup_layout);

        dialogPopup();

        //Find XML Components
        addfilesstor = (ImageView)findViewById(R.id.addfilestor);
        addfilesdriv = (ImageView)findViewById(R.id.addfilesdrive);
        addfilesstor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openfiles = new Intent(Intent.ACTION_GET_CONTENT);
                //openfiles.setType("image/*");
                openfiles.setType("*/*");
                openfiles.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(openfiles, PICK_IMAGE_REQUEST_CODE);
            }
        });

        mStorageRef = FirebaseStorage.getInstance().getReference("Uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Uploads");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode);
        if(requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data!= null && data.getData() != null){
            mImageUri = data.getData();
            uploadFile();
            Intent i = new Intent(FilePickerPopUp.this, MainActivity.class);
            i.putExtra("imagePath", mImageUri.toString());
            FilePickerPopUp.this.startActivity(i);
            MainActivity.b = true;
        }
    }

    //Dialog Popup to Choose Between Local Storage or Google Drive
    private void dialogPopup (){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width* 10), (int)(height*.3));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
    }

    //Get Extension of a file (eg. jpg, pdf etc..)
    public String getFileExtension (Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    //Upload File to Firebase
    private void uploadFile(){
       if(mImageUri != null){
           StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
           + "." + getFileExtension(mImageUri));

           fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Toast.makeText(FilePickerPopUp.this, "Files Uploaded", Toast.LENGTH_SHORT).show();
                            String download_url = uri.toString();
                            Upload upload = new Upload(mImageUri.toString().trim(),
                                    download_url);
                            String uploadID = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadID).setValue(upload);
                        }
                    });
               }

           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                    Toast.makeText(FilePickerPopUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
               }
           });
       }
       else{
           Toast.makeText(this, "No File Selected", Toast.LENGTH_SHORT).show();
       }
    }

}
