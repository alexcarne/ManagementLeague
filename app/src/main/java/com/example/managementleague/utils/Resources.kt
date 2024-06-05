/**
 * Esta ckase sellada representa la respuesta de un servicio, APP REST, firebase donde se declara la clase Error que almacenara los
 * errores de la infraestructura y el caso de exicto de una coleccion de dato de tipo genético
 */
sealed class Resources {
   // data class Success<T, E>(var data: T, var settings: E):Resources()
   data class Success<T>(var data: T): Resources()
    data class Error(var exception: Exception) : Resources()
}