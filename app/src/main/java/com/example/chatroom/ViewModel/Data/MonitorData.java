package com.example.chatroom.ViewModel.Data;
import java.util.List;

public class MonitorData {
    private int heartBeat;
    private int outputHeartbeat;
    private List<Integer> heartBeatList;
    private float bodyTemperature;
    private List<Float> bodyTemperatureList;
    private float indoorTemperature;
    private List<Float> indoorTemperatureList;
    private int resultTemperature;





    public MonitorData(int heartBeat,List<Integer> heartBeatList,float bodyTemperature,List<Float> bodyTemperatureList,float indoorTemperature,List<Float> indoorTemperatureList){

        this.heartBeat = heartBeat;
        this.heartBeatList = heartBeatList;
        this.outputHeartbeat = 70;
        this.bodyTemperature = bodyTemperature;
        this.bodyTemperatureList = bodyTemperatureList;
        this.indoorTemperature = indoorTemperature;
        this.indoorTemperatureList=indoorTemperatureList;
        this.indoorTemperature = 23;



    }
    public void updatehearBeatList(int heartBeat, List<Integer> hearBeatList){
        hearBeatList.add(heartBeat);

    }
    private int calculateAverage(List<Integer> heartBeatList){
        // 5 mins average heartBeat;
        int sumHeratBeat = 0;

        for (int i = 0; i<5;i++){
            sumHeratBeat+= heartBeatList.get(i);

        }
        int averageHeartBeat =sumHeratBeat/5;
        heartBeatList.remove(0);

        return averageHeartBeat;
    }


}
