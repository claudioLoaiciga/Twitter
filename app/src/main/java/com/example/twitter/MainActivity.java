package com.example.twitter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Array;
import java.util.Arrays;

/*FIREBASE AUTH:proporciona servicios de backend, SDK fáciles de usar y bibliotecas de IU ya elaboradas para autenticar a los usuarios en tu app.
Admite la autenticación mediante contraseñas, números de teléfono, proveedores de identidad federada populares, como Google, Facebook y Twitter, y mucho más.
* */

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    //Variables FIREBASE UI
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener; //variable que me ayuda a obtener la instancia firebase auth
    //y esta a la espera de cualquier cambio. Ej: un inicio o cierre de sesión


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializar la variable
        mFirebaseAuth= FirebaseAuth.getInstance();

        //Controlar el inicio y cierre de sesión atraves del listener propocionado por la misma clase FIREBASE Auth
        mAuthStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {// indicará cuando haya un cambio, osea un inicio o cierre de sesión
                //comprobar si existe una sesión activa
                FirebaseUser user= firebaseAuth.getCurrentUser();
                if(user!=null){// si es diferente de null, inicio de sesion exitoso

                }else {// sesión no activa/cierre de sesión
                    //limpieza de la vista, mostrar de nuevo los proveedores de inicio de sesion
                    //item de inicio de sesion
                    startActivityForResult(AuthUI.getInstance()// para recordar contraseñas y las cuentas con las que el usuario se ha logueado
                            .createSignInIntentBuilder()
                            .setIsSmartLockEnabled(false)
                            .setTosUrl("https://politicadeprivacidadplantilla.com/")
                            .setAvailableProviders(Arrays.asList(new AuthUI.IdpConfig.EmailBuilder().build()))
                            .build(), RC_SIGN_IN);
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//Obtener respuesta del servidor ya sea exitoso o no
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            if(resultCode==RESULT_OK){// Inicio exitoso
                Toast.makeText(this, "Hola...",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Ha ocurrido un error, intenta de nuevo",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //verificar que no sea nulo
        if(mAuthStateListener!= null){
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
}
