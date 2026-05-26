package com.example.test_application.ui.theme


import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test_application.R

private lateinit var viewModel: ViewModel
class BlankFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        viewModel = ViewModelProvider(
            this,
            factory
        )[ViewModel::class.java]

        return ComposeView(requireContext()).apply {
            setContent {

            }
        }
    }
}


@Composable
fun logIn(){

    Column() {

        Text(
            text = "Вход",
                modifier = Modifier.padding(
                    top = 140.dp,
                    start = 16.dp
                )
            )
    }


}
