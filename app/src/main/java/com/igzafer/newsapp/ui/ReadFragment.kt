package com.igzafer.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.igzafer.newsapp.R
import kotlinx.android.synthetic.main.fragment_read.*
import android.webkit.WebView
import android.webkit.WebViewClient


class ReadFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read, container, false)
    }

    var url: String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            url = ReadFragmentArgs.fromBundle(it).readUrl
        }
        webView.webViewClient = WebViewClient()

        webView.loadUrl(url)

    }
}