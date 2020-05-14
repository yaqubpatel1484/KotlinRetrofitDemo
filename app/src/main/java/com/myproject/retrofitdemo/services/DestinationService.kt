package com.myproject.retrofitdemo.services

import com.myproject.retrofitdemo.models.Staff
import com.smartherd.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.*

interface DestinationService {

    //For Normal PHP APIs


//    //get list list of staff
    @GET("get_staff.php")
    fun getDestinationList(): Call<List<Staff>>

    // get details of staff
    @FormUrlEncoded
    @POST("get_staff_details.php")
    fun getDetails(@Field("id") id: Int): Call<List<Staff>>


    //add staff
    /* Send Data in HTTP Request :
    Content type 2: FormUrlEncoded Format */
    @FormUrlEncoded
    @POST("add_staff.php")
    fun addStaff(
        @Field("name") name: String,
        @Field("contact") contact: String,
        @Field("address") address: String
    ): Call<String>

    //update staff
    @FormUrlEncoded
    @POST("update_staff.php")
    fun updateStaff(
        @Field("id") id: Int,
        @Field("name") name: String,
        @Field("contact") contact: String,
        @Field("address") address: String
    ): Call<String>

    //delete data
    // we can also use Void in return type of request and response statement if we don't pass and receive anything in body
    // or  we dont know which of response we will receive
    @FormUrlEncoded
    @POST("delete_staff.php")
    fun deleteStaff(@Field("id") id: Int): Call<Void>

    // modify headers

    // if we are not using standard header then we should add x- prefix
    // here we added multiple header separated by comma
    @Headers("x-device-type: Android","x-foo: bar")
    @GET("get_staff.php")
    fun getDestinationList(
        @Header("Accept-Language") language: String   // adding dynamic header
    ): Call<List<Staff>>

/*

//For RESTful Node APIs


//get list list of staff
 @GET("get_staff.php")
    fun getDestinationList(): Call<List<Staff>>

  *************************************************************



    Patch 1
    Use of @Url annotation for passing another or multiple base url in same application
    @GET
    fun getDestinationList(@Url url: String): Call<List<Staff>>

    *************************************************************

    Patch 2
    For RESTfull Api
    // Path Parameter Example ie http://smarthert.com/user/47
    @GET("get_staff_details.php/{id}")
    fun getDetails1(@Path("id") id: Int): Call<Staff>

    *************************************************************

    Patch 3
    For RESTfull Api
    // Query Parameter Method:
    Example ie http:
    //smarthert.com/user?country=india
    @GET("get_staff.php")
    fun getDestinationList(@Query("name") name: String): Call<List<Staff>>

    *************************************************************

    patch 4
    Query Parameter Method :
    For RESTfull Api which also has on function to satisfy this type of request
    // Query Parameter Example ie http://smarthert.com/user?country=india&count=1
    @GET("get_staff.php")
    fun getDestinationList(
        @Query("name") name: String,
        (@Query("count") name: String ): Call<List<Staff>>

    *************************************************************

    patch 5
    Map Query Parameter:
    For RESTfull Api which also has on function to satisfy this type of request
    // Map Query format has same as above Query Parameter format ie http://smarthert.com/user?country=india&count=1
    @GET("get_staff.php")
    fun getDestinationList(@QueryMap filter: HashMap<String, String>): Call<List<Staff>>

    *************************************************************

// get details of staff
    patch 6
    Path Query Parameters:
    //  http://smarthert.com/user/47?country=india&count=2
    @GET("get_staff_details.php/{id}")
    fun getDetails1(
        @Path("id") id: Int,
        @Query("name") name: String,
        (@Query("count") count: Int): Call<Staff>
    url formate will be http://smarthert.com/user/47?count=2 if @nd parameter of above ie @Query("name") name: String  passes to null

    *************************************************************

     //add staff
    Send Data in HTTP Request :
    Content type 1: Json Format
    @POST("add_staff.php")
    fun addDestination(@Body newStaff: Staff): Call<Staff>



    *************************************************************

     //update staff

    @FormUrlEncoded
    @PUT("update_staff.php/{id}")
    fun updateStaff(
        @Path("id") id: Int,
        @Field("name") name: String,
        @Field("contact") contact: String,
        @Field("address") address: String
    ): Call<Staff>

     *************************************************************

        //delete data
        // // we can also use Void in return type of request and response statement if we don't pass and receive anything in body
    // or  we dont know which of response we will receive
    @DELETE("delete_staff.php/{id}")
    fun deleteStaff(@Path("id") id: Int): Call<Void>

     *************************************************************

      // modify headers

   // if we are not using standard header then we should add 'x-' prefix
    // here we added multiple header separated by comma
   @Headers("x-device-type: Android","x-foo: bar")
    @GET("get_staff.php")
    fun getDestinationList(
    @QueryMap filter: HashMap<String, String>,
        @Header("Accept-Language") language: String   // adding dynamic header
    ): Call<List<Staff>>

  */
}