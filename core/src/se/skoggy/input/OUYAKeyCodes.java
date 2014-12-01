package se.skoggy.input;

import com.badlogic.gdx.controllers.mappings.Ouya;

public class OUYAKeyCodes extends KeyCodes {

	@Override
	public int left() {
		return Ouya.BUTTON_DPAD_LEFT;
	}

	@Override
	public int right() {
		return Ouya.BUTTON_DPAD_RIGHT;
	}

	@Override
	public int up() {
		return Ouya.BUTTON_DPAD_UP;
	}

	@Override
	public int down() {
		return Ouya.BUTTON_DPAD_DOWN;
	}

	@Override
	public int axisLeftX() {
		return Ouya.AXIS_LEFT_X;
	}

	@Override
	public int axisLeftY() {
		return Ouya.AXIS_LEFT_Y;
	}

	@Override
	public int axisRightX() {
		return Ouya.AXIS_RIGHT_X;
	}

	@Override
	public int axisRightY() {
		return Ouya.AXIS_RIGHT_Y;
	}

	@Override
	public int start() {
		return Ouya.BUTTON_O;
	}

	@Override
	public int back() {
		return Ouya.BUTTON_MENU;
	}

	@Override
	public int action1() {
		return Ouya.BUTTON_O;
	}

	@Override
	public int action2() {
		return Ouya.BUTTON_A;
	}

	@Override
	public int action3() {
		return Ouya.BUTTON_U;
	}

	@Override
	public int action4() {
		return Ouya.BUTTON_Y;
	}

	@Override
	public int shoulderLeft() {
		return Ouya.BUTTON_L1;
	}

	@Override
	public int shoulderRight() {
		return Ouya.BUTTON_R1;
	}

	@Override
	public int triggerLeft() {
		return Ouya.AXIS_LEFT_TRIGGER;
	}

	@Override
	public int triggerRight() {
		return Ouya.AXIS_RIGHT_TRIGGER;
	}

}
