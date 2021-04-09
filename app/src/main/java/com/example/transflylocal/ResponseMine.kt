package com.example.transflylocal

import java.util.ArrayList

class ResponseMine {

    // @SerializedName("id")
    var id: Int? = 0

    //@SerializedName("name")
    var name: String? = null

    //@SerializedName("area")
    var area: String? = null

    //@SerializedName("trailer")
    var trailer: Boolean? = null

    //@SerializedName("active")
    var active: Boolean? = null

    //@SerializedName("tyres")
    var tyres: Int? = 0

    //@SerializedName("bodytype")
    var bodytype: String? = null

    //@SerializedName("loading")
    var loading: ArrayList<ResponseLoading>? = ArrayList<ResponseLoading>();

//    @SerializedName("rate")
//    var rate: Int? = null
//
//    @SerializedName("etl")
//    var etl: Int? = null


    //@SerializedName("latitude")
    var latitude: String? = null

    //@SerializedName("longitude")
    var longitude: String? = null

    //@SerializedName("arealatitude")
    var arealatitude: String? = null

    //@SerializedName("arealongitude")
    var arealongitude: String? = null

    //@SerializedName("landmark")
    var landmark: String? = null

    //@SerializedName("fieldstaff")
    var fieldstaff: String? = null

    //@SerializedName("areamanager")
    var areamanager: String? = null

    //@SerializedName("areaimageurl")
    var areaimageurl: String? = null

}