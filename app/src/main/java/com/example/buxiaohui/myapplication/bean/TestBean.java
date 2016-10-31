package com.example.buxiaohui.myapplication.bean;

import com.example.buxiaohui.myapplication.database.UserDao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;

import com.example.buxiaohui.myapplication.database.DaoSession;
import com.example.buxiaohui.myapplication.database.TestBeanDao;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Created by buxiaohui on 14/10/2016.
 */
@Entity
public class TestBean {

    @Id
    private Long ids;
    @ToOne(joinProperty = "userIds")
    private User user;
    private String name;
    private long userIds;
    @Generated(hash = 251390918)
    private transient Long user__resolvedKey;
    /**
     * Used for active entity operations.
     */
    @Generated(hash = 1254109139)
    private transient TestBeanDao myDao;
    /**
     * Used to resolve relations
     */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    @Generated(hash = 2087637710)
    public TestBean() {
    }

    @Generated(hash = 164746956)
    public TestBean(Long ids, String name, long userIds) {
        this.ids = ids;
        this.name = name;
        this.userIds = userIds;
    }

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2016931067)
    public User getUser() {
        long __key = this.userIds;
        if (user__resolvedKey == null || !user__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserDao targetDao = daoSession.getUserDao();
            User userNew = targetDao.load(__key);
            synchronized (this) {
                user = userNew;
                user__resolvedKey = __key;
            }
        }
        return user;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1484979492)
    public void setUser(@NotNull User user) {
        if (user == null) {
            throw new DaoException(
                    "To-one property 'userIds' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.user = user;
            userIds = user.getUserId();
            user__resolvedKey = userIds;
        }
    }

    public long getUserId() {
        return userIds;
    }

    public void setUserId(long userId) {
        this.userIds = userId;
    }

    public Long getId() {
        return ids;
    }

    public void setId(Long id) {
        this.ids = id;
    }

    public void setId(long id) {
        this.ids = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * called by internal mechanisms, do not call yourself.
     */
    @Generated(hash = 1819053713)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getTestBeanDao() : null;
    }

    public Long getIds() {
        return this.ids;
    }

    public void setIds(Long ids) {
        this.ids = ids;
    }

    public long getUserIds() {
        return this.userIds;
    }

    public void setUserIds(long userIds) {
        this.userIds = userIds;
    }

}
