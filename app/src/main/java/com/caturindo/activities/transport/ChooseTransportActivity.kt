package com.caturindo.activities.transport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.caturindo.R
import com.caturindo.fragments.transport.TransportFragment
import com.caturindo.preference.Prefuser

class ChooseTransportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_transport)
        Prefuser().setvalidateOnclickRoom("1")
        loadFragment(TransportFragment())
    }

    private fun loadFragment(fragment: Fragment?): Boolean {
        //switching fragment
        if (fragment != null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            return true
        }
        return false
    }
}