package com.codemobile.hackcatonapp.interfaces

interface QueryUser{
    fun queryUserData(userArrayList:ArrayList<String>,id:String?)
}
interface UpdateApprove{
    fun updateLending(idUser:String)
}