package com.dhc.library.data.account;

import android.content.Context;
import android.text.TextUtils;

import com.dhc.library.utils.AppContext;


/**
 * 创建者：邓浩宸
 * 时间 ：2017/6/29 17:42
 * 描述 ：登陆账号管理类
 */
public enum AccountManager {

    INSTANCE;

    private final AuthPreferences authPreferences;
    private Account mCurrentAccount;
    private Context mContext;

    AccountManager() {
        mContext = AppContext.get();
        authPreferences = new AuthPreferences(AppContext.get());
    }

    public boolean isLogin() {
        return authPreferences.isLogin();
    }

    public void logout() {
        mCurrentAccount = null;
        authPreferences.clear();
    }

    /**
     * 更新用户数据
     *
     * @param account
     */
    public void refreshAccount(Account account) {
        mCurrentAccount = account;
        authPreferences.setAccessToken(account.accessToken());
        authPreferences.setRefreshToken(account.refreshToken());
        authPreferences.setUser(account.name());
        authPreferences.setUserData(account.toJson());
    }

    /**
     * 登录成功获取存入数据
     *
     * @param account
     */
    public void storeAccount(Account account) {
        mCurrentAccount = account;
        authPreferences.setAccessToken(account.accessToken());
        authPreferences.setRefreshToken(account.refreshToken());
        authPreferences.setUser(account.name());
        authPreferences.setUserData(account.toJson());
    }

    public String getUserId() {
        return getCurrentAccount() != null ? getCurrentAccount().getUserId() : "";
    }


    public String getAccessToken() {
        return authPreferences.getAccessToken();
    }

    /**
     * 刷新AccessToken
     *
     * @param accessToken
     */
    public void refreshAccessToken(String accessToken) {
        authPreferences.setAccessToken(accessToken);
    }

    /**
     * 刷新refreshToken
     *
     * @param refreshToken
     */
    public void refreshRefreshToken(String refreshToken) {
        authPreferences.setRefreshToken(refreshToken);
    }

    public String getRefreshToken() {
        return authPreferences.getRefreshToken();
    }

    public String user() {
        return authPreferences.getUser();
    }

    @SuppressWarnings("unchecked")
    public <T extends Account> T getCurrentAccount() {
        if (mCurrentAccount == null) {
            String accountJson = authPreferences.getUserData();
            if (!TextUtils.isEmpty(accountJson)
                    && mContext instanceof AccountProvider) {
                mCurrentAccount = ((AccountProvider) mContext).provideAccount(accountJson);
            }
        }
        return (T) mCurrentAccount;
    }
    }


