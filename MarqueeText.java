package com.kingter.ynjk.widget;

import com.kingter.ynjk.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MarqueeText extends TextView implements OnClickListener {
	public final static String TAG = MarqueeText.class.getSimpleName();

	public boolean isMeasure = false;

	private float textLength = 0f;// �ı�����
	private float viewWidth = 0f;
	private float step = 0f;// ���ֵĺ�����
	private float y = 0f;// ���ֵ�������
	private float temp_view_plus_text_length = 0.0f;// ���ڼ������ʱ����
	private float temp_view_plus_two_text_length = 0.0f;// ���ڼ������ʱ����
	public boolean isStarting = false;// �Ƿ�ʼ����
	private Paint paint = null;// ��ͼ��ʽ
	private String text = "";// �ı�����
	private Context context;
	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			startScroll();
		}

	};

	public MarqueeText(Context context) {
		super(context);
		initView(context);
	}

	public MarqueeText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public MarqueeText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void initView(Context context) {
		setOnClickListener(this);
		this.context = context;
	}

	/**
	 * �ı���ʼ����ÿ�θ����ı����ݻ����ı�Ч����֮����Ҫ���³�ʼ��һ��
	 */
	private void init() {
		paint = getPaint();
		paint.setColor(getResources().getColor(R.color.red));
		text = getText().toString();
		textLength = paint.measureText(text);
		viewWidth = getWidth();
		if (viewWidth == 0) {
			if (context != null) {
				Display display = ((Activity) context).getWindowManager()
						.getDefaultDisplay();
				viewWidth = display.getWidth();
			}
		}
		step = textLength;
		temp_view_plus_text_length = viewWidth + textLength;
		temp_view_plus_two_text_length = viewWidth + textLength * 2;
		y = getTextSize() + getPaddingTop();
	}

	@Override
	public Parcelable onSaveInstanceState() {
		Parcelable superState = super.onSaveInstanceState();
		SavedState ss = new SavedState(superState);

		ss.step = step;
		ss.isStarting = isStarting;

		return ss;

	}

	@Override
	public void onRestoreInstanceState(Parcelable state) {
		if (!(state instanceof SavedState)) {
			super.onRestoreInstanceState(state);
			return;
		}
		SavedState ss = (SavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());

		step = ss.step;
		isStarting = ss.isStarting;

	}

	public static class SavedState extends BaseSavedState {
		public boolean isStarting = false;
		public float step = 0.0f;

		SavedState(Parcelable superState) {
			super(superState);
		}

		@Override
		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeBooleanArray(new boolean[] { isStarting });
			out.writeFloat(step);
		}

		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {

			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}

			@Override
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}
		};

		private SavedState(Parcel in) {
			super(in);
			boolean[] b = null;
			in.readBooleanArray(b);
			if (b != null && b.length > 0)
				isStarting = b[0];
			step = in.readFloat();
		}
	}

	/**
	 * ��ʼ����
	 */
	public void startScroll() {
		isStarting = true;
		invalidate();
	}

	/**
	 * ֹͣ����
	 */
	public void stopScroll() {
		isStarting = false;
		invalidate();
	}

	@Override
	public void setText(CharSequence text, BufferType type) {
		// TODO Auto-generated method stub
		super.setText(text, type);
		// init();
		isMeasure = false;
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (!isMeasure) {
			init();
			isMeasure = true;
		}
		canvas.drawText(text, temp_view_plus_text_length - step, y, paint);
		if (!isStarting)
			return;
		step += 2;
		if (step > temp_view_plus_two_text_length)
			step = textLength;
		invalidate();

	}

	@Override
	public void onClick(View v) {
		if (isStarting) {
			stopScroll();
			handler.removeCallbacks(runnable);
			handler.postDelayed(runnable, 5000);
		}

	}

}
