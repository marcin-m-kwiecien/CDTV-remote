package xyz.mordeaux.cdtvremote;

import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public enum CDTVCode {
    NOOP(0x000),
    MOUSE_A(0x080),
    MOUSE_B(0x040),
    MOUSE_UP(0x020),
    MOUSE_DOWN(0x010),
    MOUSE_LEFT(0x008),
    MOUSE_RIGHT(0x004),
    BUTTON_1(0x001),
    BUTTON_2(0x021),
    BUTTON_3(0x011),
    BUTTON_4(0x009),
    BUTTON_5(0x029),
    BUTTON_6(0x019),
    BUTTON_7(0x005),
    BUTTON_8(0x025),
    BUTTON_9(0x015),
    BUTTON_0(0x039),
    BUTTON_ESCAPE(0x031),
    BUTTON_ENTER(0x035),
    JOYSTICK_A(0x880),
    JOYSTICK_B(0x840),
    JOYSTICK_UP(0x820),
    JOYSTICK_DOWN(0x810),
    JOYSTICK_LEFT(0x808),
    JOYSTICK_RIGHT(0x804);

    private static final Integer INITIAL_PULSE = 9000;
    private static final Integer INITIAL_PAUSE = 4500;
    private static final int REPEAT_PAUSE = 2100;

    private static final Integer PULSE_SHORT = 400;
    private static final Integer PULSE_LONG = 1200;
    public static final int CODE_LENGTH = 12;

    private final int code;

    CDTVCode(int code) {
        this.code = code;
    }

    public int[] pattern() {
        List<Integer> pattern = new ArrayList<>();
        pattern.add(INITIAL_PULSE);
        pattern.add(INITIAL_PAUSE);
        for (int position = CODE_LENGTH - 1; position >= 0; position--) {
            pattern.add(PULSE_SHORT);
            pattern.add(this.bitAt(position) ? PULSE_LONG : PULSE_SHORT);
        }
        for (int position = CODE_LENGTH - 1; position >= 0; position--) {
            pattern.add(PULSE_SHORT);
            pattern.add(this.bitAt(position) ? PULSE_SHORT : PULSE_LONG); //Reversed
        }
        pattern.add(PULSE_SHORT);
        return Ints.toArray(pattern);
    }

    public static int[] repeatPattern() {
        return new int[] {INITIAL_PULSE, REPEAT_PAUSE, PULSE_SHORT};
    }

    private boolean bitAt(int position) {
        return (this.code & (1 << position)) != 0;
    }
}
