package dev.mrbe.hymnary

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.fragment.findNavController
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import dev.mrbe.hymnary.databinding.FragmentRchBinding
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber


class RchFragment : Fragment() {

    private lateinit var binding: FragmentRchBinding
    private lateinit var query: Query

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        query = FirebaseFirestore.getInstance().collection("hymns").document("rch")
            .collection("rchHymns")

    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRchBinding.inflate(inflater, container, false)

        binding.composeView.apply {
            //dispose the composition when the view's lifecycle owner is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = NavRoutes.Hymn.route
                    ) {
                        //home (rch screen)
                        composable(NavRoutes.Hymn.route) {
                            HymnList(navController = navController)
                        }

                        //details route
                        composable(
                            NavRoutes.Details.route
                        ) { backStack ->
                            //extract argument
                            val receivedHymn = backStack.arguments?.getParcelable<Hymn>("hymn")

                            HymnDetails(hymn = receivedHymn)
                        }
                    }
                }
            }
        }


        //Navigate to user profile
        val navView: NavigationView? = requireActivity().findViewById(R.id.navView)
        val header: View? = navView?.getHeaderView(0)
        header?.setOnClickListener {
            startActivity(Intent(context, UserActivity::class.java))
        }

        return binding.root
    }

    //home page holder
    @Composable
    fun HymnList(
        hymnsViewModel: HymnsViewModel = viewModel(
            factory = HymnViewModelFactory(HymnRepo())
        ), navController: NavController
    ) {

        when (val hymnList = hymnsViewModel
            .hymnsStateFlow.asStateFlow().collectAsState().value) {

            is OnError -> {
                Text(text = "Error: Please try again later")
            }

            is OnSuccess -> {
                val listOfHymns = hymnList.querySnapshot?.toObjects(Hymn::class.java)

                listOfHymns?.let {

                    //List of hymns
                    LazyColumn {
                        items(listOfHymns) { hymn ->

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                shape = RoundedCornerShape(4.dp)
                            ) {
//                                   HymnDetails(it)

                                ListOfHymns(hymn, navController)
//                                    NavHost(navController = navController, hymn = hymn )

                            }

                        }
                    }

                }
            }
        }
    }

    //populate page with hymn list from firestore
    @Composable
    private fun ListOfHymns(
        hymn: Hymn?,
        navController: NavController
    ) {

        Row(Modifier.clickable {

            //create a bundle with selected hymn as args
            val bundle = Bundle().apply {
                putParcelable("hymn", hymn)
            }
            //navigate to selected hymn details
            navController.navigate(NavRoutes.Details.route, bundle)

        }
        ) {
            Row(modifier = Modifier.padding(12.dp)) {
                Column {
                    hymn?.title?.let {
                        Text(
                            text = it, style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        )
                    }

                    hymn?.hymnNumber?.let {
                        Text(
                            text = it, style = TextStyle(
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }
        }
    }
}


//Ext. fun. for navigating with just route and bundle
private fun NavController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        val id = destination.id
        navigate(id, args, navOptions, navigatorExtras)
    } else {
        navigate(route, navOptions, navigatorExtras)
    }
}


class HymnViewModelFactory(private val hymnRepo: HymnRepo) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HymnsViewModel::class.java)) {
            return HymnsViewModel(hymnRepo) as T
        }

        throw IllegalStateException()
    }
}


