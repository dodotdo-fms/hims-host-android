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
import com.dodotdo.himsadmin.utill.TimeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Call;

/**
 * Created by Omjoon on 2015. 12. 7..
 */
public class ServerQuery {
    public static void goLogin(retrofit.Callback callback,String id,String pw){
        Call<GetLoginResponse> call = ServiceGenerator.createService(ServerAPI.class).goLogin(new LoginRequest(id,pw,null));
        call.enqueue(callback);
    }

    public static void getRequirements(retrofit.Callback callback,String status,String start){
        Call<GetRequirementsResponse> call = ServiceGenerator.createService(ServerAPI.class).getRequirements(status,start);
        call.enqueue(callback);
    }

    public static void getEmployees(retrofit.Callback callback){
        Call<Results<List<Employee>>> call = ServiceGenerator.createService(ServerAPI.class).getEmployees();
        call.enqueue(callback);
    }

    public static void getRoom(retrofit.Callback callback){
        Call<Results<List<Room>>> call = ServiceGenerator.createService(ServerAPI.class).getRooms();
        call.enqueue(callback);
    }

    public static void putRequirementState(retrofit.Callback callback,String requirementId,String state){
        Call<Results<Requirement>> call = ServiceGenerator.createService(ServerAPI.class).putRequirementState(requirementId,new RequestPutRequirementState(state));
        call.enqueue(callback);
    }

    public static void postRequirementAssign(retrofit.Callback callback,String requirementId,List<String> ids){
        Call<Results<Requirement>> call = ServiceGenerator.createService(ServerAPI.class).postRequirementState(requirementId,new RequestPostRequirementState(ids));
        call.enqueue(callback);
    }

    public static void sendRequirement(retrofit.Callback callback,String message,List<String> ids){
        Call<Results<Requirement>> call = ServiceGenerator.createService(ServerAPI.class).sendRequirement(new RequestRequirementSend(message,ids));
        call.enqueue(callback);
    }

    public static void assignEmployeeToClean(retrofit.Callback callback,String userId,ArrayList<String> roomNumbers){
        Call<Results<List<Clean>>> call = ServiceGenerator.createService(ServerAPI.class).assignEmployeeToClean(userId,new RequestAssignEmployeeToClean(roomNumbers));
        call.enqueue(callback);
    }

    public static void unAssignEmployeeToClean(retrofit.Callback callback,String userId,String roomNumber){
        ArrayList<String > roomNumbers = new ArrayList<>();
        roomNumbers.add(roomNumber);
        Call<Results<String>> call = ServiceGenerator.createService(ServerAPI.class).deleteAssign(userId,new RequestAssignEmployeeToClean(roomNumbers));
        call.enqueue(callback);
    }

}
