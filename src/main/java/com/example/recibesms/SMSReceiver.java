package com.example.recibesms;
import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SMSReceiver extends BroadcastReceiver{

    private void sendSMS(String phoneNumber, String message){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }

    public String[] leer(String sms){//Divide la fecha en dia, mes, anio
        char[] msj = sms.toCharArray();
        String[] fecha;
        switch (msj[2]) {
            case '/':{//[20/01/1999]
                fecha = sms.split("/");
            };
            break;
            case '-':{//[20-01-1999]
                fecha = sms.split("-");
            };
            break;
            case ' ':{//[20 01 1999]
                fecha = sms.split(" ");
            };
            break;//
            default:{
                fecha = null;
            };
            break;
        }
        return fecha;
    }

    public int numero(String[] fecha){
        int numero=0;
        for (int i=0; i<fecha.length;i++)
            numero+=Integer.parseInt(fecha[i]);
        return numero;
    }

    public String fechahoy(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-YYY");
        return dateTime.format(formatters);
    }

    public String horoscopoMaya(String[] fecha){
        String sim="", sig="", des="";
        int dia=Integer.parseInt(fecha[0]);

        switch (fecha[1]) {
            case "01":{//enero
                if(dia<=9){
                    sim="KIBRAY";
                    sig="LAGARTO";
                    des="PREDICCION";
                }

                if(dia>=10){
                    sim="BATZ KIMIL";
                    sig="MONO";
                    des="PREDICCION";
                }
                break;
            }
            case "02":{//febrero
                if(dia<=6){
                    sim="BATZ KIMIL";
                    sig="MONO";
                    des="PREDICCION";
                }

                if(dia>=7){
                    sim="COZ ";
                    sig="HALCÓN";
                    des="PREDICCION";
                }

                break;
            }
            case "03":{//marzo
                if(dia<=6){
                    sim="COZ ";
                    sig="HALCÓN";
                    des="PREDICCION";
                }

                if(dia>=7){
                    sim="BALAM";
                    sig="JAGUAR";
                    des="PREDICCION";
                }

                break;
            }
            case "04":{//abril
                if(dia<=3){
                    sim="BALAM";
                    sig="JAGUAR";
                    des="PREDICCION";
                }

                if(dia>=4){
                    sim="FEX ";
                    sig="ZORRO ";
                    des="PREDICCION";
                }

                break;
            }
            case "05":{//mayo
                if(dia<=1){
                    sim="FEX ";
                    sig="ZORRO ";
                    des="PREDICCION";
                }

                if((dia>=2)&&(dia<=29)){
                    sim="KAN";
                    sig="SERPIENTE";
                    des="PREDICCION";
                }
                if(dia>=30){
                    sim="TZUB";
                    sig="ARDILLA";
                    des="PREDICCION";
                }
                break;
            }
            case "06":{//junio
                if(dia<=26){
                    sim="TZUB";
                    sig="ARDILLA";
                    des="PREDICCION";
                }
                if(dia>=27){
                    sim="AAK";
                    sig="TORTUGA";
                    des="PREDICCION";
                }

                break;
            }
            case "07":{//julio
                if(dia<=25){
                    sim="AAK";
                    sig="TORTUGA";
                    des="PREDICCION";
                }
                if(dia>=26){
                    sim="TZOOTZ ";
                    sig="MURCIELAGO ";
                    des="PREDICCION";
                }

                break;
            }
            case "08":{//agosto
                if(dia<=22){
                    sim="TZOOTZ ";
                    sig="MURCIELAGO ";
                    des="PREDICCION";
                }

                if(dia>=23){
                    sim="DZEC ";
                    sig="ESCORPIÓN ";
                    des="PREDICCION";
                }

                break;
            }
            case "09":{//septiembre
                if(dia<=19){
                    sim="DZEC ";
                    sig="ESCORPIÓN ";
                    des="PREDICCION";
                }

                if(dia>=20){
                    sim="KEH ";
                    sig="VENADO ";
                    des="PREDICCION";
                }

                break;
            }
            case "10":{//octubre
                if(dia<=17){
                    sim="KEH ";
                    sig="VENADO ";
                    des="PREDICCION";
                }

                if(dia>=18){
                    sim="MOAN ";
                    sig="LECHUZA ";
                    des="PREDICCION";
                }
                break;
            }
            case "11":{//novimebre
                if(dia<=14){
                    sim="MOAN ";
                    sig="LECHUZA ";
                    des="PREDICCION";
                }

                if(dia>=15){
                    sim="KUTZ ";
                    sig="PAVO REAL ";
                    des="PREDICCION";
                }


                break;
            }
            case "12":{//diciembre
                if(dia<=12){
                    sim="KUTZ ";
                    sig="PAVO REAL ";
                    des="PREDICCION";
                }

                if(dia>=13){
                    sim="KIBRAY";
                    sig="LAGARTO";
                    des="PREDICCION";
                }

                break;
            }
            default:{
            }
            break;
        }
        return (sim+"\n"+sig+"\n"+des);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle Bundle = intent.getExtras();
        String phone="", message="";

        MainActivity main = new MainActivity ();

        if (Bundle != null) {
            Object[] sms = (Object[]) Bundle.get("pdus");
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);
                phone = smsMessage.getOriginatingAddress();
                message = smsMessage.getMessageBody().toString();
            }

            String[] fecha = leer(message);

            message=""+horoscopoMaya(fecha);

            try {
                main.sendSM(phone, message);
            }catch(Exception e) {
                Toast.makeText(context, "Error al enviar el sms\n" + e, Toast.LENGTH_LONG).show();
            }

            message="Tu numero de la suerte: "+numero(fecha);

            try {
                main.sendSM(phone, message);
            }catch(Exception e) {
                Toast.makeText(context, "Error al enviar el sms\n" + e, Toast.LENGTH_LONG).show();
            }

            message="Horoscopo de hoy:\n"+horoscopoMaya(leer(fechahoy()));

            try {
                main.sendSM(phone, message);
            }catch(Exception e) {
                Toast.makeText(context, "Error al enviar el sms\n" + e, Toast.LENGTH_LONG).show();
            }
        }
    }


}