package com.example.listfiltering

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

class MyQuoteAdapter : RecyclerView.Adapter<MyQuoteAdapter.ViewHolder>{
    var data = Data()
    private var items = listOf<Student>()
    private var listener //to open news detail
            : ItemClickListener? = null


    constructor(listener: ItemClickListener?){
        this.listener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_row, parent, false)
        val params = RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        view.layoutParams = params
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val cnt:Int = position+1
        val info:String = """Student $cnt ${item.name} ${item.surname}
${item.univer}""".trimIndent()

        holder.tvInfo.text = info
        holder.itemView.setOnClickListener(){
        if(listener!=null){
            listener!!.itemClick(position, item)
        }
        }
    }
    fun replaceItems(items: List<Student>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    inner class ViewHolder(containerView: View) : RecyclerView.ViewHolder(containerView){
        var tvInfo: TextView
//        var sourceTextView: TextView
        init {
            tvInfo = containerView.findViewById(R.id.tvInfo) //post title
//            sourceTextView = containerView.findViewById(R.id.sourceTextView) //post title

        }
    }

    interface ItemClickListener {
        fun itemClick(position: Int, item: Student?)
        fun onClick(v: View?, position: Int, item: Student?)
    }
}