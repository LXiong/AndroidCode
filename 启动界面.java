new Handler().postDelayed(new Runnable(){  
	// Ϊ�˼��ٴ���ʹ������Handler����һ����ʱ�ĵ���
	            public void run() {  
	                Intent i = new Intent(SplashScreen.this, Main.class);   
	                //ͨ��Intent������������������Main���Activity
	                SplashScreen.this.startActivity(i);    //����Main����
	                SplashScreen.this.finish();    //�ر��Լ����������
	            }  
	        }, 5000);   //5�룬�����˰�