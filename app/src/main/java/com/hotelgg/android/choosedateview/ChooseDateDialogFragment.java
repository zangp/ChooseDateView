package com.hotelgg.android.choosedateview;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by zangpeng on 2018/3/5.
 */

public class ChooseDateDialogFragment extends DialogFragment implements View.OnClickListener {
    private RecyclerView mDateRecyclerView;
    private ChooseDateRecyclerAdapter mChooseRecyclerAdapter;
    private IconTextView mIconClose;
    private ArrayList<ChooseDateBean> mData = new ArrayList<>();
    private int mStartPosition = -1, mEndPosition = -1;
    private OnChooseDateCompletedListener chooseDateCompletedListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("zp_test", "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 去掉默认title
        this.getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 外部点击是否可以收起该dialog
        getDialog().setCanceledOnTouchOutside(false);

        Window window = this.getDialog().getWindow();
        if (window != null) {
            //去掉dialog默认的padding
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            // 设置高度为屏幕高度的四分之三
            lp.height = UIUtils.getScreenHeight(getActivity()) * 3 / 4;
            //设置dialog的位置在底部
            lp.gravity = Gravity.BOTTOM;
            //设置dialog的动画
            lp.windowAnimations = R.style.AnimBottom;
            window.setAttributes(lp);
            window.setBackgroundDrawable(new ColorDrawable());
        }

        View view = inflater.inflate(R.layout.dialog_choose_date, container, false);

        initView(view);
        initRecyclerView();
        initListener();
        initData();

        return view;
    }

    public void setChooseDateCompletedListener(OnChooseDateCompletedListener chooseDateCompletedListener) {
        this.chooseDateCompletedListener = chooseDateCompletedListener;
    }

    private void initView(View view) {
        mDateRecyclerView = (RecyclerView) view.findViewById(R.id.choose_date_recycler_view);
        mIconClose = (IconTextView) view.findViewById(R.id.choose_date_close);
    }

    private void initListener() {
        mIconClose.setOnClickListener(this);
    }

    private void initRecyclerView() {
        mChooseRecyclerAdapter = new ChooseDateRecyclerAdapter(getActivity(), mData);

        mChooseRecyclerAdapter.setDateCompleteListener(new ChooseDateRecyclerAdapter.DateCompleteListener() {
            @Override
            public void onSelectComplete(Calendar start, Calendar end, int startPosition, int endPosition) {
                mStartPosition = startPosition;
                mEndPosition = endPosition;

                if (chooseDateCompletedListener != null) {
                    chooseDateCompletedListener.onChooseDateCompleted(start, end, startPosition, endPosition);
                }

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                    }
                }, 500);
            }
        });

        final GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 7);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                // 这个item占有几个位置
                return (mChooseRecyclerAdapter.getItemViewType(position)
                        == ChooseDateRecyclerAdapter.TYPE_YEAR_MONTH ? layoutManager.getSpanCount() : 1);
            }
        });

        mDateRecyclerView.setAdapter(mChooseRecyclerAdapter);
        mDateRecyclerView.setLayoutManager(layoutManager);
        mDateRecyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void initData() {

        if (mData != null && mData.size() <= 0) {
            Calendar calendar = Calendar.getInstance();
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

            for (int i = 0; i < 12; i++) {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                mData.add(new ChooseDateBean(1, String.valueOf(calendar.get(Calendar.MONTH) + 1) + "月"));

                int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                for (int j = 1; j < dayOfWeek; j++) {
                    DateOfDayBean bean = new DateOfDayBean();
                    bean.setDayOfMonth("");
                    mData.add(new ChooseDateBean(2, bean));
                }

                int days = getDayCountByYearAndMonth(calendar.get(Calendar.YEAR), (calendar.get(Calendar.MONTH) + 1));
                for (int j = 0; j < days; j++) {
                    DateOfDayBean bean = new DateOfDayBean();
                    bean.setDayOfMonth(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
                    bean.setCalendar(calendar);
//                    Log.e("zp_test", "day: " + calendar.get(Calendar.DAY_OF_MONTH) + " month: " + calendar.get(Calendar.MONTH));
                    //节日
                    if (calendar.get(Calendar.MONTH) == 0 && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
                        bean.setHolidayText("元旦节");
                    }

                    if (calendar.get(Calendar.MONTH) == 1 && calendar.get(Calendar.DAY_OF_MONTH) == 14) {
                        bean.setHolidayText("情人节");
                    }

                    if (calendar.get(Calendar.MONTH) == 2 && calendar.get(Calendar.DAY_OF_MONTH) == 8) {
                        bean.setHolidayText("妇女节");
                    }

                    if (calendar.get(Calendar.MONTH) == 2 && calendar.get(Calendar.DAY_OF_MONTH) == 12) {
                        bean.setHolidayText("植树节");
                    }

                    if (calendar.get(Calendar.MONTH) == 3 && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
                        bean.setHolidayText("愚人节");
                    }

                    if (calendar.get(Calendar.MONTH) == 4 && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
                        bean.setHolidayText("劳动节");
                    }

                    if (calendar.get(Calendar.MONTH) == 4 && calendar.get(Calendar.DAY_OF_MONTH) == 4) {
                        bean.setHolidayText("青年节");
                    }

                    if (calendar.get(Calendar.MONTH) == 5 && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
                        bean.setHolidayText("儿童节");
                    }

                    if (calendar.get(Calendar.MONTH) == 6 && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
                        bean.setHolidayText("建党节");
                    }

                    if (calendar.get(Calendar.MONTH) == 7 && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
                        bean.setHolidayText("建军节");
                    }

                    if (calendar.get(Calendar.MONTH) == 8 && calendar.get(Calendar.DAY_OF_MONTH) == 10) {
                        bean.setHolidayText("教师节");
                    }

                    if (calendar.get(Calendar.MONTH) == 9 && calendar.get(Calendar.DAY_OF_MONTH) == 1) {
                        bean.setHolidayText("国庆节");
                    }

                    if (calendar.get(Calendar.MONTH) == 10 && calendar.get(Calendar.DAY_OF_MONTH) == 22) {
                        bean.setHolidayText("感恩节");
                    }

                    if (calendar.get(Calendar.MONTH) == 11 && calendar.get(Calendar.DAY_OF_MONTH) == 25) {
                        bean.setHolidayText("圣诞节");
                    }

//                    Log.e("zp_test", "initData: " + calendar.compareTo(Calendar.getInstance()));这个比较算了时分秒

                    if (i == 0 && calendar.get(Calendar.DAY_OF_MONTH) == currentDay) {
                        bean.setToday(true);
                        bean.setCanClick(true);
                    }

                    if (calendar.after(Calendar.getInstance())) {
                        bean.setCanClick(true);
                    }

                    mData.add(new ChooseDateBean(2, bean));

                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                }

            }

        }


        if (mStartPosition != -1 && mEndPosition != -1) {
            mChooseRecyclerAdapter.setStartPosition(mStartPosition);
            mChooseRecyclerAdapter.setEndPosition(mEndPosition);
        } else {
            Calendar calendar1 = Calendar.getInstance();
            calendar1.add(Calendar.DAY_OF_MONTH, 7);
            int pos = getPositionByCalendar(calendar1);
            if (pos >= 0 && pos < mData.size() - 1) {
                mStartPosition = pos;
                mChooseRecyclerAdapter.setStartPosition(pos);
                mChooseRecyclerAdapter.setEndPosition(pos);
            }
        }


        mChooseRecyclerAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        if (v == mIconClose) {
            dismiss();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mStartPosition != -1) {
            mDateRecyclerView.scrollToPosition(mStartPosition);
        }
    }

    /**
     * 计算指定年月的天数
     */
private int getDayCountByYearAndMonth(int year, int month) {
    int days = 30;
    switch (month) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            days = 31;
            break;
        case 4:
        case 6:
        case 9:
        case 11:
            days = 30;
            break;
        case 2:
            if (year % 400 == 0 || (year % 100 != 0 && year % 4 == 0)) {
                days = 29;
            } else {
                days = 28;
            }
            break;
    }
    return days;
}

    private boolean compareTowCalendarIsSameDay(Calendar calendar1, Calendar calendar2) {
        if (calendar1 == null || calendar2 == null) {
            return false;
        }

        return calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
                && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)
                && calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);

    }

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

    public interface OnChooseDateCompletedListener {
        void onChooseDateCompleted(Calendar calendarStart, Calendar calendarEnd, int start, int end);
    }

}
