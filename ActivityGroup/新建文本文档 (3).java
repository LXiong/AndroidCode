/**
 * ����ע��ʱ���Id������Activity
 * 
 * @see #showActivity(String, Bundle)
 * @param actId
 */
public void showActivity(String actId) {
showActivity(actId, null);
}

/**
 * ����Activity��������ʾActivity��View���󶨵�ViewGroup��
 * 
 * @param cls
 * @param data
 */
public void showActivity(Class<? extends AbsActivity> cls, Bundle data) {
View targetView = null;
// û�б�ע���Activity�ᱻ��������Activity������
String actId = cls.getCanonicalName();
if (childs.containsKey(actId)) {// �Ѿ�ע��
if (data != null && mAbsGroup.getActivity(actId) != null) {
/**
 * ������ݲ�Ϊnull�����Ҹ�Id��Activity�Ѿ����������ˣ�
 * Ϊ�˱�֤���ݿ��Դ�onCreate�����е���Activity������destroyActivity
 */
mAbsGroup.destroyActivity(actId);
}
} else {// û��ע���
actId = EXTERNAL_ACTIVITY_ID;
if (mAbsGroup.getActivity(actId) != null) {
/**
 * ��������һ��AbsActivity������,�����������ڱ����������ڲ�������ʣ��ں��˵�ʱ����remove��,
 * �����;�л���Ĭ�ϵ�RootView
 * (Home��Rank...)��ǰ��Ҳ�ᱻ�Ƴ����������Ĳ���Ҫ�Ļ�������,���������Ƶ�����
 */
mAbsGroup.destroyActivity(actId);
}
lastExternalAct = cls;// ��������Activity��Class
externalActPrevId = currentActId;// ���浱ǰ��RootViewId,��back��ʱ����showPrevViewʱ����
}
mAbsGroup.startActivity(actId, cls, data);
Activity act = mAbsGroup.getActivity(actId);
if (act instanceof ActivitysHolder) {
targetView = ((ActivitysHolder) act).getActivityView();
} else {
targetView = act.getWindow().getDecorView();
}
changeView(targetView, actId);
}

/**
 * ��ʾĬ�ϵ�Activity
 * 
 * @see #showActivity(Class, Bundle)
 */
public boolean showDefaultActivity() {
if (defaultActId != null) {
Class<? extends AbsActivity> cls = childs.get(defaultActId).childClass;
if (cls != null) {
showActivity(cls);
return true;
}
}
return false;
}

/**
 * ��ʾ��һ��Activity
 * 
 * @see #showActivity(Class, Bundle)
 */
public void showPrevActivity() {
if (prevActId == currentActId)
return;
Class<? extends AbsActivity> cls;
if (currentActId != null && currentActId.endsWith(EXTERNAL_ACTIVITY_ID)) {
// �����ǰ������Activity������ʾһ��Activity��ʱ��Ҫ�������Act
destroyExternalActivity();
}
if (prevActId.equals(EXTERNAL_ACTIVITY_ID)) {
// ���ǰһ��View������Activity���ȴ�ActivityGroup���Ƴ������л�RootView
destroyExternalActivity();
cls = lastExternalAct;
} else {
cls = childs.get(prevActId).childClass;
}
showActivity(cls);
}

/**
 * �жϵ�ǰ�ǲ�������ָ����Activity
 * 
 * @param act
 * @return
 */
public boolean isIn(Class<? extends AbsActivity> actClass) {
String id = actClass.getCanonicalName();
if (currentActId != null && id.endsWith(currentActId)) {
return true;
}
return false;
}

/**
 * �ж��ǲ���������Activity�Ľ���
 * 
 * @return
 */
public boolean isInExternalAct() {
return isIn(EXTERNAL_ACTIVITY_ID);
}

/**
 * �жϵ�ǰ��view�ǲ�����Ҫ��view
 * 
 * @param rootViewName
 * @return
 */
public boolean isIn(String actId) {
if (currentActId != null && currentActId.equals(actId))
return true;
return false;
}
 
/**
 * ��ȡ��ǰActivity��Id
 * 
 * @return
 */
public String getCurrentActivityId() {
return currentActId;
}

/**
 * ��ȡǰһ����ʾ��Activity��Id
 * 
 * @return
 */
public String getPrevActivityId() {
return prevActId;
}

/**
 * ״̬����
 * 
 * @author skg
 * 
 */
public interface StatusListener {
public void onChildChanged(String actId, Object actTag);
}

private StatusListener mStatusListener;

/**
 * ����״̬������
 * 
 * @param sl
 */
public void setStatusListener(StatusListener sl) {
mStatusListener = sl;
}

private Child createChild(Class<? extends AbsActivity> cls, Object tag) {
if (cls == null)
return null;
Child child = new Child();
child.childClass = cls;
child.tag = tag;
return child;
}

/**
 * �ַ�Activity״̬�ı�
 * 
 * @param actId
 */
private void dispatchChildChanged(String actId, Object actTag) {
if (mStatusListener != null)
mStatusListener.onChildChanged(actId, actTag);
}

/**
 * ���View��View������
 * 
 * @param childClass
 * @param targetActId
 */
private void changeView(View actView, String actId) {
if (actView == null)
return;
if (containerView.getChildCount() > 0)
containerView.removeAllViews();
containerView.addView(actView);
setCurrentInfo(actId);
Child child = childs.get(actId);
Object tag = null;
if (child != null) {
tag = child.tag;
}
dispatchChildChanged(actId, tag);
}

/**
 * ���õ�ǰ��RootViewId
 * 
 * @param currActId
 */
private void setCurrentInfo(String currActId) {
if (currActId == null)
return;
if (currentActId != null && currActId.endsWith(currentActId)) {
return;
}
if (currActId.endsWith(EXTERNAL_ACTIVITY_ID)) {
prevActId = externalActPrevId;
} else {
prevActId = currentActId;
}
currentActId = currActId;
}

/**
 * ��������Activity
 */
private void destroyExternalActivity() {
mAbsGroup.destroyActivity(EXTERNAL_ACTIVITY_ID);
lastExternalAct = null;
externalActPrevId = null;
}
}
 
