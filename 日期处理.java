//���ݵ�ǰ���ڻ�ȡ��long��ʱ��
public static long getLongtimeForDate(int year, int month, int day){
		String temp = String.format("%d-%02d-%02d", year,
				month, day);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try
		{
			date = formatter.parse(temp);
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date.getTime();
	}
	
	//����long��ʱ���ȡ����
	public static String getDateByLongTime(long time){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(new java.sql.Timestamp(time));
	}
	
	//���ݵ�ǰϵͳʱ���ȡ����
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	String format = formatter.format(new java.sql.Timestamp(System.currentTimeMillis()));
		System.out.println(format);
