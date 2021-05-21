package com.xufeifan.application.databinding;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.xufeifan.application.BR;


public class LoginBean extends BaseObservable {

    private String account;

    private String password;

    public LoginBean(String account, String password) {
        this.account = account;
        this.password = password;
    }

    @Bindable
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
        notifyPropertyChanged(BR.account);
    }

    @Bindable
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        notifyPropertyChanged(BR.password);
    }
}
