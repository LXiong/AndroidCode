import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

/**
 * ����ע�����Activity���ṩActivity����ת
 * 
 * @author skg
 * 
 */
public class ChildActivityManager {
// û�б���ǰע���Activityͳһֻ�����һ��ID
private final String EXTERNAL_ACTIVITY_ID = "ExternalAct";
private Class<? extends AbsActivity> lastExternalAct;// ���һ��������������Activity
private String externalActPrevId;// ������Activity����֮ǰ��ʾ��Activity��Id

private String prevActId;// ǰһ��ʾ��view
private String currentActId;
private String defaultActId;
private AbsActivityGroup mAbsGroup;
private ViewGroup containerView;

private class Child {
Class<? extends AbsActivity> childClass;
Object tag;
}

/**
 * ActivityGroup�Զ��������Activity
 */
private HashMap<String, Child> childs;

protected ChildActivityManager(AbsActivityGroup ag, ViewGroup actPlace) {
mAbsGroup = ag;
containerView = actPlace;
childs = new HashMap<String, Child>();
}

/**
 * ��ӱ��������Activity
 * 
 * @param cls
 */
public void addChildActivity(Class<? extends AbsActivity> cls) {
addChildActivity(cls, null, false);
}

/**
 * ��ӱ��������Activity
 * 
 * @param cls
 * @param tag
 */
public void addChildActivity(Class<? extends AbsActivity> cls, Object tag) {
addChildActivity(cls, tag, false);
}

/**
 * ��ӱ��������Activity
 * 
 * @param cls
 * @param tag
 * @param defaultAct
 */
public void addChildActivity(Class<? extends AbsActivity> cls, Object tag,
boolean defaultAct) {
Child child = createChild(cls, tag);
if (child != null)
childs.put(cls.getCanonicalName(), child);
if (defaultAct) {
setDefaultActivity(cls);
}
}

/**
 * ��ָ����Activity����ΪĬ�ϵ�<br>
 * �ķ�����������Object��Tag
 * 
 * @see #addChildActivity(Class, Object, boolean)
 * 
 * @param cls
 */
public void setDefaultActivity(Class<? extends AbsActivity> cls) {
if (!childs.containsKey(cls.getCanonicalName())) {
Child child = createChild(cls, null);
childs.put(cls.getCanonicalName(), child);
}
defaultActId = cls.getCanonicalName();
}

/**
 * ����ע��ʱ���Class�Ƴ���Activity
 * 
 * @param cls
 */
public void removeChildActivity(Class<? extends AbsActivity> cls) {
childs.remove(cls.getCanonicalName());
}

/**
 * @see #showActivity(Class, Bundle)
 */
public void showActivity(Class<? extends AbsActivity> cls) {
showActivity(cls, null);
}

/**
 * ����ע��ʱ���Id������Activity
 * 
 * �Ƽ�ʹ�� {@link #setDefaultActivity(Class)}
 * ����Id�������ȷ�����Ѿ�ע�����Id�������������ʧ�󣬺��п���д��Id�������Ҳ���ע��Activity
 * 
 * @param actId
 */
public void showActivity(String actId, Bundle data) {
showActivity(childs.get(actId).childClass, data);
}
