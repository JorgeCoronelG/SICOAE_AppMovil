package com.uteq.sicoae.Fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*

import com.uteq.sicoae.R
import com.uteq.sicoae.Activities.ChangePasswordActivity

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        setHasOptionsMenu(true)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = resources.getString(R.string.my_profile)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_change_password, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.item_change_password -> {
                val intent = Intent(context, ChangePasswordActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
