package com.example.forwardsms.core.utils

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract

object SmsHelper {
    fun getContactName(context: Context, phoneNumber: String): String? {
        val uri = Uri.withAppendedPath(
            ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
            Uri.encode(phoneNumber)
        )

        context.contentResolver.query(
            uri,
            arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME),
            null,
            null,
            null
        )?.use { cursor ->
            if (cursor.moveToFirst()) {
                return cursor.getString(
                    cursor.getColumnIndexOrThrow(
                        ContactsContract.PhoneLookup.DISPLAY_NAME
                    )
                )
            }
        }

        return null
    }
}