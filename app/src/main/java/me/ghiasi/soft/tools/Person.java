package me.ghiasi.soft.tools;

public class Person {
    //[[1,0,"Masoud",null,null,"00:00:00","00:00:00",null,"09360840616",0,0,0]]
    //`SPid`,`Cid`,`SPname`,`SPprofileImg`,`SPpictures`,`SPstartWorkTime`,`SPendWorkTime`,`SPdiscreption`,`SPphoneNumber`,`SPvote`,`SPbusy`,`SPstatus`
    int SPid, Cid, vote,busy,status;
    String name;
    String profileImg, pictures, startWorkTime, endWorkTime, discreption, phoneNumber;

    public Person(int SPid, int cid, String name, String profileImg
            , String pictures, String startWorkTime, String endWorkTime, String discreption
            , String phoneNumber, int vote ,int busy , int status) {
        this.SPid = SPid;
        Cid = cid;
        this.vote = vote;
        this.name = name;
        this.profileImg = profileImg;
        this.pictures = pictures;
        this.startWorkTime = startWorkTime;
        this.endWorkTime = endWorkTime;
        this.discreption = discreption;
        this.phoneNumber = phoneNumber;
        this.busy = busy;
        this.status = status;
    }

    public int getCid() {
        return Cid;
    }

    public int getVote() {
        return vote;
    }

    public String getName() {
        return name;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public String getPictures() {
        return pictures;
    }

    public String getStartWorkTime() {
        return startWorkTime;
    }

    public String getEndWorkTime() {
        return endWorkTime;
    }

    public String getDiscreption() {
        return discreption;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getSPid() {
        return SPid;
    }

    public int getBusy() {
        return busy;
    }

    public int getStatus() {
        return status;
    }
}