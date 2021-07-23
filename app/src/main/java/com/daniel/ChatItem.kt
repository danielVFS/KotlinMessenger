package com.daniel

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder

class ChatItem(): Item<ViewHolder>() {
    override fun bind(viewHolder: ViewHolder, position: Int) {

    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}