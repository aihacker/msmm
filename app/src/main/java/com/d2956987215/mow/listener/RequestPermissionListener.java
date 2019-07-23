package com.d2956987215.mow.listener;

import java.util.List;

/**
 * created by lq
 */

public interface RequestPermissionListener {
    void onGranted();

    void onDeny(List<String> permissions);
}