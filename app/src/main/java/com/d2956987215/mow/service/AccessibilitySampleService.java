package com.d2956987215.mow.service;

import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class AccessibilitySampleService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        int eventType = accessibilityEvent.getEventType();
        switch (eventType) {
            //通知
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                break;
            //窗口改变
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED:
                String className = accessibilityEvent.getClassName().toString();
                String SnsLineUi = "com.tencent.mm.plugin.sns.ui.SnsUploadUI";//发送朋友圈的界面
                if (className.equals(SnsLineUi)) {//发送朋友圈的界面
                    AccessibilityNodeInfo rootNode = getRootInActiveWindow();
                    if (rootNode == null) {
                        return;
                    }

                    List<AccessibilityNodeInfo> edt = rootNode.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/djk");
                    if (edt.size() > 0) {
                        Bundle arguments = new Bundle();
                        arguments.putCharSequence(AccessibilityNodeInfo.ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, "");
                        AccessibilityNodeInfo edtView = edt.get(0);

                        edtView.performAction(AccessibilityNodeInfo.FOCUS_INPUT);
                        edtView.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
//                      edt.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }

                }
                break;
        }
    }

    @Override
    public void onInterrupt() {

    }
}
