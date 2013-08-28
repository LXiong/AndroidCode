public class Download extends Activity implements OnClickListener
{
	/** Called when the activity is first created. */
	private Button downloadTxtButton;
	private Button downloadMp3Button;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		findView();
	}

	public void findView()
	{
		downloadTxtButton = (Button) findViewById(R.id.downloadTxt);
		downloadTxtButton.setOnClickListener(this);

		downloadMp3Button = (Button) findViewById(R.id.downloadMp3);
		downloadMp3Button.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		int viewId = v.getId();
		switch (viewId)
		{
		
		/**
		 * �������ֻ���������ı��ļ����������ж�ȡ�ַ��ܵġ�
		 */
		case R.id.downloadTxt:
		{
			HttpDownloader httpDownloader = new HttpDownloader();
			String lrc = httpDownloader.downStr("http://61.184.100.229/");//����ط���url�����Լ�����
			Log.e("@@@@", "downloadTxt: " + lrc);
		}
		
		/**
		 * ����������������κ��ļ���
		 */
		case R.id.downloadMp3:
		{
			HttpDownloader httpDownloader = new HttpDownloader();
			int result = httpDownloader.downFile(
					"http://192.168.1.107:8080/voa1500/a1.mp3", "voa/",
					"a1.mp3");//����ط���urlͬ�������Լ�����
			Log.e("@@@@", "downloadMp3");
		}
		default:
			break;
		}
	}
}