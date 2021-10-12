package dev.mrbe.hymnary.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("hymnContent")

fun bindHymnContent(textView: TextView, inputContent: String) {
    val modifiedString = inputContent.replace(Regex("""\d\."""), "\n $0")
    textView.text = modifiedString

}
