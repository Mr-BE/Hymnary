package dev.mrbe.hymnary

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment
import dev.mrbe.hymnary.databinding.FragmentOnBoardingBinding

class onBoardingFragment : DialogFragment() {
    private lateinit var binding: FragmentOnBoardingBinding
    private val indicators = mutableListOf<View>()
    private var currentPosition = 0

//    private var previous = binding.buttonPrevious
//    private var next = binding.buttonNext
//    private var navContainer = binding.navContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AppCompatDialog(context, R.style.DialogFullScreen)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setIndicators()
        updateIndicators()
        attachNextButtonListener()
        attachPreviousButtonListener()
    }

    //when user clicks next
    private fun attachNextButtonListener() {

        binding.buttonNext.setOnClickListener {
            when (currentPosition) {
                0 -> binding.buttonNext.navigate(R.id.first_scene, R.id.second_scene)
                1 -> binding.buttonNext.navigate(R.id.second_scene, R.id.third_scene)
                else -> dismiss()
            }
        }
    }

    private fun attachPreviousButtonListener() {
        binding.buttonPrevious.setOnClickListener {
            when (currentPosition) {
                2 -> binding.buttonPrevious.navigate(R.id.third_scene, R.id.second_scene)
                else -> binding.buttonPrevious.navigate(R.id.second_scene, R.id.first_scene)
            }
        }
    }


    //ext. func. for button navigation onClick (increase value of position)
    private fun Button.navigate(startSceneId: Int, endSceneId: Int) {
        currentPosition = when (id) {
            R.id.button_next -> currentPosition.inc()
            else -> currentPosition.dec()
        }
        updateNavState()

        //change forward text and container background based on scene position
        if (endSceneId == R.id.third_scene) {
            binding.navContainer.setBackgroundColor(resources.getColor(R.color.color_onboarding_page3))
            binding.buttonNext.setText(R.string.button_text_complete)

        } else {
            binding.buttonNext.setText(R.string.button_text_next)
            binding.navContainer.setBackgroundColor(resources.getColor(R.color.onboarding_background))
        }
        //set transition from included Motion layout
        binding.includedLayout.motionLayout.setTransition(startSceneId, endSceneId)
        binding.includedLayout.motionLayout.transitionToEnd()
        updateIndicators()

    }

    //show user progress on screen with dots
    private fun updateIndicators() {
        indicators.forEachIndexed { index, view ->
            val background = when (index) {
                currentPosition -> R.drawable.selected_dot
                else -> R.drawable.un_selected_dot
            }
//            view.setBackgroundResource(background)
            view.setBackgroundDrawable(context?.getDrawable(background))
        }
    }

    private fun setIndicators() {
        val dotRadius: Int = convertDpToPixel(12f, context)
        val margin: Int = convertDpToPixel(4f, context)
        indicators.clear()
        binding.indicatorsContainer.removeAllViews()

        //build progress dot views
        for (i in 0 until 3) {
            val view = View(context)
            view.id = View.generateViewId()
            val layoutParams = FrameLayout.LayoutParams(dotRadius * 2, dotRadius * 2)
            layoutParams.setMargins(margin, margin, margin, margin)
            view.layoutParams = layoutParams
            indicators.add(view)
            binding.indicatorsContainer.addView(view)
        }
    }

    private fun convertDpToPixel(dp: Float, context: Context?): Int {
        if (context != null) {
            return (dp * (context.resources.displayMetrics.densityDpi.toFloat()
                    / DisplayMetrics.DENSITY_DEFAULT)).toInt()
        }
        return 0
    }


    private fun updateNavState() {
        when {
            currentPosition > 0 -> binding.buttonPrevious.visibility = View.VISIBLE
            else -> binding.buttonPrevious.visibility = View.INVISIBLE
        }
    }
}

