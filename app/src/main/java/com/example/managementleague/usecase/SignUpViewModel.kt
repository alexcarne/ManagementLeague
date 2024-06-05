package com.example.managementleague.usecase

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.managementleague.model.entity.User
import com.example.managementleague.model.repository.UserRepository
import com.example.managementleague.state.SignUpState
import com.example.managementleague.utils.AuthManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.regex.Pattern

class SignUpViewModel :ViewModel(){
    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var phone = MutableLiveData<String>()

    private  var state = MutableLiveData<SignUpState>()

    fun validate(context: Context){
        when{
            TextUtils.isEmpty(email.value) -> state.value = SignUpState.EmailEmptyError
            ValidarEmail(email.value) -> state.value = SignUpState.EmailFormatError
            TextUtils.isEmpty(password.value) -> state.value = SignUpState.PasswordEmptyError
            password.value!!.length<6 -> state.value = SignUpState.PasswordLengthError
            //ValidarPassword(password.value) -> state.value = SignUpState.PasswordFormatError
            TextUtils.isEmpty(phone.value.toString()) -> state.value = SignUpState.PhoneEmptyError
            phone.value.toString().length!=9 -> state.value = SignUpState.PhoneFormatError

            else->{
                try {
                    GlobalScope.launch {
                            AuthManager(context).createUserWithEmailAndPassword(email.value!!, password.value!!)
                    }
                    UserRepository.insertUser(User(UserRepository.currentid()+1, name.value!!,
                        email.value!!, password.value!!, phone.value!!
                    ))
                    state.value = SignUpState.Success
                }catch (e: Exception){
                    state.value = SignUpState.Error
                }


            }

        }

    }
    fun getState(): LiveData<SignUpState> {
        return state;
    }
    private fun ValidarEmail(value: String?): Boolean {
        val pattern = Pattern.compile("^\\S+@\\S+\\.\\S+")

        if (!pattern.matcher(value).matches()) {
            return true
        }
        return false
    }
    //No funciona todavia mirar Expresión regular
    fun ValidarPassword(password: String?): Boolean {
        // Expresión regular para la validación de la contraseña
        val regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{7,}$".toRegex()
        return regex.matches(password!!)
    }

}