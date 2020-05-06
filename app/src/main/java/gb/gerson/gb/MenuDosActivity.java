package gb.gerson.gb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.muddzdev.styleabletoast.StyleableToast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.*;
import android.widget.*;

import com.google.android.gms.tasks.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import com.google.firebase.storage.*;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;

import android.widget.ImageView;
import android.widget.ProgressBar;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class MenuDosActivity extends AppCompatActivity {
    private static final int RECOGER_SOLICITUD_IMAGEN = 1;

    private Button btnEligeImagen;
    private Button btnCarga;
    private TextView txtVerSubidas;
    private EditText edtNombreDelArchivo;
    private EditText edtDescription;
    private ImageView imageView;
    private ProgressBar mprogressBar;

    private Uri mImagenRuta;
    private StorageTask mUploadTask;

    // Variables para storage
    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_dos);

        //Casteamos
        btnEligeImagen = findViewById(R.id.btnEligeImagen);
        btnCarga = findViewById(R.id.btnSubir);
        edtNombreDelArchivo = findViewById(R.id.edtFileName);
        edtDescription = findViewById(R.id.edtDescription);
        imageView = findViewById(R.id.image_view);
        mprogressBar = findViewById(R.id.progress_bar);

        //Storage
        storageReference = FirebaseStorage.getInstance().getReference("Ofertas");
        // Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("Ofertas");

        btnEligeImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abriArchivos();
            }
        });
        btnCarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(MenuDosActivity.this, "Carga en Progreso", Toast.LENGTH_SHORT).show();
                } else {
                    subirArchivo();
                }
            }
        });


    }

    // MEtodo para anbrir el gestor de archivos de android
    private void abriArchivos() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, RECOGER_SOLICITUD_IMAGEN);
    }

    // Recuperar la url de la imagen y cargarla al imagenview en android
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RECOGER_SOLICITUD_IMAGEN && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImagenRuta = data.getData();
            Picasso.with(this).load(mImagenRuta).into(imageView);

        }
    }

    private String obtenerDireccionDelArchivo(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    // Metodo para subir los datos a firebase
    private void subirArchivo() {
        if (mImagenRuta != null) {
            final StorageReference fileReference = storageReference.child(System.currentTimeMillis()
                    + "." + obtenerDireccionDelArchivo(mImagenRuta));

            fileReference.putFile(mImagenRuta).continueWithTask(
                    new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return fileReference.getDownloadUrl();
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {

                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                int Id = (int) new Date().getTime();
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String uid = user.getUid();
                                uid.toString().trim();
                                String subirId = Integer.toString(Id);
                                String nombre = edtNombreDelArchivo.getText().toString().trim();
                                String descripcion = edtDescription.getText().toString().trim();
                                if(TextUtils.isEmpty(nombre))
                                {
                                    Toast.makeText(MenuDosActivity.this, "Escriba un Nombre de la oferta", Toast.LENGTH_LONG).show();
                                }else if(TextUtils.isEmpty(descripcion))
                                {
                                    Toast.makeText(MenuDosActivity.this, "Escriba una descripcion de la oferta", Toast.LENGTH_LONG).show();
                                }else
                                {

                                    Subir upload = new Subir(nombre, downloadUri.toString(), descripcion, Id,uid);
                                    databaseReference.child(subirId).setValue(upload);
                                    Toast.makeText(MenuDosActivity.this, "Subida Correctamente", Toast.LENGTH_LONG).show();
                                    limpiarCampos();
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MenuDosActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
            //Progreso de la barra del progressbar
        }else {
            Toast.makeText(MenuDosActivity.this, "No se ha seleccionado una imagen: ", Toast.LENGTH_LONG).show();
        }
    }
    private void limpiarCampos() {
        edtNombreDelArchivo.setText("");
        imageView.invalidate();
        imageView.setImageBitmap(null);
        edtDescription.setText("");
    }
}



