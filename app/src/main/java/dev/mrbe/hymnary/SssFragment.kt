package dev.mrbe.hymnary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.Navigator
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.mrbe.hymnary.databinding.FragmentRchBinding
import dev.mrbe.hymnary.databinding.FragmentSssBinding
import kotlinx.coroutines.flow.asStateFlow

private lateinit var binding: FragmentSssBinding
/**
 * A simple [Fragment] subclass.
 * Use the [SssFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SssFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSssBinding.inflate(inflater, container, false)

//        binding.viewCompose.apply {
//            //dispose the composition when the view's lifecycle owner is destroyed
//            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
//            setContent {
//                MaterialTheme {
//                    BooksList()
//                }
//            }
//        }
//        @Composable
//        fun NavHostExample(
//        navController: NavHostController = rememberNavController(),
//        navigator: NavigationActions.Navigator = get (),
//        ){
//            val lifecylceOwner = LocalLifecycleOwner.current
//            val navigatorState by navigator.navActions.asLifecycleAwareState(
//                lifecylceOwner = lifecylceOwner,
//                initialState = null
//            )
//
//    LaunchedEffect(navigatorState){
//        navigatorState?.let{
//            it.parcelableArguments.forEach{ arg ->
//                navController.currentBackStackEntry?.arguments.putParcelable(arg.key, arg.value)
//            }
//            navController.navigate(it.destination, it.navOptions)
//        }
//    }
//    NavHost(navController = navController, startDestination = NavigationDestinations.sssScreen)
//
//
//        }

        return binding.root
    }

}

@Composable
fun BooksList(
    hymnsViewModel: HymnsViewModel = viewModel(
        factory = HymnViewModelFactory(HymnRepo())
    )
) {
    when (val hymnsList = hymnsViewModel.hymnsStateFlow.asStateFlow().collectAsState().value) {

        is OnError -> {
            Text(text = "Please try again later")
        }

        is OnSuccess -> {
            val listOfBooks = hymnsList.querySnapshot?.toObjects(Book::class.java)
            listOfBooks?.let {
                Column {
                    Text(
                        text = "Booker",
                        style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.ExtraBold),
                        modifier = Modifier.padding(16.dp)
                    )

                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        items(listOfBooks) {

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                shape = RoundedCornerShape(16.dp)
                            ) {
                                BookDetails(it)
                            }
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BookDetails(book: Book?) {
    var showBookDescription by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.clickable {
        showBookDescription = showBookDescription.not()
    }) {
        Row(modifier = Modifier.padding(12.dp)) {
            Column {
                book?.let {
                    Text(
                        text = it.title, style = TextStyle(
                            fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Blue
                        )
                    )
                }
            }

            Column {
                book?.let {
                    Text(
                        text = it.author, style = TextStyle(
                            fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Blue
                        )
                    )
                }
            }

        }

    }

}


