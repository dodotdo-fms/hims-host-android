package com.dodotdo.himsadmin.serverinterface;


import com.dodotdo.himsadmin.model.Clean;
import com.dodotdo.himsadmin.model.Employee;
import com.dodotdo.himsadmin.model.Requirement;
import com.dodotdo.himsadmin.model.Room;
import com.dodotdo.himsadmin.serverinterface.request.LoginRequest;
import com.dodotdo.himsadmin.serverinterface.request.RequestAssignEmployeeToClean;
import com.dodotdo.himsadmin.serverinterface.request.RequestPostRequirementState;
import com.dodotdo.himsadmin.serverinterface.request.RequestPutRequirementState;
import com.dodotdo.himsadmin.serverinterface.request.RequestRequirementSend;
import com.dodotdo.himsadmin.serverinterface.response.GetLoginResponse;
import com.dodotdo.himsadmin.serverinterface.response.GetRequirementsResponse;
import com.dodotdo.himsadmin.serverinterface.response.Results;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.HTTP;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Omjoon on 2015. 12. 7..
 */
public interface ServerAPI {
//    @GET("/api/users")
//    Call<GetUsersResponse> getUsers();
//

    @POST("/api/v2/auth/employee")
    Call<GetLoginResponse> goLogin(@Body LoginRequest loginRequest);



    @GET("/api/v2/requirements")
    Call<GetRequirementsResponse> getRequirements(@Query("status") String status,@Query("start")String starts);

    @GET("/api/v2/employees")
    Call<Results<List<Employee>>> getEmployees();

    @GET("/api/v2/rooms")
    Call<Results<List<Room>>> getRooms();

    @PUT("/api/v2/requirements/{requirementId}")
    Call<Results<Requirement>> putRequirementState(@Path("requirementId") String requirementId,@Body RequestPutRequirementState state);

    @POST("/api/v2/requirements/{requirementId}/employees")
    Call<Results<Requirement>> postRequirementState(@Path("requirementId") String requirementId, @Body RequestPostRequirementState ids);

    @POST("/api/v2/requirements")
    Call<Results<Requirement>> sendRequirement(@Body RequestRequirementSend body);

    @POST("/api/v2/employees/{userid}/cleans")
    Call<Results<List<Clean>>> assignEmployeeToClean(@Path("userid")String userId,@Body RequestAssignEmployeeToClean body);
    @HTTP(method = "DELETE", path = "api/v2/employees/{userid}/cleans", hasBody = true)
    Call<Results<String>> deleteAssign(@Path("userid")String userId,@Body RequestAssignEmployeeToClean body);
//
//    @GET("/api/users/logout")
//    Call<LogoutResponse> goLogout();
//
//    @GET
//    Call<GetRoomsResponse> getRooms(@Url String url);
//
//    @POST("/api/clean")
//    Call<PostCleanResponse> postClean(@Body RequestPostClean requestPostClean);
//
//    @GET
//    Call<GetChannelResponse> getChannel(@Url String path);
//
//    @GET
//    Call<GetMessageResponse> getMessages(@Url String path);
//
//    @GET
//    @Streaming
//    Call<ResponseBody> getMessage(@Url String path);
//
//    @GET("/api/walkie/channel/{channelId}/enter")
//    Call<CommonResultReponse> postChannelEnter(@Path("channelId") String channelId);
//
//    @GET("/api/walkie/channel/{channelId}/exit")
//    Call<CommonResultReponse> postChannelExit(@Path("channelId") String channelId);
//
//    @Multipart
//    @POST("/api/walkie/channel/{channelId}/msg")
//    Call<CommonResultReponse> postMsg(@Path("channelId") String channelId,
//                                      @Part("file") RequestBody body);
//
//    @POST("api/push/register")
//    Call<CommonResultReponse> postRegisterDeviceId(@Body RequestRegisterDeviceId requestbody);
//
//    @DELETE("/api/push/register/{deviceId}")
//    Call<CommonResultReponse> deleteRegisterDeviceId(@Path("deviceId") String deviceId);
}
