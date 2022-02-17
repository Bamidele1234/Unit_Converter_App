package com.example.Unit_Converter_App

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.Unit_Converter_App.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //Boolean for the switching
    private var state : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Hide the system bars
        hideSystemBars()

        setContentView(binding.root)

        // if the icon is clicked
        binding.iconInputQuery.setOnClickListener {
            state = !state
            when {
                state -> {
                    binding.serviceQuestion.text = getString(R.string.to)
                    binding.serviceQuestion2.text = getString(R.string.from)
                    binding.inputValue1.isEnabled = false
                    binding.inputValue2.isEnabled = true
                    reconvert()
                }
                else -> {
                    // All the methods to do the conversion are here
                    binding.serviceQuestion.text = getString(R.string.from)
                    binding.serviceQuestion2.text = getString(R.string.to)
                    binding.inputValue2.isEnabled = false
                    binding.inputValue1.isEnabled = true
                    convert()
                }
            }
        }

        //When the button is pressed
        binding.calculateFab.setOnClickListener{


            // Shrink the extended fab to just the icon
            binding.calculateFab.shrink()

            when {
                state -> {
                    binding.serviceQuestion.text = getString(R.string.to)
                    binding.serviceQuestion2.text = getString(R.string.from)
                    binding.inputValue1.isEnabled = false
                    binding.inputValue2.isEnabled = true
                    reconvert()
                }
                else -> {
                    // All the methods to do the conversion are here

                    binding.serviceQuestion.text = getString(R.string.from)
                    binding.serviceQuestion2.text = getString(R.string.to)
                    binding.inputValue2.isEnabled = false
                    binding.inputValue1.isEnabled = true
                    convert()
                }
            }
        }


    }
    // Convert a unit of measurement to another
    private fun convert() {
        val stringInTextField = binding.inputValueEdit1.text.toString()

        //Convert the text to a double or a null value if there's no input
        val value = stringInTextField.toDoubleOrNull()

        // Store the unit the user wants to convert from
        val unit1 = binding.fromButtons.checkedRadioButtonId

        // Store the unit the user wants to convert to
        val unit2 = binding.toButtons.checkedRadioButtonId

        when (value) {
            null -> {
                showSnackbar()
                return
            }
            else -> {
                // Compare the values against meter and store it in "from"
                val from = when (unit1) {
                    R.id.millimeter -> 1
                    R.id.centimeter -> 2
                    R.id.meter -> 3
                    R.id.kilometer -> 4
                    else -> 0
                }

                // Compare the values against meter and store it in "to
                val to = when (unit2)  {
                    R.id.inch -> toInches(value, from)
                    R.id.foot -> toFeet(value, from)
                    R.id.yard -> toYard(value, from)
                    R.id.mile -> toMile(value, from)
                    else -> 0
                }

                // Format/roundup the number
                val solution = ("%.3f".format(to))

                //Set the text value to the solution
                binding.inputValueEdit2.setText(solution)

            }
        }
    }

    private fun reconvert(){
        val stringInTextField = binding.inputValueEdit2.text.toString()

        //Convert the text to a double or a null value if there's no input
        val value = stringInTextField.toDoubleOrNull()

        // Store the unit the user wants to convert from
        val unit1 = binding.toButtons.checkedRadioButtonId

        // Store the unit the user wants to convert to
        val unit2 = binding.fromButtons.checkedRadioButtonId

        when (value) {
            null -> {
                showSnackbar()
                return
            }
            else -> {
                // Compare the values against meter and store it in "from"
                val from = when (unit1) {
                    R.id.inch -> 1
                    R.id.foot -> 2
                    R.id.yard -> 3
                    R.id.mile -> 4
                    else -> 0
                }

                // Compare the values against meter and store it in "to
                val to = when (unit2)  {
                    R.id.millimeter -> toMillimeter(value, from)
                    R.id.centimeter -> toCentimeter(value, from)
                    R.id.meter -> toMeter(value, from)
                    R.id.kilometer -> toKiloMeter(value, from)
                    else -> 0
                }

                // Format/roundup the number
                val solution = ("%.3f".format(to))

                //Set the text value to the solution
                binding.inputValueEdit1.setText(solution)

            }
        }



    }

    // Function to calculate to inches
    private fun toInches(amount: Double, convert: Int): Double{
        val ans = when(convert){
            1 -> amount * 0.0393701
            2 -> amount * 0.393701
            3 -> amount * 39.3701
            4 -> amount * 393701
            else -> amount
        }
        return ans
    }

    //Function to calculate to foot
    private fun toFeet(amount: Double, convert: Int): Double{
        val ans = when(convert){
            1 -> amount * 0.00328084
            2 -> amount * 0.0328084
            3 -> amount * 3.28084
            4 -> amount * 3280.84
            else -> amount
        }
        return ans
    }

    // Function to calculate to yard
    private fun toYard(amount: Double, convert: Int): Double{
        val ans = when(convert){
            1 -> amount * 0.00109361
            2 -> amount * 0.0109361
            3 -> amount * 1.09361
            4 -> amount * 1093.61
            else -> amount
        }
        return ans
    }

    // Function to convert to Mile
    private fun toMile(amount: Double, convert: Int): Double{
        val ans = when(convert){
            1 -> amount * 0.00000062137
            2 -> amount * 0.0000062137
            3 -> amount * 0.000621371
            4 -> amount * 0.621371
            else -> amount
        }
        return ans
    }

    // Function to convert to Millimeter
    private fun toMillimeter(amount: Double, convert: Int): Double{
        val ans = when(convert){
            1 -> amount * 25.4
            2 -> amount * 304.8
            3 -> amount * 914.4
            4 -> amount * 1609340
            else -> amount
        }
        return ans
    }

    // Function to convert to centimeter
    private fun toCentimeter(amount: Double, convert: Int): Double{
        val ans = when(convert){
            1 -> amount * 2.54
            2 -> amount * 30.48
            3 -> amount * 91.44
            4 -> amount * 160934
            else -> amount
        }
        return ans
    }

    // Function to convert to meter
    private fun toMeter(amount: Double, convert: Int): Double{
        val ans = when(convert){
            1 -> amount * 0.0254
            2 -> amount * 0.3048
            3 -> amount * 0.9144
            4 -> amount * 1609.34
            else -> amount
        }
        return ans
    }

    // Function to convert to kilometer
    private fun toKiloMeter(amount: Double, convert: Int): Double{
        val ans = when(convert){
            1 -> amount * 0.0000254
            2 -> amount * 0.0003048
            3 -> amount * 0.0009144
            4 -> amount * 1.60934
            else -> amount
        }
        return ans
    }

    // Self explanatory function
    private fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    // Show a notification when the text box is empty
    private fun showSnackbar(): Boolean {
        val empty = getString(R.string.request)
        Snackbar.make(
            findViewById(R.id.constraintLayout),
            empty,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}





