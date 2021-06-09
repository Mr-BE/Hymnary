package dev.mrbe.hymnary.viewItems

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginRight
import androidx.databinding.DataBindingUtil
import dev.mrbe.hymnary.R
import dev.mrbe.hymnary.databinding.LayoutBubbleItemBinding
import org.koin.core.KoinApplication.Companion.init


class OnboardingBubbleItem @kotlin.jvm.JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private lateinit var binding: LayoutBubbleItemBinding

    init {
        initView(attrs)

    }

    private fun initView(attrs: AttributeSet?) {
        val inflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//        binding = DataBindingUtil.inflate(inflater, R.layout.layout_bubble_item, this, true)
        binding = LayoutBubbleItemBinding.inflate(LayoutInflater.from(context), this, true)

//        View.inflate(context, R.layout.layout_bubble_item, this)

        //create TypeArray with specific TextView styling
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.OnboardingBubbleItem)
        try {
            val textColor =
                typeArray.getColor(R.styleable.OnboardingBubbleItem_bl_text_color, Color.BLACK)
            val gravity = typeArray.getInt(R.styleable.OnboardingBubbleItem_bl_alignment, 0)
            val marginTop =
                typeArray.getDimensionPixelSize(R.styleable.OnboardingBubbleItem_bl_margin_top, 16)
            val marginStart =
                typeArray.getDimensionPixelSize(
                    R.styleable.OnboardingBubbleItem_bl_margin_start,
                    16
                )
            val marginEnd =
                typeArray.getDimensionPixelSize(R.styleable.OnboardingBubbleItem_bl_margin_end, 16)
            val marginBottom =
                typeArray.getDimensionPixelSize(
                    R.styleable.OnboardingBubbleItem_bl_margin_bottom,
                    16
                )
            val text = typeArray.getString(R.styleable.OnboardingBubbleItem_bl_text)

            //set margins
//            val bubbleItemTitle =findViewById<TextView>(R.id.bubbleItemTitle)
            (binding.bubbleItemTitle.layoutParams as ConstraintLayout.LayoutParams)
                .setMargins(marginStart, marginTop, marginEnd, marginBottom)

            binding.bubbleItemTitle.text = text
            binding.bubbleItemTitle.setTextColor(textColor)
            binding.bubbleItemTitle.setTextGravity(gravity)
        } finally {
            typeArray.recycle()
        }

    }

//    override fun onFinishInflate() {
//        super.onFinishInflate()
//        binding = LayoutBubbleItemBinding.bind(this)
//    }

    //Extension function for text gravity value
    private fun TextView.setTextGravity(value: Int) {
        gravity = when (value) {
            0 -> Gravity.START
            1 -> Gravity.CENTER
            else -> Gravity.END
        }
    }

    //ImageView
    private fun ImageView.setGravity(value: Int) {
        val params = layoutParams as ConstraintLayout.LayoutParams
        when (value) {
            0 -> {
                params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                params.setMargins(320, 20, 0, 0)
            }
            1 -> {
                params.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID
                params.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
            }
            else -> {
                params.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID
            }
        }
        requestLayout()
    }

    //Show bubble details when in focus
    private fun showDetails() = binding.bubbleItemContainer
        .animate()
        .alpha(1f).setDuration(500)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                binding.bubbleItemTitle.visibility = View.VISIBLE
                binding.bubbleItemDescription.visibility = View.VISIBLE
                binding.bubbleItemIcons.visibility = View.VISIBLE
            }
        })
        .start()


    //hide details on transition
    private fun hideDetails() = binding.bubbleItemContainer
        .animate()
        .alpha(0f).setDuration(500)
        .setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                binding.bubbleItemTitle.visibility = View.INVISIBLE
                binding.bubbleItemDescription.visibility = View.INVISIBLE
                binding.bubbleItemIcons.visibility = View.INVISIBLE
            }
        })
        .start()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (w >= 200.toPixels()) {
            showDetails()
        } else {
            hideDetails()
        }
    }

    private fun Int.toPixels(): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(), Resources.getSystem().displayMetrics
        ).toInt()
    }

}






