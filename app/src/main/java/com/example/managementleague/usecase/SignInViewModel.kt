package com.example.managementleague.usecase

import android.content.ContentValues.TAG
import android.content.Context
import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.managementleague.model.entity.User
import com.example.managementleague.utils.AuthManager
import com.example.managementleague.state.SigninState
import com.example.managementleague.utils.AuthRes
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class SignInViewModel : ViewModel() {
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    private var state = MutableLiveData<SigninState>()

    fun validate(context: Context) {

        when {

            TextUtils.isEmpty(email.value) -> state.value = SigninState.EmailEmptyError
            TextUtils.isEmpty(password.value) -> state.value = SigninState.PasswordEmptyError
            else -> {
                //Se crea una corrutina qie suspenda el hilo principal hasta que el
                // bloque withContext del repositotio termina de ejecutarse
                viewModelScope.launch {
                    state.value = SigninState.Loading(true)
                    //Vamos a ejecutar el login del repositorio -> que pregunta a la capa de la infraestructura
                    val result = AuthManager(context).signInWithEmailAndPassword(
                        email.value!!,
                        password.value!!
                    )
                    //is cuando sea un data class
                    state.value = SigninState.Loading(false)
                    when (result) {
                        is AuthRes.Success<*> -> {
                            Log.i(TAG, "Login correcto del usuario")
                            state.value = SigninState.Success(result)
                        }

                        is AuthRes.Error -> {
                            Log.i(TAG, "Informacion del dato ${result.errorMessage}")
                            state.value = SigninState.Error(result.errorMessage)
                        }

                        else -> {}
                    }
                }

            }
        }

    }

    fun getState(): LiveData<SigninState> {
        return state;
    }
}