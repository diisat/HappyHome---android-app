package com.app.icesi.proyectoappsmoviles.client_activities;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.employee_activities.CalendarEmpRegActivity;
import com.app.icesi.proyectoappsmoviles.employee_activities.RegisterEmployeeActivity;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class RegisterClientActivity extends AppCompatActivity {

    Button btn_registerClient,btn_calendar;

    EditText txtName,txtLastName,txtAddress,txtEmail,txtCC,txtTel;
    TextView txtDateOfBirth;

    RadioGroup rdSex;
    String sexSelected="";
    RadioButton rdAcceptTermsCond;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);

        txtName=findViewById(R.id.txtNameClient);
        txtLastName=findViewById(R.id.txtLastNamesClient);
        txtAddress=findViewById(R.id.txtAdressClient);
        txtEmail=findViewById(R.id.txtEmailCliente);
        txtDateOfBirth=findViewById(R.id.txtDateOfBirthClient);
        txtDateOfBirth.setText(getIntent().getStringExtra("dateClient"));
        btn_calendar= findViewById(R.id.btn_calendarClient);
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterClientActivity.this,CalendarClientRegActivity.class);
                startActivity(i);
            }
        });
        txtCC=findViewById(R.id.txtCCClient);
        txtTel=findViewById(R.id.txtTelClient);



        rdSex= (RadioGroup) findViewById(R.id.rdSexClient);
        rdSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rdFemale){
                    sexSelected="F";

                }else if(checkedId==R.id.rdMale){
                    sexSelected="M";

                }
            }
        });
        rdAcceptTermsCond= (RadioButton)findViewById(R.id.rdAcceptTermsCond);

        inicializarFirebase();

        btn_registerClient=(Button)findViewById(R.id.btn_registerClient);
        btn_registerClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                        //TODO - registro en el firebase
                        Usuario p=new Usuario();
                        p.setUid(UUID.randomUUID().toString());
                        p.setNombres(txtName.getText().toString());
                        p.setApellidos(txtLastName.getText().toString());
                        p.setCedula(txtCC.getText().toString());
                        p.setCorreo(txtEmail.getText().toString());
                        p.setTelefono(txtTel.getText().toString());
                        p.setGenero(sexSelected);

                        String dates=txtDateOfBirth.getText().toString();
                        ParsePosition pp1 = new ParsePosition(0);
                        Date date = formatter.parse(dates,pp1);
                        p.setFecha_nacimiento(date);

                        //falta location
                        //p.setUbicacion(new Location());

                        databaseReference.child("usuarios").child("clientes").child(p.getUid()).setValue(p);
                        // Toast.makeText(this,"Agregado",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterClientActivity.this,PerfilClienteActivity.class);
                        i.putExtra("userId",p.getUid());
                        startActivity(i);


                        //Toast.makeText(this,"Debe aceptar los terminos y condiciones para poder registrarse.",Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase=FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference=firebaseDatabase.getReference();



    }




    public void validacion(){
        String name= txtName.getText().toString();
        String lastName= txtLastName.getText().toString();
        String address= txtAddress.getText().toString();
        String birth= txtDateOfBirth.getText().toString();
        String genre=sexSelected;
        String email= txtEmail.getText().toString();
        String tel= txtTel.getText().toString();


        if(name.equals("")){
            txtName.setError("Required");
        }
        if(lastName.equals("")){
            txtLastName.setError("Required");
        }
        if(address.equals("")){
            txtAddress.setError("Required");
        }
        if(birth.equals("")){
            txtDateOfBirth.setError("Required");
        }
        if(genre.equals("")){
            //rdSex.setError("Required");
        }
        if(email.equals("")){
            txtEmail.setError("Required");
        }
        if(tel.equals("")){
            txtName.setError("Required");
        }
        if(name.equals("")){
            txtName.setError("Required");
        }

    }
}