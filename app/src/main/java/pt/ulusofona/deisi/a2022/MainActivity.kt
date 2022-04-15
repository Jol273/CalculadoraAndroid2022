package pt.ulusofona.deisi.a2022

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.ActionBarDrawerToggle
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import pt.ulusofona.deisi.a2022.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var binding: ActivityMainBinding
    private val operations: ArrayList<OperationUi> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(TAG, "o método onCreate foi invocado")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //operations.add(OperationUi("1+1","2.0"))

        if(!screenRotated(savedInstanceState)){
            NavigationManager.goToCalculatorFragment(supportFragmentManager, operations)
            //NavigationManager.goToCalculatorFragment(supportFragmentManager)
        }
    }

    override fun onStart() {
        super.onStart()
        setSupportActionBar(binding.toolbar)
        setupDrawerMenu()
        operations.add(OperationUi("1+1","2.0"))
        operations.add(OperationUi("1*3","3.0"))
        NavigationManager.goToCalculatorFragment(supportFragmentManager, operations)
    }

    private fun screenRotated(savedInstanceState: Bundle?): Boolean {
        return savedInstanceState != null
    }

    private fun setupDrawerMenu(){
        val toggle = ActionBarDrawerToggle(this,
            binding.drawer, binding.toolbar,
            R.string.drawer_open,R.string.drawer_close
        )
        binding.navDrawer.setNavigationItemSelectedListener {
            onClickNavigationItem(it)
        }
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun onClickNavigationItem(item: MenuItem): Boolean{

        when(item.itemId){
            R.id.nav_calculator ->
                NavigationManager.goToCalculatorFragment(
                        supportFragmentManager, operations
                )
            R.id.nav_history ->
                NavigationManager.goToHistoryFragment(
                        supportFragmentManager, operations
                )
        }
        binding.drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        when {
            binding.drawer.isDrawerOpen(GravityCompat.START) -> {
                binding.drawer.closeDrawer(GravityCompat.START)
            }
            supportFragmentManager.backStackEntryCount == 1 -> {
                finish()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        Log.i(TAG,"o método onDestroy foi invocado")
        super.onDestroy()
    }

}