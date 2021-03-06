package com.purplishsocks.whofirst;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GestureDetectorCompat;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class QuestionActivity extends FragmentActivity {

	private static TextView questionText;
	private static Question q;
	private QuestionFragment QuestFrag;
	private GestureDetectorCompat mDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		mDetector = new GestureDetectorCompat(this, new MyGestureListener());

		questionText = new TextView(this);
		q = new Question();

		if (savedInstanceState == null) {
			QuestFrag = new QuestionFragment();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.questionContainer, QuestFrag).commit();
		}

	}

	public static class QuestionFragment extends Fragment {

		public QuestionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_question,
					container, false);

			questionText = (TextView) rootView.findViewById(R.id.question);
			questionText.setText(q.nextQuestion());

			return rootView;
		}

		public void setText(String text) {
			TextView textView = (TextView) getView()
					.findViewById(R.id.question);
			textView.setText(text);
		}
	}

	public void changeQuestion(View view) {
		QuestFrag.setText(q.nextQuestion());
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
						if (diffX > 0)
							onBackPressed();
					}
				}
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			return result;
		}
	}

	@Override
	public void onBackPressed() {
		this.finish();
		overridePendingTransition(R.anim.left_slide_in, R.anim.left_slide_out);
	}
}
