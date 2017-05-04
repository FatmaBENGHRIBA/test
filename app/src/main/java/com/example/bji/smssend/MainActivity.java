package com.example.bji.smssend;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    Sensor accerolometre;
    SensorManager sensor1 ;
    DatabaseHandler db;
    TextView Numero;
    TextView affichageX;
    private Timer myTimer;
  boolean write=true;
   ArrayList<AccesValeur> name;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    boolean b = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    db = new DatabaseHandler(this);

        Numero = (TextView) findViewById(R.id.textView2);
        affichageX = (TextView) findViewById(R.id.textViewX); affichageX.setMovementMethod(new ScrollingMovementMethod());



        sensor1 = (SensorManager) getSystemService(SENSOR_SERVICE);
        accerolometre = sensor1.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
       // db.addContact(new AccesValeur(1500,100,450));





    }

    protected void onPause() {
        super.onPause();
        sensor1.unregisterListener(this,accerolometre);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensor1.registerListener(this, accerolometre, SensorManager.SENSOR_DELAY_UI);
    }
    public void onSensorChanged(SensorEvent event) {
        float x;
        float y;
        float z;

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt(x*x + y*y + z*z);
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;

            Numero.setText(Float.toString(x));
            if (mAccel > 15 && write) {
                affichageX.setText("");

                db.addContact(new AccesValeur(Float.toString(x), Float.toString(y), Float.toString(z)));
                name = new ArrayList<>();
                name = db.getAllValeur();

                Toast.makeText(MainActivity.this,"acc = "+mAccel,Toast.LENGTH_LONG ).show();
               Toast.makeText(MainActivity.this,String.valueOf(name.size()),Toast.LENGTH_LONG ).show();
                for (int i = 0; i < name.size(); i++) {
                    affichageX.setText(affichageX.getText()+String.valueOf(i)+"\n");
                    affichageX.setText(affichageX.getText()+" X="+name.get(i).getX()+"\n");
                    affichageX.setText(affichageX.getText()+" Y="+name.get(i).getY()+"\n");
                    affichageX.setText(affichageX.getText()+" Z="+name.get(i).getZ()+"\n");
                }
                new CountDownTimer(3000, 1000) {

                    public void onTick(long millisUntilFinished) {
                    write=false;
                    }

                    public void onFinish() {
                    write=true;
                    }

                }.start();
                String num = "53924519";
                String msg = "urgence!!";
                if (num.length() >= 8 && msg.length() > 0 && b) {
                    SmsManager.getDefault().sendTextMessage(num, null, msg, null, null);
                    b = false;
                } else {
                    Toast.makeText(MainActivity.this, "entrer le numero et/ou le message", Toast.LENGTH_SHORT).show();
                }
            }
            }



    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
