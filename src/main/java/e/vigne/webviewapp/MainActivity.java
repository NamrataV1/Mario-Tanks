package e.vigne.webviewapp;

import android.content.Context;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity  {
        TextView textx,texty,textz,textthetha;
        SensorManager senman;
        Sensor sen;
        double theta;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url1 ="http://10.0.1.19:8080/stream";
        WebView view1=(WebView) this.findViewById(R.id.webView1);
        view1.getSettings().setJavaScriptEnabled(true);
        view1.loadUrl(url1);
        String url2 ="http://10.0.1.24:8080/stream";
        WebView view2=(WebView) this.findViewById(R.id.webView2);
        view2.getSettings().setJavaScriptEnabled(true);
        view2.loadUrl(url2);

        senman = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sen = senman.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        textx = (TextView) findViewById(R.id.textView);
        //texty = (TextView) findViewById(R.id.textView4);
       // textz = (TextView) findViewById(R.id.textView5);
        textthetha = (TextView) findViewById(R.id.textView6);

    }

    public void onResume() {
        super.onResume();
        senman.registerListener(gyroListener,sen,SensorManager.SENSOR_DELAY_NORMAL);

    }public void onStop(){
        super.onStop();
        senman.unregisterListener(gyroListener);
    }
    public SensorEventListener gyroListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            textx.setText("X: " + (int)x + "rads/sec");
            //texty.setText("Y: " + (int)y + "rads/sec");
           // textz.setText("Z: "+ (int)z + "rads/sec");

            double dtheta = (x*60)/(2*3.14);

            theta = theta +dtheta;

            textthetha.setText("angle" +(int)theta);


        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

}
