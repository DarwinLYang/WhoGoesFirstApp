package com.purplishsocks.whofirst;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GestureDetectorCompat;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends FragmentActivity {

	private GestureDetectorCompat mDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDetector = new GestureDetectorCompat(this, new MyGestureListener());

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.mainContainer, new MainScreenFragment()).commit();
		}
	}

	public static class MainScreenFragment extends Fragment {

		public MainScreenFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.mDetector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {

		private static final int SWIPE_THRESHOLD = 100;
		private static final int SWIPE_VELOCITY_THRESHOLD = 100;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			boolean result = false;
			try {
				float diffY = e2.getY() - e1.getY();
				float diffX = e2.getX() - e1.getX();
				if (Math.abs(diffX) > Math.abs(diffY)) {
					if (Math.abs(diffX) > SWIPE_THRESHOLD
							&& Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
						if (diffX < 0)
							qScreen();
						else
							sScreen();
					}
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			return result;
		}
	}

	public void questionScreen(View view) {
		qScreen();
	}

	private void qScreen() {
		Intent intent = new Intent(this, QuestionActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
	}

	public void settingsScreen(View view) {
		sScreen();
	}

	private void sScreen() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
	}

}
