package com.example.proj_keep

import lv.romstr.mobile.rtu_android.Note

interface AdapterClickListener {

    fun itemClicked(item: Note)

    fun deleteClicked(item: Note)

}