package com.example.boge.child.addParent;

/**
 * Created by Boge on 2017/5/24.
 */

public class PersonInfo {
    private static PersonInfo personInfo=null;
    private String account="";

    private PersonInfo(){

    }
    public static PersonInfo getPersonInfo() {
        if (personInfo == null)
            personInfo = new PersonInfo();
        return personInfo;
    }
    public String getAccount(){
        return account;
    }
    public void setAccount(String account){
        this.account=account;
    }
}
