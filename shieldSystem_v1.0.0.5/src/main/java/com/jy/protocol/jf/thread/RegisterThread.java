package com.jy.protocol.jf.thread;

public class RegisterThread extends Thread{

    public RegisterThread(){

    }

    public RegisterThread(String name){
        super.setName(name);
    }

    @Override
    public void run(){
//        while(true){
//            Object flag = ParamStatic.issuedStaticMap.get("flag");
//            if(flag != null && (boolean)flag){
//                String ip = null;
//                try {
//                    ip = (String) ParamStatic.issuedStaticMap.get(IssuedSendUtils.DETECTION_IP);
//                    Thread.sleep(1000);
//                    if(StringUtils.isBlank(ip)){
//                        System.out.println("未获取到ip信息");
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                if(StringUtils.isNotBlank(ip)) {
//                    GeneralProtocol.register();
//                    break;
//                }
//            }
//        }
    }	
}
