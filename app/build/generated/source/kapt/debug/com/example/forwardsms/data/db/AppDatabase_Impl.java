package com.example.forwardsms.data.db;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.forwardsms.data.dao.ForwardedSmsDao;
import com.example.forwardsms.data.dao.ForwardedSmsDao_Impl;
import com.example.forwardsms.data.dao.LogDao;
import com.example.forwardsms.data.dao.LogDao_Impl;
import com.example.forwardsms.data.dao.PendingMessageDao;
import com.example.forwardsms.data.dao.PendingMessageDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile PendingMessageDao _pendingMessageDao;

  private volatile LogDao _logDao;

  private volatile ForwardedSmsDao _forwardedSmsDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `pending_messages` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `type` TEXT NOT NULL, `payload` TEXT NOT NULL, `status` TEXT NOT NULL, `retryCount` INTEGER NOT NULL, `createdAt` INTEGER NOT NULL, `sentAt` INTEGER)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `logs` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `timestamp` INTEGER NOT NULL, `type` TEXT NOT NULL, `message` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `forwarded_sms` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `smsId` TEXT, `sender` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `smsHash` TEXT NOT NULL, `messageRef` TEXT)");
        _db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_forwarded_sms_smsHash` ON `forwarded_sms` (`smsHash`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'c76ed49085e2e83d20aae281fbc9f777')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `pending_messages`");
        _db.execSQL("DROP TABLE IF EXISTS `logs`");
        _db.execSQL("DROP TABLE IF EXISTS `forwarded_sms`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsPendingMessages = new HashMap<String, TableInfo.Column>(7);
        _columnsPendingMessages.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingMessages.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingMessages.put("payload", new TableInfo.Column("payload", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingMessages.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingMessages.put("retryCount", new TableInfo.Column("retryCount", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingMessages.put("createdAt", new TableInfo.Column("createdAt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPendingMessages.put("sentAt", new TableInfo.Column("sentAt", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPendingMessages = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPendingMessages = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPendingMessages = new TableInfo("pending_messages", _columnsPendingMessages, _foreignKeysPendingMessages, _indicesPendingMessages);
        final TableInfo _existingPendingMessages = TableInfo.read(_db, "pending_messages");
        if (! _infoPendingMessages.equals(_existingPendingMessages)) {
          return new RoomOpenHelper.ValidationResult(false, "pending_messages(com.example.forwardsms.data.entities.PendingMessage).\n"
                  + " Expected:\n" + _infoPendingMessages + "\n"
                  + " Found:\n" + _existingPendingMessages);
        }
        final HashMap<String, TableInfo.Column> _columnsLogs = new HashMap<String, TableInfo.Column>(4);
        _columnsLogs.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLogs.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLogs.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLogs.put("message", new TableInfo.Column("message", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLogs = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLogs = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLogs = new TableInfo("logs", _columnsLogs, _foreignKeysLogs, _indicesLogs);
        final TableInfo _existingLogs = TableInfo.read(_db, "logs");
        if (! _infoLogs.equals(_existingLogs)) {
          return new RoomOpenHelper.ValidationResult(false, "logs(com.example.forwardsms.data.entities.LogEntry).\n"
                  + " Expected:\n" + _infoLogs + "\n"
                  + " Found:\n" + _existingLogs);
        }
        final HashMap<String, TableInfo.Column> _columnsForwardedSms = new HashMap<String, TableInfo.Column>(6);
        _columnsForwardedSms.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsForwardedSms.put("smsId", new TableInfo.Column("smsId", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsForwardedSms.put("sender", new TableInfo.Column("sender", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsForwardedSms.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsForwardedSms.put("smsHash", new TableInfo.Column("smsHash", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsForwardedSms.put("messageRef", new TableInfo.Column("messageRef", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysForwardedSms = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesForwardedSms = new HashSet<TableInfo.Index>(1);
        _indicesForwardedSms.add(new TableInfo.Index("index_forwarded_sms_smsHash", true, Arrays.asList("smsHash"), Arrays.asList("ASC")));
        final TableInfo _infoForwardedSms = new TableInfo("forwarded_sms", _columnsForwardedSms, _foreignKeysForwardedSms, _indicesForwardedSms);
        final TableInfo _existingForwardedSms = TableInfo.read(_db, "forwarded_sms");
        if (! _infoForwardedSms.equals(_existingForwardedSms)) {
          return new RoomOpenHelper.ValidationResult(false, "forwarded_sms(com.example.forwardsms.data.entities.ForwardedSms).\n"
                  + " Expected:\n" + _infoForwardedSms + "\n"
                  + " Found:\n" + _existingForwardedSms);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "c76ed49085e2e83d20aae281fbc9f777", "a7f1ed372ea398a08d658da0caaa0024");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "pending_messages","logs","forwarded_sms");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `pending_messages`");
      _db.execSQL("DELETE FROM `logs`");
      _db.execSQL("DELETE FROM `forwarded_sms`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(PendingMessageDao.class, PendingMessageDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LogDao.class, LogDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ForwardedSmsDao.class, ForwardedSmsDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public PendingMessageDao pendingMessageDao() {
    if (_pendingMessageDao != null) {
      return _pendingMessageDao;
    } else {
      synchronized(this) {
        if(_pendingMessageDao == null) {
          _pendingMessageDao = new PendingMessageDao_Impl(this);
        }
        return _pendingMessageDao;
      }
    }
  }

  @Override
  public LogDao logDao() {
    if (_logDao != null) {
      return _logDao;
    } else {
      synchronized(this) {
        if(_logDao == null) {
          _logDao = new LogDao_Impl(this);
        }
        return _logDao;
      }
    }
  }

  @Override
  public ForwardedSmsDao forwardedSmsDao() {
    if (_forwardedSmsDao != null) {
      return _forwardedSmsDao;
    } else {
      synchronized(this) {
        if(_forwardedSmsDao == null) {
          _forwardedSmsDao = new ForwardedSmsDao_Impl(this);
        }
        return _forwardedSmsDao;
      }
    }
  }
}
