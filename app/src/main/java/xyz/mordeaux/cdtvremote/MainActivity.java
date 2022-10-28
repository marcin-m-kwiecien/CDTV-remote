package xyz.mordeaux.cdtvremote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.ConsumerIrManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "irstuff";

    private ButtonsClickListener clickListener;

    private class ButtonsClickListener implements View.OnTouchListener, Runnable {
        private final ConsumerIrManager consumerIrManager;
        private final AtomicReference<CDTVCode> currentlyPressed = new AtomicReference<>(CDTVCode.NOOP);
        private final AtomicBoolean running = new AtomicBoolean(false);
        private final AtomicBoolean firstTimePressed = new AtomicBoolean(false);

        private ButtonsClickListener(ConsumerIrManager consumerIrManager) {
            this.consumerIrManager = consumerIrManager;
        }

        private void start() {
            this.running.set(true);
            new Thread(this).start();
        }

        private void stop() {
            this.running.set(false);
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            Switch mouseJoystick = findViewById(R.id.switch_mouse_joystick);
            boolean isJoystick = mouseJoystick.isChecked();
            int action = event.getAction() & MotionEvent.ACTION_MASK;

            if (action == MotionEvent.ACTION_UP) {
                currentlyPressed.set(CDTVCode.NOOP);
                return true;
            } else if (action != MotionEvent.ACTION_DOWN) {
                return false;
            }

            switch (v.getId()) {
                case R.id.button_mouse_up:
                    this.currentlyPressed.set(isJoystick ? CDTVCode.JOYSTICK_UP : CDTVCode.MOUSE_UP);
                    break;
                case R.id.button_mouse_down:
                    this.currentlyPressed.set(isJoystick ? CDTVCode.JOYSTICK_DOWN : CDTVCode.MOUSE_DOWN);
                    break;
                case R.id.button_mouse_left:
                    this.currentlyPressed.set(isJoystick ? CDTVCode.JOYSTICK_LEFT : CDTVCode.MOUSE_LEFT);
                    break;
                case R.id.button_mouse_right:
                    this.currentlyPressed.set(isJoystick ? CDTVCode.JOYSTICK_RIGHT : CDTVCode.MOUSE_RIGHT);
                    break;
                case R.id.button_mouse_a:
                    this.currentlyPressed.set(isJoystick ? CDTVCode.JOYSTICK_A : CDTVCode.MOUSE_A);
                    break;
                case R.id.button_mouse_b:
                    this.currentlyPressed.set(isJoystick ? CDTVCode.JOYSTICK_B : CDTVCode.MOUSE_B);
                    break;
                case R.id.button_key_1:
                    this.currentlyPressed.set(CDTVCode.BUTTON_1);
                    break;
                case R.id.button_key_2:
                    this.currentlyPressed.set(CDTVCode.BUTTON_2);
                    break;
                case R.id.button_key_3:
                    this.currentlyPressed.set(CDTVCode.BUTTON_3);
                    break;
                case R.id.button_key_4:
                    this.currentlyPressed.set(CDTVCode.BUTTON_4);
                    break;
                case R.id.button_key_5:
                    this.currentlyPressed.set(CDTVCode.BUTTON_5);
                    break;
                case R.id.button_key_6:
                    this.currentlyPressed.set(CDTVCode.BUTTON_6);
                    break;
                case R.id.button_key_7:
                    this.currentlyPressed.set(CDTVCode.BUTTON_7);
                    break;
                case R.id.button_key_8:
                    this.currentlyPressed.set(CDTVCode.BUTTON_8);
                    break;
                case R.id.button_key_9:
                    this.currentlyPressed.set(CDTVCode.BUTTON_9);
                    break;
                case R.id.button_key_0:
                    this.currentlyPressed.set(CDTVCode.BUTTON_0);
                    break;
                case R.id.button_key_escape:
                    this.currentlyPressed.set(CDTVCode.BUTTON_ESCAPE);
                    break;
                case R.id.button_key_enter:
                    this.currentlyPressed.set(CDTVCode.BUTTON_ENTER);
                    break;
            }
            this.firstTimePressed.set(true);
            return true;
        }

        @Override
        public void run() {
            while(this.running.get()) {
                try {
                    if (currentlyPressed.get() == CDTVCode.NOOP) {
                        Thread.sleep(20);
                        continue;
                    }
                    if (firstTimePressed.get()) {
                        consumerIrManager.transmit(40000, this.currentlyPressed.get().pattern());
                        firstTimePressed.set(false);
                        Thread.sleep(48); //Repeat every 60ms, but signal itself lasts for 11.5ms - 60-11.5 ~= 48
                        continue;
                    }
                    consumerIrManager.transmit(40000, CDTVCode.repeatPattern());
                    Thread.sleep(48); //Repeat every 60ms, but signal itself lasts for 11.5ms - 60-11.5 ~= 48
                } catch (InterruptedException e) {
                    //Shouldn't happen
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.clickListener = new ButtonsClickListener(
                (ConsumerIrManager) getApplicationContext().getSystemService(Context.CONSUMER_IR_SERVICE));

        this.clickListener.start();

        findViewById(R.id.button_mouse_up).setOnTouchListener(this.clickListener);
        findViewById(R.id.button_mouse_down).setOnTouchListener(this.clickListener);
        findViewById(R.id.button_mouse_left).setOnTouchListener(this.clickListener);
        findViewById(R.id.button_mouse_right).setOnTouchListener(this.clickListener);
        findViewById(R.id.button_mouse_a).setOnTouchListener(this.clickListener);
        findViewById(R.id.button_mouse_b).setOnTouchListener(this.clickListener);
        findViewById(R.id.button_key_1).setOnTouchListener(this.clickListener);
        findViewById(R.id.button_key_2).setOnTouchListener(this.clickListener);
        findViewById(R.id.button_key_3).setOnTouchListener(this.clickListener);
        findViewById(R.id.button_key_4).setOnTouchListener(this.clickListener);
        findViewById(R.id.button_key_5).setOnTouchListener(this.clickListener);
        findViewById(R.id.button_key_6).setOnTouchListener(this.clickListener);
        findViewById(R.id.button_key_7).setOnTouchListener(this.clickListener);
        findViewById(R.id.button_key_8).setOnTouchListener(this.clickListener);
        findViewById(R.id.button_key_9).setOnTouchListener(this.clickListener);
        findViewById(R.id.button_key_0).setOnTouchListener(this.clickListener);
        findViewById(R.id.button_key_escape).setOnTouchListener(this.clickListener);
        findViewById(R.id.button_key_enter).setOnTouchListener(this.clickListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        this.clickListener.stop();
    }
}