package UI.Characters

import Data.CharactersRepository
import Data.RetrofitInstance
import Data.RickAndMortyApi
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.semantics.SemanticsActions.OnClick
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.myapplication.ui.theme.MyApplicationTheme
import org.w3c.dom.Text
import retrofit2.Retrofit

class Fragment : Fragment() {

    private lateinit var viewModel: ViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val api = RetrofitInstance.rickAndMortyApi
        val repository = CharactersRepository(api)
        val factory = CharactersViewModelFactory(repository)

        viewModel = ViewModelProvider(
            this,
            factory
        )[ViewModel::class.java]

        return ComposeView(requireContext()).apply {
            setContent {
                ChartersScreen(viewModel)
            }
        }
    }

    @Composable
    fun ChartersScreen(viewModel: ViewModel){

        val characters = viewModel.characters.collectAsLazyPagingItems()

        when (characters.loadState.refresh) {
            is LoadState.Loading -> { Text (text = "Загрузка...")}
            is LoadState.Error -> {Text (text = "Ошибка загрузки.")}
            else -> LazyColumn {
                items(characters.itemCount) { index ->
                    val character = characters[index]
                    if (character!=null) {

                        var expanded by remember { mutableStateOf(false) }

                        Card (
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(5.dp)
                                .clickable{
                                    expanded = !expanded
                                },
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row {
                                AsyncImage(
                                    modifier = Modifier
                                        .size(100.dp),
                                    model = character.image,
                                    contentDescription = null,
                                    contentScale = ContentScale.FillWidth
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Text(
                                    modifier = Modifier
                                        .padding(5.dp),
                                    text = character.name,
                                    fontSize = 18.sp
                                )

                                Text(
                                    modifier = Modifier
                                        .padding(8.dp),
                                    text = character.status,
                                    fontSize = 10.sp
                                )
                            }

                            if (expanded) {
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Text(text = "ID: ${character.id}")
                                    Text(text = "Status: ${character.status}")
                                    Text(text = "Name: ${character.name}")
                                    Text(text = "Species: ${character.species}")
                                    Text(text = "Gender: ${character.gender}")
                                }
                            }
                        }

                    }
                }

                item {

                    when (val appendState = characters.loadState.append) {

                        is LoadState.Loading -> {
                            Text(text = "Загрузка ещё...")
                        }

                        is LoadState.Error -> {
                            Text(text = appendState.error.toString())
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}

