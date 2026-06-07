package com.example.forwardsms.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.forwardsms.data.entities.ForwardedSms;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ForwardedSmsDao_Impl implements ForwardedSmsDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ForwardedSms> __insertionAdapterOfForwardedSms;

  public ForwardedSmsDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfForwardedSms = new EntityInsertionAdapter<ForwardedSms>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `forwarded_sms` (`id`,`smsId`,`sender`,`timestamp`,`smsHash`,`messageRef`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, ForwardedSms value) {
        stmt.bindLong(1, value.getId());
        if (value.getSmsId() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getSmsId());
        }
        if (value.getSender() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSender());
        }
        stmt.bindLong(4, value.getTimestamp());
        if (value.getSmsHash() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getSmsHash());
        }
        if (value.getMessageRef() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getMessageRef());
        }
      }
    };
  }

  @Override
  public Object insert(final ForwardedSms item, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          long _result = __insertionAdapterOfForwardedSms.insertAndReturnId(item);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object existsByHash(final String hash, final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM forwarded_sms WHERE smsHash = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (hash == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, hash);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if(_cursor.moveToFirst()) {
            final Integer _tmp;
            if (_cursor.isNull(0)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getInt(0);
            }
            _result = _tmp;
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getById(final long id, final Continuation<? super ForwardedSms> $completion) {
    final String _sql = "SELECT * FROM forwarded_sms WHERE id = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ForwardedSms>() {
      @Override
      public ForwardedSms call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSmsId = CursorUtil.getColumnIndexOrThrow(_cursor, "smsId");
          final int _cursorIndexOfSender = CursorUtil.getColumnIndexOrThrow(_cursor, "sender");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfSmsHash = CursorUtil.getColumnIndexOrThrow(_cursor, "smsHash");
          final int _cursorIndexOfMessageRef = CursorUtil.getColumnIndexOrThrow(_cursor, "messageRef");
          final ForwardedSms _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpSmsId;
            if (_cursor.isNull(_cursorIndexOfSmsId)) {
              _tmpSmsId = null;
            } else {
              _tmpSmsId = _cursor.getString(_cursorIndexOfSmsId);
            }
            final String _tmpSender;
            if (_cursor.isNull(_cursorIndexOfSender)) {
              _tmpSender = null;
            } else {
              _tmpSender = _cursor.getString(_cursorIndexOfSender);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpSmsHash;
            if (_cursor.isNull(_cursorIndexOfSmsHash)) {
              _tmpSmsHash = null;
            } else {
              _tmpSmsHash = _cursor.getString(_cursorIndexOfSmsHash);
            }
            final String _tmpMessageRef;
            if (_cursor.isNull(_cursorIndexOfMessageRef)) {
              _tmpMessageRef = null;
            } else {
              _tmpMessageRef = _cursor.getString(_cursorIndexOfMessageRef);
            }
            _result = new ForwardedSms(_tmpId,_tmpSmsId,_tmpSender,_tmpTimestamp,_tmpSmsHash,_tmpMessageRef);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getByMessageRef(final String ref,
      final Continuation<? super ForwardedSms> $completion) {
    final String _sql = "SELECT * FROM forwarded_sms WHERE messageRef = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (ref == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, ref);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ForwardedSms>() {
      @Override
      public ForwardedSms call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSmsId = CursorUtil.getColumnIndexOrThrow(_cursor, "smsId");
          final int _cursorIndexOfSender = CursorUtil.getColumnIndexOrThrow(_cursor, "sender");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfSmsHash = CursorUtil.getColumnIndexOrThrow(_cursor, "smsHash");
          final int _cursorIndexOfMessageRef = CursorUtil.getColumnIndexOrThrow(_cursor, "messageRef");
          final ForwardedSms _result;
          if(_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpSmsId;
            if (_cursor.isNull(_cursorIndexOfSmsId)) {
              _tmpSmsId = null;
            } else {
              _tmpSmsId = _cursor.getString(_cursorIndexOfSmsId);
            }
            final String _tmpSender;
            if (_cursor.isNull(_cursorIndexOfSender)) {
              _tmpSender = null;
            } else {
              _tmpSender = _cursor.getString(_cursorIndexOfSender);
            }
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final String _tmpSmsHash;
            if (_cursor.isNull(_cursorIndexOfSmsHash)) {
              _tmpSmsHash = null;
            } else {
              _tmpSmsHash = _cursor.getString(_cursorIndexOfSmsHash);
            }
            final String _tmpMessageRef;
            if (_cursor.isNull(_cursorIndexOfMessageRef)) {
              _tmpMessageRef = null;
            } else {
              _tmpMessageRef = _cursor.getString(_cursorIndexOfMessageRef);
            }
            _result = new ForwardedSms(_tmpId,_tmpSmsId,_tmpSender,_tmpTimestamp,_tmpSmsHash,_tmpMessageRef);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
