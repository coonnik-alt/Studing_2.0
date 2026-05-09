import android.os.Bundle
import android.provider.Settings.Global.getString
import android.util.Log
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recycleview_retrofit.Movie
import com.example.recycleview_retrofit.MovieAdapter
import com.example.recycleview_retrofit.R
import com.example.recycleview_retrofit.RetrofitInstance
import com.example.recycleview_retrofit.databinding.FragmentActivityBinding
import kotlinx.coroutines.launch
import retrofit2.http.Query

class ActivityFragment : Fragment(R.layout.fragment_activity) {

    private var _binding: FragmentActivityBinding? = null
    private val binding get() = _binding!!

    private val myAdapter = MovieAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("ZZZ", "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentActivityBinding.bind(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = myAdapter

        Log.e("ZZZ", "before loadMovies()")
        loadMovies()
        Log.e("ZZZ", "after loadMovies()")
    }

    private fun loadMovies() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                Log.e("ZZZ", "before request")

                val resp = RetrofitInstance.api.getMovies()

                val filtered = resp.docs.filter { m ->
                    val hasTitle = !m.name.isNullOrBlank() || !m.enName.isNullOrBlank() || !m.alternativeName.isNullOrBlank()
                    val hasDesc  = !m.description.isNullOrBlank() || !m.shortDescription.isNullOrBlank()
                    val hasPoster = !m.poster?.url.isNullOrBlank()
                    hasTitle && hasDesc && hasPoster
                }

                myAdapter.submitList(filtered)


            } catch (e: Throwable) {
                Log.e("ZZZ", "request error", e)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

