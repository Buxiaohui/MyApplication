package com.example.buxiaohui.myapplication.database;

import android.database.sqlite.SQLiteDatabase;

import com.example.buxiaohui.myapplication.Global;
import com.example.buxiaohui.myapplication.bean.TestBean;
import com.example.buxiaohui.myapplication.bean.User;

import org.greenrobot.greendao.database.Database;

/**
 * Created by buxiaohui on 12/10/2016.
 */

public class DbManager {

    private static DbManager mDbManager;
    private static DaoMaster.DevOpenHelper mDevOpenHelper;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;
    private static SQLiteDatabase mSqLiteDatabase;
    private static Database db;

    public DbManager() {
        init();
    }

    public static DbManager getInstance() {
        if (mDbManager == null) {
            synchronized (DbManager.class) {
                if (null == mDbManager) {
                    mDbManager = new DbManager();
                }
            }

        }
        return mDbManager;
    }

    public void init() {
        mDevOpenHelper = new DaoMaster.DevOpenHelper(Global.APP_CONTEXT, "test-app", null);
        mSqLiteDatabase = mDevOpenHelper.getWritableDatabase();
        db = mDevOpenHelper.getWritableDb();
        mDaoMaster = new DaoMaster(mSqLiteDatabase);
        DaoMaster.createAllTables(db,true);
        mDaoSession = mDaoMaster.newSession();
    }

    public static SQLiteDatabase getSqLiteDatabase() {
        if (mDevOpenHelper == null) {
            getInstance();

        }
        return mSqLiteDatabase;
    }

    public static DaoSession getDaoSession() {
        if (mDaoSession == null) {
            synchronized (DbManager.class) {
                if (null == mDaoSession) {
                    mDaoSession = getDaoMaster().newSession();
                }
            }
        }
        return mDaoSession;
    }

    public static DaoMaster getDaoMaster() {
        if (mDaoMaster == null) {
            synchronized (DbManager.class) {
                if (null == mDaoMaster) {
                    mDaoMaster = new DaoMaster(getSqLiteDatabase());
                }
            }
        }
        return mDaoMaster;
    }

    /****************************************************************/
    /****************************************************************/
    /*****************************User******************************/
    /****************************************************************/

    public static void insertUser(User user) {
        if (user != null) {
            DbManager.getDaoSession().getUserDao().insert(user);
        }

    }
    public static void insertTestBean(TestBean testBean) {
        if (testBean != null) {
            DbManager.getDaoSession().getTestBeanDao().insert(testBean);
        }

    }

}
