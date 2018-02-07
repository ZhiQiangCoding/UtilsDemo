package com.czq.utilsdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.czq.utilsdemo.R;

public class TestTouchActivity extends AppCompatActivity {

    public TestTouchActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_touch);

    }

    /**
     * dispatchTouchEvent作用：决定事件是否由onInterceptTouchEvent来拦截处理。
     * 返回super.dispatchTouchEvent时，由onInterceptTouchEvent来决定事件的流向
     * 返回false时，会继续分发事件，自己内部只处理了ACTION_DOWN
     * 返回true时，不会继续分发事件，自己内部处理了所有事件（ACTION_DOWN,ACTION_MOVE,ACTION_UP）
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 返回true时，内部处理所有的事件，换句话说，后续事件将继续传递给该view的onTouchEvent()处理
     * 返回false时，事件会向上传递，由onToucEvent来接受，如果最上面View中的onTouchEvent也返回false的话，那么事件就会消失
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
