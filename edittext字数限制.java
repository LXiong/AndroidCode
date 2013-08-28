private EditText mEditText ;  
        mEditText  = (EditText) findViewById(R.id.mEditText );  
/** �������� */  
        mEditText  .addTextChangedListener(new TextWatcher() {  
            private CharSequence temp;                
            private int selectionStart ;                
            private int selectionEnd ;  
            @Override  
            public void beforeTextChanged(CharSequence s, int start, int count,  
                    int after) {  
                temp = s;   
            }  
              
            @Override  
            public void onTextChanged(CharSequence s, int start, int before,  
                    int count) {  
                  
            }   
              
            @Override  
            public void afterTextChanged(Editable s) {  
                selectionStart = mEditText  .getSelectionStart();                    
                selectionEnd = mEditText  .getSelectionEnd();                    
                Log.d(TAG,""+selectionStart);                    
                if (temp.length() > 8) {                        
                    Toast.makeText(MAUpdateAty.this, "�������ܳ���8��", Toast.LENGTH_SHORT).show();                        
                    s.delete(selectionStart-1, selectionEnd);                        
                    int tempSelection = selectionStart;                        
                    mEditText  .setText(s);                        
                    mEditText  .setSelection(tempSelection);                    
                }   
                Log.d(TAG," "+selectionEnd);  
            }  
        });  
		
		
		
		
private TextWatcher mTextWatcher = new TextWatcher(){  
  Toast mToast = null;  
  public void beforeTextChanged(CharSequence s, int start,   
    int count,int after) {  
  }  
  
  public void onTextChanged(CharSequence s, int start,   
    int before,int count) {  
  }  
    
  public void afterTextChanged(Editable s) {  
   int nSelStart = 0;  
   int nSelEnd = 0;  
   boolean nOverMaxLength = false;  
     
   nSelStart = mEditText.getSelectionStart();  
   nSelEnd   = mEditText.getSelectionEnd();  
     
   nOverMaxLength = (s.length() > Constants.MAX_TEXT_INPUT_LENGTH) ? true : false;  
   if(nOverMaxLength){  
    if(null == mToast){  
     mToast = Toast.makeText(mContext,   
       R.string.IDS_MSG_TEXT_OVER_MAXLENGTH,   
       Toast.LENGTH_SHORT);  
    }  
    mToast.show();  
      
    s.delete(nSelStart - 1, nSelEnd);  
  
    mEditText.setTextKeepState(s);//�����ע����һ�У����ֹ��ԭ�ȵ�λ�ã��� mEditText.setText(s)���ù���ܵ���ǰ�棬�������ټ�mEditText.setSelection(nSelStart) Ҳ��������  
    }  
  }  
 };  