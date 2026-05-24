package Data

import Models.CharacterDto
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class CharactersRepository (
    private val api: RickAndMortyApi
) {

    fun getCharacters () : Flow<PagingData<CharacterDto>>{
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 20,
                prefetchDistance = 1,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                CharactersPagingSource(api)
            }
        ).flow
    }



}