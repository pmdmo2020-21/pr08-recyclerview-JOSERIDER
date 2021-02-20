package es.iessaladillo.pedrojoya.pr06.ui.users

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import es.iessaladillo.pedrojoya.pr06.data.model.User
import es.iessaladillo.pedrojoya.pr06.databinding.UsersActivityItemBinding
import es.iessaladillo.pedrojoya.pr06.utils.loadUrl

typealias onClick = (pos:Int) -> Unit

class UsersAdapter: androidx.recyclerview.widget.ListAdapter<User,UsersAdapter.ViewHolder>(UserDiffCallback) {


    lateinit var onEditUser:onClick
    lateinit var onDeleteUser:onClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
            ViewHolder(UsersActivityItemBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


    inner class ViewHolder(private val binding: UsersActivityItemBinding):RecyclerView.ViewHolder(binding.root){

        init {
            binding.usersItemBtnEdit.setOnClickListener {
                onEditUser(absoluteAdapterPosition)
            }
            binding.usersItemBtnDelete.setOnClickListener {
                onDeleteUser(absoluteAdapterPosition)
            }
        }


        fun bind(user: User) {
            binding.run{
                usersItemLblName.text = user.name
                usersItemLblMail.text = user.email
                usersItemLblPhoneNumber.text = user.phoneNumber
                usersItemImgUser.loadUrl(user.photoUrl)
            }
        }
    }


    object UserDiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem

    }
}