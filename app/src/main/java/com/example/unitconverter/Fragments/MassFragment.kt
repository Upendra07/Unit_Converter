package com.example.unitconverter.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.unitconverter.R

class MassFragment : Fragment() {

    lateinit var valueTxtView : EditText
    lateinit var convertFromView : AutoCompleteTextView
    lateinit var convertToView : AutoCompleteTextView
    lateinit var resultTxtView : TextView
    lateinit var calculateBtnView: Button
    lateinit var labelResultView: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Convert Mass"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mass, container, false)

        valueTxtView = view.findViewById(R.id.valueTxt)
        convertFromView = view.findViewById(R.id.convertFrom)
        convertToView = view.findViewById(R.id.convertTo)
        resultTxtView = view.findViewById(R.id.resultTxt)
        calculateBtnView = view.findViewById(R.id.calculateBtn)
        labelResultView = view.findViewById(R.id.labelResult)

        val timeUnits = resources.getStringArray(R.array.mass_units)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_view, timeUnits)
        convertFromView.setAdapter(arrayAdapter)
        convertToView.setAdapter(arrayAdapter)

        calculateBtnView.setOnClickListener {
            calculateResult(
                convertFromView.text.toString(),
                convertToView.text.toString(),
                valueTxtView.text.toString())
        }

        valueTxtView.setOnFocusChangeListener { v, hasFocus ->
            Utils.hideSoftKeyBoard(requireContext(), requireView() )
        }

        setResultVisiblity(false)
        return view
    }

    override fun onResume() {
        super.onResume()
        val timeUnits = resources.getStringArray(R.array.mass_units)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_view, timeUnits)
        convertFromView.setAdapter(arrayAdapter)
        convertToView.setAdapter(arrayAdapter)
    }


    private fun calculateResult(from: String, to: String, value: String){


        if(validateInputs(from,to,value)) return

        val givenValue = value.toFloat()
        var resultValue = 0f

        when(from+to){
            resources.getStringArray(R.array.mass_units)[0]+resources.getStringArray(R.array.mass_units)[1]->{
                resultValue = givenValue / 1000f
            }
            resources.getStringArray(R.array.mass_units)[0]+resources.getStringArray(R.array.mass_units)[2]->{
                resultValue = givenValue / (2.71f+6)
            }
            resources.getStringArray(R.array.mass_units)[0]+resources.getStringArray(R.array.mass_units)[2]->{
                resultValue = givenValue / 453592f
            }

            resources.getStringArray(R.array.mass_units)[1]+resources.getStringArray(R.array.mass_units)[0]->{
                resultValue = givenValue * 1000f
            }
            resources.getStringArray(R.array.mass_units)[1]+resources.getStringArray(R.array.mass_units)[2]->{
                resultValue = givenValue / 1000f
            }
            resources.getStringArray(R.array.mass_units)[1]+resources.getStringArray(R.array.mass_units)[3]->{
                resultValue = givenValue / 454f
            }

            resources.getStringArray(R.array.mass_units)[2]+resources.getStringArray(R.array.mass_units)[0]->{
                resultValue = givenValue * (2.71f+6)
            }
            resources.getStringArray(R.array.mass_units)[2]+resources.getStringArray(R.array.mass_units)[1]->{
                resultValue = givenValue * 1000f
            }
            resources.getStringArray(R.array.mass_units)[2]+resources.getStringArray(R.array.mass_units)[3]->{
                resultValue = givenValue * 2.205f
            }

            resources.getStringArray(R.array.mass_units)[3]+resources.getStringArray(R.array.mass_units)[0]->{
                resultValue = givenValue * 453592f
            }
            resources.getStringArray(R.array.mass_units)[3]+resources.getStringArray(R.array.mass_units)[1]->{
                resultValue = givenValue * 454f
            }
            resources.getStringArray(R.array.mass_units)[3]+resources.getStringArray(R.array.mass_units)[2]->{
                resultValue = givenValue / 2.205f
            }
        }

        resultTxtView.text = resultValue.toString()
        setResultVisiblity(true)

    }

    private fun validateInputs(from: String, to: String, value: String) : Boolean {

        var isSame = false
        var isEmpty = false

        if(from.equals(to,false)) {

            isSame = true
            Toast.makeText(requireContext(),"Please choose different units to convert!", Toast.LENGTH_SHORT).show()
            setResultVisiblity(false)

        }

        if(valueTxtView.text.isEmpty()){

            isEmpty = true
            // Toast.makeText(requireContext(),"Please enter value to convert",Toast.LENGTH_SHORT).show()
            valueTxtView.error = "Enter value to convert!!"
            setResultVisiblity(false)

        }

        return isSame || isEmpty
    }

    private fun setResultVisiblity(visible: Boolean) {
        if (!visible){
            resultTxtView.visibility = View.INVISIBLE
            labelResultView.visibility = View.INVISIBLE
        }else{
            resultTxtView.visibility = View.VISIBLE
            labelResultView.visibility = View.VISIBLE
        }
    }

    object Utils {

        fun hideSoftKeyBoard(context: Context, view: View) {
            try {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
            } catch (e: Exception) {
                // TODO: handle exception
                e.printStackTrace()
            }

        }
    }
}