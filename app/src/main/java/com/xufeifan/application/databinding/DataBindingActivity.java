package com.xufeifan.application.databinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xufeifan.application.R;
import com.xufeifan.application.databinding.ActivityDataBindingBinding;

/**
 * 双向绑定
 */
public class DataBindingActivity extends AppCompatActivity {

    private ActivityDataBindingBinding binding;
    private LoginBean loginBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_data_binding);
        loginBean = new LoginBean("15396763201", "884477ws");
        binding.setLogin(loginBean);
        binding.setPresenter(new Presenter());


    }

    public class Presenter {
//        public void onTextChange(Character s, int start, int before, int count) {
//            loginBean.setAccount(s.toString());
//
//        }
//
//        public void onPasswordTextChange(Character s, int start, int before, int count) {
//            loginBean.setPassword(s.toString());
//
//        }

        public void onSubmit(View view) {
            Toast.makeText(DataBindingActivity.this, "SHOW", Toast.LENGTH_SHORT).show();
        }
    }
}
