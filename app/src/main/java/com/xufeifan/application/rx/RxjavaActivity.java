package com.xufeifan.application.rx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xufeifan.application.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 操作符
 * create just from defer...创建型
 * transform 转换
 * filter 过滤
 */
public class RxjavaActivity extends AppCompatActivity {

    private TextView tvSatrt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        initView();

        initListener();
    }

    private void initListener() {
        tvSatrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doRxjavaAction();
            }
        });
    }

    //执行Rxjava示例代码
    private void doRxjavaAction() {

        //create
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("Hello world!!!");
                emitter.onComplete();

            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                Log.i("xfftll", s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {


            }
        });

    }

    private void initView() {
        tvSatrt = findViewById(R.id.tv_start);

    }
}
