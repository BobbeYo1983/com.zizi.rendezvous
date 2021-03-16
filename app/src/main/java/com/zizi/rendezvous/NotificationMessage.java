package com.zizi.rendezvous;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Класс отправки уведомления в асинхронной задаче на девайс с переданным токеном
 * String ... tokenDevice - обрабатывается только первый параметр, токен устройства, на который отправляем уведомление
 */
public class NotificationMessage extends AsyncTask<String,Void,Void> {

    private GlobalApp globalApp; //глобальный класс приложения, чтобы могли писать логи

    public NotificationMessage(GlobalApp globalApp){
        this.globalApp = globalApp;
    }


    @Override
    //protected Void doInBackground(Void... voids) {
    protected Void doInBackground(String ... tokenDevice) {

        try {

            URL url = new URL("https://fcm.googleapis.com/fcm/send");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            conn.setRequestMethod("POST");
            // тут ключ сервака Farebase для FCM
            conn.setRequestProperty("Authorization", "key=" + "AAAAZPuiZNE:APA91bHz2KjVHK_hraIu_DNWwsIuOOGhNaP3peg4FaE8Dnz3zM_FxUOM0Lk5IkSenB2weptEE5gAdU_H89Bnr0Zv4wod5MDki02xGuX39pTeBZkTyrRucVbtw3qYBO5xG-nYzXUa787q");
            conn.setRequestProperty("Content-Type", "application/json");

            JSONObject json = new JSONObject();

            ///тут указывается токен устройства на который отправляем
            globalApp.Log("ClassNotificationMessage", "doInBackground", "tokenDevice партнера = " + tokenDevice[0], false);
            json.put("to", tokenDevice[0]); // берем первый и единственный параметр со входа в функцию
            json.put("priority", "high"); // добавляем высокий приоритет, так как для типа посылки data он не самый высокий и может быть долгая доставка

            JSONObject data = new JSONObject();
            data.put("title", "Сообщение");   // Notification title
            data.put("body", "У вас есть новое сообщение"); // Notification body
            data.put("userID", globalApp.GetCurrentUserUid()); // ID пользователя отправителя
            data.put("tokenDevice", globalApp.GetTokenDevice()); // tokenDevice отправителя
            data.put("name", globalApp.requestMeeting.getName()); // имя отправителя
            data.put("age", globalApp.requestMeeting.getAge()); // возраст отправителя

            ///если не добавлять в посылку раздел notification, то данные гарантированно будут доставляться в метод onMessageReceived в службу ServiceFirebaseCloudMessaging, а из этого метода и формируем уведомление
            json.put("data", data); // добавляем в json - посылку

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(json.toString());
            wr.flush();
            wr.close();

            if (conn.getResponseCode() != 200) { // если код ошибки от сервера не равен нормальному значению 200

                globalApp.Log("ClassNotificationMessage",
                        "doInBackground",
                        "Ошибка отправки уведомления, код: " + conn.getResponseCode() + ", сообщение от сервера: " + conn.getResponseMessage(),
                        true
                );

                // тут закоментирован способ получения полного ответа
                /*
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                classLogsAllApp.Log(conn.getResponseCode() + " " + response); // пишем лог на сервак
                */
            }


        }
        catch (Exception e)
        {
            globalApp.Log("ClassNotificationMessage",
                    "doInBackground",
                    "Исключение при отправке уведомления: " + e.getMessage(),
                    true
            );


        }
        return null;
    }

}
