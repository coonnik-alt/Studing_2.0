package Data
import Models.CharacterDto
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.Pager
import androidx.paging.PagingConfig

class CharactersPagingSource(
    val api : RickAndMortyApi,
    ) : PagingSource <Int, CharacterDto>(){

    override fun getRefreshKey(state: PagingState<Int, CharacterDto>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val page = state.closestPageToPosition(anchorPosition)
            page?.prevKey?.plus(1) ?:
            page?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDto> {
        return try {

            val page = params.key ?: 1
            val responce = api.getCharacters(page)

            LoadResult.Page(
                data = responce.results,
                prevKey = if (page == 1) null else page -1,
                nextKey = if(page < responce.info.pages) page + 1 else null
            )
        } catch (e : Exception) {
            Log.d("MY_TAG", e.toString())

            LoadResult.Error(e)
        }
    }

}