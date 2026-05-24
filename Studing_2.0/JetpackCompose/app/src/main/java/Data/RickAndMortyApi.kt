package Data

import Models.CharactersResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {


    @GET("character")
    suspend fun getCharacters (
        @Query("page") page : Int
    ): CharactersResponseDto

}