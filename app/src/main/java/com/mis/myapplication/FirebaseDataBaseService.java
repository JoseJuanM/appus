package com.mis.myapplication;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import okhttp3.internal.connection.StreamAllocation;

public class FirebaseDataBaseService {
    private static String userId;
    private static FirebaseDataBaseService service;
    private static FirebaseDatabase mDatabase;

    public static FirebaseDataBaseService getServiceInstance(){
        if (service == null || mDatabase == null){
            service = new FirebaseDataBaseService();
            mDatabase = FirebaseDatabase.getInstance();
            mDatabase.setPersistenceEnabled(true);
        }
        if (userId == null || userId.isEmpty()){
            userId = FirebaseAuth.getInstance().getCurrentUser() != null ? FirebaseAuth.getInstance().getCurrentUser().getUid() : "";
        }
        return service;
    }

    public DatabaseReference getItem(String travelId){
        return mDatabase.getReference("user/U123/travel/" + travelId).getRef();
    }
}
