package com.kltn.ecodemy.domain.models.zalo

import com.kltn.ecodemy.constant.Constant.MAC_KEY
import com.kltn.ecodemy.constant.Constant.ZALO_APP_ID
import com.kltn.ecodemy.domain.helper.ZaloHelpers
import okhttp3.FormBody
import java.util.Date

data class ZaloOrderData(
    val courseTitle: String,
    val app_id: String = ZALO_APP_ID.toString(),
    val app_user: String = "Android_Demo",
    val app_time: String = Date().time.toString(),
    val amount: String,
    val app_trans_id: String = ZaloHelpers.appTransId,
    val embed_data: String = "{}",
    val item: String = "[]",
    val bank_code: String = "zalopayapp",
    val description: String = courseTitle + "\n" + app_trans_id,
    val mac: String = ZaloHelpers.getMac(
        key = MAC_KEY,
        data = String.format(
            "%s|%s|%s|%s|%s|%s|%s",
            app_id,
            app_trans_id,
            app_user,
            amount,
            app_time,
            embed_data,
            item
        ),
    )
){

    fun formBody() = FormBody.Builder()
        .add("app_id", app_id)
        .add("app_user", app_user)
        .add("app_time", app_time)
        .add("amount", amount)
        .add("app_trans_id", app_trans_id)
        .add("embed_data", embed_data)
        .add("item", item)
        .add("bank_code", bank_code)
        .add("description", description)
        .add("mac", mac)
        .build()
}
