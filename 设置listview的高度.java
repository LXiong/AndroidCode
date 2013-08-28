//ListView��ÿ��Item������LinearLayout�������������ģ���Ϊ������Layout(��RelativeLayout)û����дonMeasure()�����Ի���onMeasure()ʱ�׳��쳣��
private void setLvHeight(ListView lvReview) {
		ListAdapter adapter = lvReview.getAdapter();
		if (adapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < adapter.getCount(); i++) {
			View itemView = adapter.getView(i, null, lvReview);
			itemView.measure(0, 0);
			totalHeight += itemView.getMeasuredHeight();
		}
		ViewGroup.LayoutParams layoutParams = lvReview.getLayoutParams();
		layoutParams.height = totalHeight
				+ (lvReview.getDividerHeight() * (adapter.getCount() - 1));// ���и�+ÿ�еļ��
		lvReview.setLayoutParams(layoutParams);
	}