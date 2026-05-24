package Models

data class CharactersResponseDto(
    val info : InfoDto,
    val results : List<CharacterDto>){

}
