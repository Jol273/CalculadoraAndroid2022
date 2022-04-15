package pt.ulusofona.deisi.a2022

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object NavigationManager {

    private fun placeFragment(fm: FragmentManager, fragment: Fragment) {
        val transition = fm.beginTransaction()
        transition.replace(R.id.frame, fragment)
        transition.addToBackStack(null)
        transition.commit()
    }

    fun goToCalculatorFragment(fm: FragmentManager, operations: ArrayList<OperationUi>){
        placeFragment(fm,CalculatorFragment())
        //placeFragment(fm, CalculatorFragment.newInstance(operations))
    }

    fun goToOperationDetailFragment(fm: FragmentManager, operationUi: OperationUi){
        placeFragment(fm, OperationDetailFragment.newInstance(operationUi))
    }

    /*fun goToCalculatorFragment(fm: FragmentManager){
        placeFragment(fm, CalculatorFragment())
    }*/


    fun goToHistoryFragment(fm: FragmentManager, operations: ArrayList<OperationUi>){
        placeFragment(fm, HistoryFragment.newInstance(operations))
    }
}
