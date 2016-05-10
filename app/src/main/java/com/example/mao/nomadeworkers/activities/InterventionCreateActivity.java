package com.example.mao.nomadeworkers.activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.example.mao.nomadeworkers.R;
import com.example.mao.nomadeworkers.model.Client;
import com.example.mao.nomadeworkers.model.Photo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class InterventionCreateActivity extends AppCompatActivity {
    private EditText edittext;
    private static final String APP_KEY = "mcwwm0q6pt4cpcr";
    private static final String APP_SECRET = "rposf9n7dutu4zy";
    private static final String ACCOUNT_PREFS_NAME = "prefs";
    private static final String ACCESS_KEY_NAME = "ACCESS_KEY";
    private static final String ACCESS_SECRET_NAME = "ACCESS_SECRET";

    static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;
    private DropboxAPI<AndroidAuthSession> mDBApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention_create2);
        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);
        mDBApi.getSession().startOAuth2Authentication(InterventionCreateActivity.this);

        try {
            File file = new File("working-draft.txt");
            FileInputStream inputStream = new FileInputStream(file);
            DropboxAPI.Entry response = mDBApi.putFile("/magnum-opus.txt", inputStream,file.length(), null, null);

            Log.i("DbExampleLog", "The uploaded file's rev is: " + response.rev);
        }catch (Exception e){
            Log.i("DbExampleLog","Erreur dropbox upload");
        }
        ImageButton b;
        ImageView viewImage;


        List<String> status = Arrays.asList("A venir","En cours","Terminée") ;
        List<Client> clients = Client.listAll(Client.class);
        Spinner spinner = (Spinner)this.findViewById(R.id.chooseClient);
        Spinner spinner2 = (Spinner)this.findViewById(R.id.chooseIntStatus);
        ArrayAdapter<Client> clientsAdapter = new ArrayAdapter<Client>(this, android.R.layout.simple_spinner_dropdown_item,clients);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,status);
        spinner.setAdapter(clientsAdapter);
        spinner2.setAdapter(statusAdapter);

        b = (ImageButton) findViewById(R.id.takePhotoInt);
        //viewImage=(ImageView)findViewById(R.id.viewImage);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        edittext = (EditText)findViewById(R.id.setDateInt);
        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(InterventionCreateActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    @Override
    protected void onResume() {
        super.onResume();
        AndroidAuthSession session = mDBApi.getSession();

        // The next part must be inserted in the onResume() method of the
        // activity from which session.startAuthentication() was called, so
        // that Dropbox authentication completes properly.
        if (session.authenticationSuccessful()) {
            try {
                // Mandatory call to complete the auth
                session.finishAuthentication();

                // Store it locally in our app for later use
                storeAuth(session);
            } catch (IllegalStateException e) {
                showToast("Couldn't authenticate with Dropbox:" + e.getLocalizedMessage());
                Log.i("DBtest", "Error authenticating", e);
            }
        }
    }
    /**
     * Shows keeping the access keys returned from Trusted Authenticator in a local
     * store, rather than storing user name & password, and re-authenticating each
     * time (which is not to be done, ever).
     */
    private void storeAuth(AndroidAuthSession session) {
        // Store the OAuth 2 access token, if there is one.
        String oauth2AccessToken = session.getOAuth2AccessToken();
        if (oauth2AccessToken != null) {
            SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString(ACCESS_KEY_NAME, "oauth2:");
            edit.putString(ACCESS_SECRET_NAME, oauth2AccessToken);
            edit.commit();
            return;
        }
        // Store the OAuth 1 access token, if there is one.  This is only necessary if
        // you're still using OAuth 1.
        AccessTokenPair oauth1AccessToken = session.getAccessTokenPair();
        if (oauth1AccessToken != null) {
            SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
            SharedPreferences.Editor edit = prefs.edit();
            edit.putString(ACCESS_KEY_NAME, oauth1AccessToken.key);
            edit.putString(ACCESS_SECRET_NAME, oauth1AccessToken.secret);
            edit.commit();
        }
    }
    private void showToast(String msg) {
        Toast error = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        error.show();
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    private void selectImage() {

        final CharSequence[] options = { "Prendre une photo", "Annuler" };
        List<Photo> listPhotos = Photo.listAll(Photo.class);
        final String nextNomPhoto = "photo_intervention_" + String.valueOf(listPhotos.size()+ 1)  +".jpg";

        AlertDialog.Builder builder = new AlertDialog.Builder(InterventionCreateActivity.this);
        builder.setTitle("Ajouter une photo");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Prendre une photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        // Create the File where the photo should go
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File

                        }
                        // Continue only if the File was successfully created
                        if (photoFile != null) {
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(photoFile));
                            try {
                                File file = new File("working-draft.txt");
                                FileInputStream inputStream = new FileInputStream(file);
                                DropboxAPI.Entry response = mDBApi.putFile("/magnum-opus.txt", inputStream,
                                        file.length(), null, null);
                                Log.i("DbExampleLog", "The uploaded file's rev is: " + response.rev);
                            }catch (Exception e){
                                Log.i("DbExampleLog","Erreur dropbox upload");
                            }
                            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
                        }
                    }
                } else if (options[item].equals("Annuler")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

}
