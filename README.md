#### 展示效果
![image](https://github.com/zangp/ChooseDateView/blob/master/image/WechatIMG64.jpeg)
![image](https://github.com/zangp/ChooseDateView/blob/master/image/WechatIMG65.jpeg)
![image](https://github.com/zangp/ChooseDateView/blob/master/image/WechatIMG66.jpeg)


#### 使用方式
```
ChooseDateDialogFragment dialogFragment = new ChooseDateDialogFragment();
dialogFragment.show(getSupportFragmentManager(), "date");
```

#### 说明
默认显示的时间为今天后的第七天，如需要自己传入时间的话，可以传入一个calendar对象，如下方法可以找出该calendar对象属于数据源的第几项（当然前提是存在于数据源中）


```
private int getPositionByCalendar(Calendar calendar) {
    if (calendar == null || mData.size() <= 0) {
        return -1;
    }

    for (int i = 0; i < mData.size(); i++) {
        Object object = mData.get(i).getViewContent();
        if (object != null && object instanceof DateOfDayBean) {
            DateOfDayBean dayBean = (DateOfDayBean) object;
            if (compareTowCalendarIsSameDay(calendar, dayBean.getCalendar())) {
                return i;
            }
        }
    }

    return -1;

}
```

