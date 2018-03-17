package com.hotelgg.android.choosedateview;

import java.io.Serializable;

/**
 * Created by zangpeng on 2018/3/5.
 */

public class ChooseDateBean implements Serializable {
    private int viewType;
    private Object viewContent;

    public ChooseDateBean(int viewType, Object viewContent) {
        this.viewType = viewType;
        this.viewContent = viewContent;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public Object getViewContent() {
        return viewContent;
    }

    public void setViewContent(Object viewContent) {
        this.viewContent = viewContent;
    }
}
