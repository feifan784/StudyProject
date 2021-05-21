package com.xufeifan.application.encryption;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.xufeifan.application.R;

/**
 * 加解密技术分类
 * -哈希函数----SHA256、MD5
 * -对称加密----AES、DES
 * -非对称加密----RSA、ECC、Elgamal
 * -密钥交换----DH、ECDH
 */

public class EncryptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encryption);



    }
}
