package com.example.buxiaohui.myapplication.database;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.example.buxiaohui.myapplication.bean.User;

import com.example.buxiaohui.myapplication.bean.TestBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TEST_BEAN".
*/
public class TestBeanDao extends AbstractDao<TestBean, Long> {

    public static final String TABLENAME = "TEST_BEAN";

    /**
     * Properties of entity TestBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Ids = new Property(0, Long.class, "ids", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property UserIds = new Property(2, long.class, "userIds", false, "USER_IDS");
    }

    private DaoSession daoSession;


    public TestBeanDao(DaoConfig config) {
        super(config);
    }
    
    public TestBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TEST_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: ids
                "\"NAME\" TEXT," + // 1: name
                "\"USER_IDS\" INTEGER NOT NULL );"); // 2: userIds
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TEST_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TestBean entity) {
        stmt.clearBindings();
 
        Long ids = entity.getIds();
        if (ids != null) {
            stmt.bindLong(1, ids);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
        stmt.bindLong(3, entity.getUserIds());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TestBean entity) {
        stmt.clearBindings();
 
        Long ids = entity.getIds();
        if (ids != null) {
            stmt.bindLong(1, ids);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
        stmt.bindLong(3, entity.getUserIds());
    }

    @Override
    protected final void attachEntity(TestBean entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public TestBean readEntity(Cursor cursor, int offset) {
        TestBean entity = new TestBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // ids
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.getLong(offset + 2) // userIds
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TestBean entity, int offset) {
        entity.setIds(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserIds(cursor.getLong(offset + 2));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(TestBean entity, long rowId) {
        entity.setIds(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(TestBean entity) {
        if(entity != null) {
            return entity.getIds();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TestBean entity) {
        return entity.getIds() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getUserDao().getAllColumns());
            builder.append(" FROM TEST_BEAN T");
            builder.append(" LEFT JOIN USER T0 ON T.\"USER_IDS\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected TestBean loadCurrentDeep(Cursor cursor, boolean lock) {
        TestBean entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        User user = loadCurrentOther(daoSession.getUserDao(), cursor, offset);
         if(user != null) {
            entity.setUser(user);
        }

        return entity;    
    }

    public TestBean loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<TestBean> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<TestBean> list = new ArrayList<TestBean>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<TestBean> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<TestBean> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
