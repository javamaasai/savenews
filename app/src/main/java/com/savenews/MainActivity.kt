package com.savenews

import android.animation.LayoutTransition
import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.savenews.ui.RetroFragment
import kotlinx.android.synthetic.main.network_layout.*

class MainActivity: AppCompatActivity() {
    lateinit var retroFragment: RetroFragment
    private var searchView : androidx.appcompat.widget.SearchView? = null
    private var searchItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_layout)
        replaceFragment()

        /*
        Timer("Reset", false).schedule(7000) {
            println("==> try and reset ==")
            runOnUiThread {
                retroFragment.listAdapter?.setAdapterList(emptyList())
                progress_bar.visibility = View.VISIBLE
            }
        }
        */
    }
    //check internet

    fun replaceFragment(){
        retroFragment = RetroFragment(progress_bar)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container_retro_room, retroFragment)
            .commit()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        searchItem = menu.findItem(R.id.appSearchBar)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView
        searchView!!.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        val txtSearch = searchView!!.findViewById<View>(androidx.appcompat.R.id.search_src_text) as EditText
        txtSearch.hint = getString(R.string.search_hint)
        txtSearch.setHintTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
        txtSearch.setTextColor(Color.BLACK)

        // change search close button image
        val closeButton = searchView!!.findViewById<ImageView>(R.id.search_close_btn)
        closeButton.setImageResource(R.drawable.ic_close)

        closeButton.setOnClickListener { v ->
            txtSearch.setText("")
            //reset list
        }

        // Make animation transition
        val searchBar = searchView!!.findViewById(R.id.search_bar) as LinearLayout
        searchBar.layoutTransition = LayoutTransition()

        // do something when collapsed and expanded
        searchItem?.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                //do something when collapsed
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {
                // do something when expanded
                invalidateOptionsMenu()
                if (supportActionBar != null) {
                    supportActionBar!!.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(applicationContext, R.color.colorPrimaryDark)))
                }
                return true
            }
        })

        searchView = searchItem?.actionView as androidx.appcompat.widget.SearchView
        searchView!!.isIconified = true
        searchView!!.setOnCloseListener { true }

        val searchPlate =
            searchView!!.findViewById(androidx.appcompat.R.id.search_src_text) as EditText
        searchPlate.hint = getString(R.string.search_hint)
        val searchPlateView: View = searchView!!.findViewById(androidx.appcompat.R.id.search_plate)
        searchPlateView.setBackgroundColor(
            ContextCompat.getColor(
                this,
                android.R.color.white
            )
        )

        searchView!!.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                retroFragment.listAdapter?.queryForText(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                retroFragment.listAdapter?.queryForText(newText)
                return false
            }
        })
        searchView!!.setOnCloseListener { false }

        return super.onCreateOptionsMenu(menu)
    }
}