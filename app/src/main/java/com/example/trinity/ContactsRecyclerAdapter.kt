package com.example.trinity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import androidx.appcompat.widget.AppCompatTextView
import com.example.githubClient.R
import com.example.trinity.data.Contact

class ContactAdapter(private val contacts: List<Contact>, private val listener: (Contact) -> Unit) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvName: AppCompatTextView = itemView.findViewById(R.id.tvName)
        private val profileImage: CircleImageView = itemView.findViewById(R.id.profile_image)

        @SuppressLint("SetTextI18n")
        fun bind(contact: Contact) {
            tvName.text = "${contact.firstName} ${contact.lastName}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(contacts[position])
        // set listener for each item in the recycler view
        holder.itemView.setOnClickListener { listener(contacts[position]) }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }
}
