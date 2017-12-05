package com.hy.mvp.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.hy.mvp.greendao.bean.ContactsPortraitConverter;
import com.hy.mvp.ui.bean.ContactsPortrait;

import com.hy.mvp.ui.bean.AddrBook;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ADDR_BOOK".
*/
public class AddrBookDao extends AbstractDao<AddrBook, Long> {

    public static final String TABLENAME = "ADDR_BOOK";

    /**
     * Properties of entity AddrBook.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property _id = new Property(0, Long.class, "_id", true, "_id");
        public final static Property Id = new Property(1, String.class, "id", false, "ID");
        public final static Property OwnerId = new Property(2, String.class, "ownerId", false, "OWNER_ID");
        public final static Property ContactsId = new Property(3, String.class, "contactsId", false, "CONTACTS_ID");
        public final static Property Status = new Property(4, int.class, "status", false, "STATUS");
        public final static Property Create_time = new Property(5, long.class, "create_time", false, "CREATE_TIME");
        public final static Property Contacts_name = new Property(6, String.class, "contacts_name", false, "CONTACTS_NAME");
        public final static Property Contacts_school = new Property(7, String.class, "contacts_school", false, "CONTACTS_SCHOOL");
        public final static Property Pingyin = new Property(8, String.class, "pingyin", false, "PINGYIN");
        public final static Property Contacts_portrait = new Property(9, String.class, "contacts_portrait", false, "CONTACTS_PORTRAIT");
    }

    private final ContactsPortraitConverter contacts_portraitConverter = new ContactsPortraitConverter();

    public AddrBookDao(DaoConfig config) {
        super(config);
    }
    
    public AddrBookDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ADDR_BOOK\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: _id
                "\"ID\" TEXT," + // 1: id
                "\"OWNER_ID\" TEXT," + // 2: ownerId
                "\"CONTACTS_ID\" TEXT," + // 3: contactsId
                "\"STATUS\" INTEGER NOT NULL ," + // 4: status
                "\"CREATE_TIME\" INTEGER NOT NULL ," + // 5: create_time
                "\"CONTACTS_NAME\" TEXT," + // 6: contacts_name
                "\"CONTACTS_SCHOOL\" TEXT," + // 7: contacts_school
                "\"PINGYIN\" TEXT," + // 8: pingyin
                "\"CONTACTS_PORTRAIT\" TEXT);"); // 9: contacts_portrait
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ADDR_BOOK\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, AddrBook entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String ownerId = entity.getOwnerId();
        if (ownerId != null) {
            stmt.bindString(3, ownerId);
        }
 
        String contactsId = entity.getContactsId();
        if (contactsId != null) {
            stmt.bindString(4, contactsId);
        }
        stmt.bindLong(5, entity.getStatus());
        stmt.bindLong(6, entity.getCreate_time());
 
        String contacts_name = entity.getContacts_name();
        if (contacts_name != null) {
            stmt.bindString(7, contacts_name);
        }
 
        String contacts_school = entity.getContacts_school();
        if (contacts_school != null) {
            stmt.bindString(8, contacts_school);
        }
 
        String pingyin = entity.getPingyin();
        if (pingyin != null) {
            stmt.bindString(9, pingyin);
        }
 
        ContactsPortrait contacts_portrait = entity.getContacts_portrait();
        if (contacts_portrait != null) {
            stmt.bindString(10, contacts_portraitConverter.convertToDatabaseValue(contacts_portrait));
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, AddrBook entity) {
        stmt.clearBindings();
 
        Long _id = entity.get_id();
        if (_id != null) {
            stmt.bindLong(1, _id);
        }
 
        String id = entity.getId();
        if (id != null) {
            stmt.bindString(2, id);
        }
 
        String ownerId = entity.getOwnerId();
        if (ownerId != null) {
            stmt.bindString(3, ownerId);
        }
 
        String contactsId = entity.getContactsId();
        if (contactsId != null) {
            stmt.bindString(4, contactsId);
        }
        stmt.bindLong(5, entity.getStatus());
        stmt.bindLong(6, entity.getCreate_time());
 
        String contacts_name = entity.getContacts_name();
        if (contacts_name != null) {
            stmt.bindString(7, contacts_name);
        }
 
        String contacts_school = entity.getContacts_school();
        if (contacts_school != null) {
            stmt.bindString(8, contacts_school);
        }
 
        String pingyin = entity.getPingyin();
        if (pingyin != null) {
            stmt.bindString(9, pingyin);
        }
 
        ContactsPortrait contacts_portrait = entity.getContacts_portrait();
        if (contacts_portrait != null) {
            stmt.bindString(10, contacts_portraitConverter.convertToDatabaseValue(contacts_portrait));
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public AddrBook readEntity(Cursor cursor, int offset) {
        AddrBook entity = new AddrBook( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // _id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // ownerId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // contactsId
            cursor.getInt(offset + 4), // status
            cursor.getLong(offset + 5), // create_time
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // contacts_name
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // contacts_school
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // pingyin
            cursor.isNull(offset + 9) ? null : contacts_portraitConverter.convertToEntityProperty(cursor.getString(offset + 9)) // contacts_portrait
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, AddrBook entity, int offset) {
        entity.set_id(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setId(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setOwnerId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setContactsId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setStatus(cursor.getInt(offset + 4));
        entity.setCreate_time(cursor.getLong(offset + 5));
        entity.setContacts_name(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setContacts_school(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setPingyin(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setContacts_portrait(cursor.isNull(offset + 9) ? null : contacts_portraitConverter.convertToEntityProperty(cursor.getString(offset + 9)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(AddrBook entity, long rowId) {
        entity.set_id(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(AddrBook entity) {
        if(entity != null) {
            return entity.get_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(AddrBook entity) {
        return entity.get_id() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
