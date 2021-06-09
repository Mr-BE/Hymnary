//package dev.mrbe.hymnary
//
//import android.content.Context
//import android.graphics.Color
//import android.util.AttributeSet
//import android.view.View
//import android.widget.FrameLayout
//
//class OnboardingBubbleItem
//@kotlin.jvm.JvmOverloads
//constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
//    FrameLayout(context, attrs, defStyle) {
//
//    init {
//        initView(attrs)
//    }
//
//    private fun initView(attrs: AttributeSet?) {
//        View.inflate(context, R.layout.layout_bubble_item, this)
//
//
//        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.OnboardingBubbleItem)
//
//        val textColor =
//            typedArray.getColor(R.styleable.OnboardingBubbleItem_bl_text_color, Color.BLACK)
//        val gravity = typedArray.getInt(R.styleable.OnboardingBubbleItem_bl_alignment, 0)
//        val marginTop =
//            typedArray.getDimensionPixelSize(R.styleable.OnboardingBubbleItem_bl_margin_top, 16)
//        val marginStart =
//            typedArray.getDimensionPixelSize(R.styleable.OnboardingBubbleItem_bl_margin_start, 16)
//        val marginEnd =
//            typedArray.getDimensionPixelSize(R.styleable.OnboardingBubbleItem_bl_margin_end, 16)
//        val marginBottom =
//            typedArray.getDimensionPixelSize(R.styleable.OnboardingBubbleItem_bl_margin_bottom, 16)
//
//        val text = typedArray.getString(R.styleable.OnboardingBubbleItem_bl_text)
//
//        (bubbleItemTitle.layoutParams as ConstraintLayout.LayoutParams)
//            .setMargins(marginStart, marginTop, marginEnd, marginBottom)
//        bubbleItemTitle.text = text
//        bubbleItemTitle.setTextColor(textColor)
//        bubbleItemTitle.setTextGravity(gravity)
//
//        (bubbleItemDescription.layoutParams as ConstraintLayout.LayoutParams)
//            .setMargins(marginStart, 16, marginEnd, marginBottom)
//
//        val description = ta.getString(R.styleable.OnboardingBubbleItem_bl_description)
//        bubbleItemDescription.text = description
//        bubbleItemDescription.setTextColor(textColor)
//        bubbleItemDescription.setTextGravity(gravity)
//        ta.recycle()
//    }
//}
