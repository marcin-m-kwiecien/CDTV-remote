package xyz.mordeaux.cdtvremote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "irstuff";

    private class ButtonsClickListener implements View.OnClickListener {
        private final ConsumerIrManager consumerIrManager;

        private ButtonsClickListener(ConsumerIrManager consumerIrManager) {
            this.consumerIrManager = consumerIrManager;
        }

        @Override
        public void onClick(View v) {
            Switch mouseJoystick = findViewById(R.id.switch_mouse_joystick);
            boolean isJoystick = mouseJoystick.isChecked();
            CDTVCode code = null;
            switch (v.getId()) {
                case R.id.button_mouse_up:
                    code = isJoystick ? CDTVCode.JOYSTICK_UP : CDTVCode.MOUSE_UP;
                    break;
                case R.id.button_mouse_down:
                    code = isJoystick ? CDTVCode.JOYSTICK_DOWN : CDTVCode.MOUSE_DOWN;
                    break;
                case R.id.button_mouse_left:
                    code = isJoystick ? CDTVCode.JOYSTICK_LEFT : CDTVCode.MOUSE_LEFT;
                    break;
                case R.id.button_mouse_right:
                    code = isJoystick ? CDTVCode.JOYSTICK_RIGHT : CDTVCode.MOUSE_RIGHT;
                    break;
                case R.id.button_mouse_a:
                    code = isJoystick ? CDTVCode.JOYSTICK_A : CDTVCode.MOUSE_A;
                    break;
                case R.id.button_mouse_b:
                    code = isJoystick ? CDTVCode.JOYSTICK_B : CDTVCode.MOUSE_B;
                    break;
                case R.id.button_key_1:
                    code = CDTVCode.BUTTON_1;
                    break;
                case R.id.button_key_2:
                    code = CDTVCode.BUTTON_2;
                    break;
                case R.id.button_key_3:
                    code = CDTVCode.BUTTON_3;
                    break;
                case R.id.button_key_4:
                    code = CDTVCode.BUTTON_4;
                    break;
                case R.id.button_key_5:
                    code = CDTVCode.BUTTON_5;
                    break;
                case R.id.button_key_6:
                    code = CDTVCode.BUTTON_6;
                    break;
                case R.id.button_key_7:
                    code = CDTVCode.BUTTON_7;
                    break;
                case R.id.button_key_8:
                    code = CDTVCode.BUTTON_8;
                    break;
                case R.id.button_key_9:
                    code = CDTVCode.BUTTON_9;
                    break;
                case R.id.button_key_0:
                    code = CDTVCode.BUTTON_0;
                    break;
                case R.id.button_key_escape:
                    code = CDTVCode.BUTTON_ESCAPE;
                    break;
                case R.id.button_key_enter:
                    code = CDTVCode.BUTTON_ENTER;
                    break;
            }
            if (code != null) {
                consumerIrManager.transmit(40000, code.pattern());
                Toast.makeText(MainActivity.this, "Sent code " + code.name(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButtonsClickListener listener = new ButtonsClickListener(
                (ConsumerIrManager) getApplicationContext().getSystemService(Context.CONSUMER_IR_SERVICE));


        findViewById(R.id.button_mouse_up).setOnClickListener(listener);
        findViewById(R.id.button_mouse_down).setOnClickListener(listener);
        findViewById(R.id.button_mouse_left).setOnClickListener(listener);
        findViewById(R.id.button_mouse_right).setOnClickListener(listener);
        findViewById(R.id.button_mouse_a).setOnClickListener(listener);
        findViewById(R.id.button_mouse_b).setOnClickListener(listener);
        findViewById(R.id.button_key_1).setOnClickListener(listener);
        findViewById(R.id.button_key_2).setOnClickListener(listener);
        findViewById(R.id.button_key_3).setOnClickListener(listener);
        findViewById(R.id.button_key_4).setOnClickListener(listener);
        findViewById(R.id.button_key_5).setOnClickListener(listener);
        findViewById(R.id.button_key_6).setOnClickListener(listener);
        findViewById(R.id.button_key_7).setOnClickListener(listener);
        findViewById(R.id.button_key_8).setOnClickListener(listener);
        findViewById(R.id.button_key_9).setOnClickListener(listener);
        findViewById(R.id.button_key_0).setOnClickListener(listener);
        findViewById(R.id.button_key_escape).setOnClickListener(listener);
        findViewById(R.id.button_key_enter).setOnClickListener(listener);

    }
}