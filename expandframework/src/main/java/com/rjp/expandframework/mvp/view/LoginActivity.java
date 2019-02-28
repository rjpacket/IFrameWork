package com.rjp.expandframework.mvp.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rjp.expandframework.R;
import com.rjp.expandframework.mvp.presenter.ILoginPresenter;
import com.rjp.expandframework.mvp.presenter.LoginPresenterImpl;

public class LoginActivity extends Activity implements ILoginView, View.OnClickListener {

    private EditText etUserName;
    private EditText etPassword;
    private Button btnClear;
    private Button btnLogin;
    private ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserName = findViewById(R.id.et_user_name);
        etPassword = findViewById(R.id.et_user_password);
        btnClear = findViewById(R.id.btn_clear);
        btnLogin = findViewById(R.id.btn_login);
        btnClear.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        loginPresenter = new LoginPresenterImpl(this);
        loginPresenter.networkGetUserInfo();
    }

    @Override
    public void clearUserName() {
        etUserName.setText("");
    }

    @Override
    public void clearUserPassword() {
        etPassword.setText("");
    }

    @Override
    public void loginSuccess() {
        toast("登录成功了");
    }

    @Override
    public void loginFailed() {
        toast("登录失败了");
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_clear) {
            loginPresenter.clear();
        } else if (i == R.id.btn_login) {
            loginPresenter.login(etUserName.getText().toString().trim(), etPassword.getText().toString().trim());
        }
    }
}
