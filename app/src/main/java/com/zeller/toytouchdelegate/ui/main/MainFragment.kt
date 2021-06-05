package com.zeller.toytouchdelegate.ui.main

import android.content.Context
import android.graphics.Rect
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DimenRes
import com.zeller.toytouchdelegate.R
import com.zeller.toytouchdelegate.databinding.MainFragmentBinding
import timber.log.Timber

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false).also {
            binding = MainFragmentBinding.bind(it)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.apply {
            post {
                increaseClickableArea(
                    left = R.dimen.margin_xlarge,
                    right = R.dimen.margin_xlarge
                )
            }
            setOnClickListener {
                action()
            }
        }
    }

    private fun View.increaseClickableArea(
        @DimenRes top: Int? = null,
        @DimenRes left: Int? = null,
        @DimenRes bottom: Int? = null,
        @DimenRes right: Int? = null
    ) {
        val rect = Rect()
        getHitRect(rect)
        Timber.d("Before: $rect")
        rect.top -= top?.let { dimen(it) } ?: 0
        rect.left -= left?.let { dimen(it) } ?: 0
        rect.bottom += bottom?.let { dimen(it) } ?: 0
        rect.right += right?.let { dimen(it) } ?: 0
        Timber.d("After: $rect")
        (parent as? View)?.touchDelegate = TouchDelegate(rect, this)
    }

    private fun action() {
        Toast.makeText(context, "CLICK", Toast.LENGTH_SHORT).show()
    }

}

fun Fragment.dimen(@DimenRes resource: Int): Int = activity?.dimen(resource) ?: 0
fun Context.dimen(@DimenRes resource: Int): Int = resources.getDimensionPixelSize(resource)

