package com.example.arcmenu

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class DashboardActivity : AppCompatActivity() {

    var triggerButton : ImageButton? = null
    var home: ImageButton?=null
    var product: ImageButton?=null
    var scanner: ImageButton?=null
    var order: ImageButton?=null
    var account: ImageButton?=null
    var socialMedia: ImageButton?=null

    lateinit var circularMenu: RelativeLayout
    lateinit var circularSubMenu: RelativeLayout
    private var isMenuOpen = false
    private var isSubMenuOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        triggerButton = findViewById(R.id.triggerButton)
        circularMenu = findViewById(R.id.circularMenu)

        home = findViewById(R.id.home)
        product = findViewById(R.id.product)
        scanner = findViewById(R.id.scanner)
        order = findViewById(R.id.order)
        account = findViewById(R.id.user)
        socialMedia = findViewById(R.id.socialmedia)

        triggerButton!!.setOnClickListener{
            if(isMenuOpen){
                closeMenu()
            }else{
                openMenu()
            }
        }
        home!!.setOnClickListener{
            Toast.makeText(this,"Home",Toast.LENGTH_SHORT).show()
        }
        product!!.setOnClickListener{
            Toast.makeText(this,"Product",Toast.LENGTH_SHORT).show()
        }
        scanner!!.setOnClickListener{
            Toast.makeText(this,"Scanner",Toast.LENGTH_SHORT).show()
        }
        order!!.setOnClickListener{
            Toast.makeText(this,"Order",Toast.LENGTH_SHORT).show()
        }
        account!!.setOnClickListener{
            Toast.makeText(this,"Account",Toast.LENGTH_SHORT).show()
        }
        socialMedia!!.setOnClickListener{
            Toast.makeText(this,"Social Media",Toast.LENGTH_SHORT).show()
        }

    }


    private fun closeMenu() {

            circularMenu.visibility = View.GONE
            triggerButton!!.setBackgroundResource(R.drawable.ic_menu)
            for (i in 0 until circularMenu.childCount) {
                val menuItem = circularMenu.getChildAt(i)
                if (menuItem is ImageButton) {
                    val animatorX = ObjectAnimator.ofFloat(menuItem, "translationX", 0f)
                    val animatorY = ObjectAnimator.ofFloat(menuItem, "translationY", 0f)

                    val animatorSet = AnimatorSet()
                    animatorSet.playTogether(animatorX, animatorY)
                    animatorSet.duration = 500 // Set animation duration
                    animatorSet.start()
                    menuItem.visibility = View.INVISIBLE
                }
            }

            isMenuOpen = false

    }

    private fun openMenu() {
        if(!isSubMenuOpen && !isMenuOpen){
            circularMenu.visibility = View.VISIBLE
            triggerButton!!.setBackgroundResource(R.drawable.ic_close)
            val centerX = circularMenu.width / 2
            val centerY = -(180)
            val startAngle = 180f // Start angle in degrees
            val endAngle = 360f // End angle in degrees
            val displayMetrics = DisplayMetrics()
            val windowManager = circularMenu.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            val screenWidth = displayMetrics.widthPixels
            val radius = screenWidth / 3    // Radius of the circular arrangement

            for (i in 0 until circularMenu.childCount) {
                val menuItem = circularMenu.getChildAt(i)
                if (menuItem is ImageButton) {
                    val angle = startAngle + (i * (endAngle - startAngle) / (circularMenu.childCount - 1))
                    val x = radius * Math.cos(Math.toRadians(angle.toDouble())).toFloat()
                    val y = centerY + radius * Math.sin(Math.toRadians(angle.toDouble())).toFloat()

                    val animatorX = ObjectAnimator.ofFloat(menuItem, "translationX", x)
                    val animatorY = ObjectAnimator.ofFloat(menuItem, "translationY", y)

                    val animatorSet = AnimatorSet()
                    animatorSet.playTogether(animatorX, animatorY)
                    animatorSet.duration = 500 // Set animation duration
                    animatorSet.startDelay = (i * 100).toLong() // Delay for a staggered effect
                    animatorSet.start()
                    menuItem.visibility = View.VISIBLE
                }
            }

            isMenuOpen = true
        }
    }

}