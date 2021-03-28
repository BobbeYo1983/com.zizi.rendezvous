package com.zizi.rendezvous.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.zizi.rendezvous.Activity.ActivityMeetings;
import com.zizi.rendezvous.BuildConfig;
import com.zizi.rendezvous.Data.Data;
import com.zizi.rendezvous.GlobalApp;
import com.zizi.rendezvous.Models.ModelMeeting;
import com.zizi.rendezvous.R;

import java.sql.Struct;
import java.util.Date;
import java.util.Random;

/** Фрагмент с функциями администратора */
public class FragmentAdmin extends Fragment {

    private GlobalApp globalApp; // глобальный класс приложения
    private ActivityMeetings activityMeetings; //Активити со встречами
    private boolean tietCountMeetingsIsUpdate; // поле обновлено/вычитано из БД

    //виджеты
    private MaterialToolbar materialToolbar; // верхняя панелька
    private ProgressBar progressBar; //крутилка
    private DrawerLayout drawerLayout; //шторка меню слева
    private TextInputEditText tietCountMeetings; //количество активных заявок на встречи
    private Button bModeration; //кнопка с модерацией
    private Button bTimeCheck; //кнопка проверки просрочки заявок



    public FragmentAdmin() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        globalApp = (GlobalApp) getActivity().getApplicationContext(); //получаем глобальный класс приложения
        tietCountMeetingsIsUpdate = false; //отмечаем, что поле не обновлено
        activityMeetings = (ActivityMeetings)getActivity();

        //ищем вьюхи
        materialToolbar = getActivity().findViewById(R.id.material_toolbar); // верхняя панелька
        progressBar = getActivity().findViewById(R.id.progressBar);
        tietCountMeetings = getActivity().findViewById(R.id.tietCountMeetings);
        bModeration = getActivity().findViewById(R.id.bModeration);
        drawerLayout = getActivity().findViewById(R.id.drawerLayout); //слой левой шторки
        bTimeCheck = getActivity().findViewById(R.id.bTimeCheck);



    }

    @Override
    public void onStart() {
        super.onStart();

        // materialToolbar /////////////////////////////////////////////////////////////////////////
        materialToolbar.setTitle(globalApp.GetBundle("title")); //устанавливаем название из аргументов
        materialToolbar.setNavigationIcon(R.drawable.ic_outline_menu_24); // делаем кнопку навигации менюшкой
        materialToolbar.getMenu().findItem(R.id.request).setVisible(false); // скрываем пункт заявки на встречу
        // событие при клике на кнопку навигации/гамбургер на верхней панельке
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        //==========================================================================================



        ShowProgressBar(true);//показать экран ожидания



        //tietCountMeetings //////////////////////////////////////////////////////////////////////////
        // Запрос количества заявок
        queryCountMeetings();
        //===========================================================================================



        //bModeration//////////////////////////////////////////////////////////////////////////////
        bModeration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentModeration fragmentModeration = new FragmentModeration();
                activityMeetings.ChangeFragment(fragmentModeration, true);
            }
        });
        //==========================================================================================



        //bTimeCheck////////////////////////////////////////////////////////////////////////////////
        //проверяем заявки на просроченность
        bTimeCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //проверяем первую заявку
                checkTimeRequest(123, true);

            }
        });
        //==========================================================================================


    }



    /** Запрос количества заявок из БД */
    private void queryCountMeetings() {
        CollectionReference collectionReference = globalApp.GenerateCollectionReference("meetings");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    globalApp.Log(getClass().getSimpleName(), "onStart/onComplete", "Количество заявок на встречу = " + task.getResult().size(), false);
                    int countMeetings = task.getResult().size(); //количество встреч
                    tietCountMeetings.setText(String.valueOf(countMeetings));
                    tietCountMeetingsIsUpdate = true; //отмечаем, что поле обновлено
                    UpdateUI(); //пробуем обновить интерфейс, сейчас там крутится прогрессбар
                } else {
                    globalApp.Log(getClass().getSimpleName(), "onStart/onComplete", "Ошибка при запросе количества заявок на встречу: " + task.getException(), false);
                }
            }
        });
    }



    /**
     * Проверяет заявку, просрочилась ли она, если просрочилась, то удаляет из БД
     * @param meetingID - идентификатор заявки, за котрым выбирается следующая заявка
     * @param first - усли true, то первый параметр игнорируется и из БД берется первая заявка
     */
    private void checkTimeRequest (long meetingID, boolean first) {

        ShowProgressBar(true);

        CollectionReference collectionReference;
        collectionReference = globalApp.GenerateCollectionReference("meetings");

        //запрос в БД
        Query query;

        if (first) { //если выбрать первую
            query = collectionReference
                    .orderBy("meetingID") // упорядочиваем по ID
                    .limit(1) // читаем только первую
                    ;
        } else {
            query = collectionReference
                    .whereGreaterThan("meetingID", meetingID)
                    .orderBy("meetingID") // упорядочиваем по ID
                    .limit(1) // читаем только первую
            ;
        }



        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    if (!task.getResult().getDocuments().isEmpty()){ //если хотя бы один документ вычитан

                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0); //получаем документ фаресторе

                        ModelMeeting modelMeeting = documentSnapshot.toObject(ModelMeeting.class); //сериализация в класс

                        Date currentDateTime = new Date();

                        long difference = currentDateTime.getTime() - modelMeeting.getTimeStamp().getTime(); //разница по времени в милисекундах
                        difference = (int) (difference / (1000 * 60 * 60)); //в часах

                        globalApp.Log("FragmentAdmin", "bTimeCheck.setOnClickListener",
                                "Разница во времени в заявке = " + difference + " часов", false);

                        if (difference >= 24){

                            //Удаляем заявочку
                            globalApp.GenerateDocumentReference("meetings", modelMeeting.getUserID())
                                    .delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){

                                        //переходим к следующей заявке
                                        checkTimeRequest(modelMeeting.getMeetingID(), false);

                                    } else {

                                        ShowProgressBar(false);

                                        //Покажем пользователю сообщение//////////////////////////////////////////////////
                                        new AlertDialog.Builder(getContext())
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setTitle("Ошибка удаления из БД")
                                                .setMessage("Ошибка при удалении заявки из БД: " + task.getException())
                                                .setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                })
                                                //.setNegativeButton("No", null)
                                                .show();
                                        //===============================================================================
                                    }
                                }
                            });

                            //перейти к следующей
                        }


                    } else { //если нет ни одного документа

                        ShowProgressBar(false);

                        //Покажем пользователю и разрешаем повторить чтение/////////////////////////////
                        new AlertDialog.Builder(getContext())
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setTitle("Нет данных")
                                .setMessage("Нет заявок на проверку по времени.")
                                //.setPositiveButton("Повторить", new DialogInterface.OnClickListener() {
                                //    @Override
                                //    public void onClick(DialogInterface dialog, int which) {
                                //        ReadRandomMeeting();
                                //    }
                                //})
                                //.setNegativeButton("No", null)
                                .show();
                        //===============================================================================

                        queryCountMeetings();

                    }

                } else {

                    ShowProgressBar(false);

                    //Покажем пользователю сообщение//////////////////////////////////////////////////
                    new AlertDialog.Builder(getContext())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Ошибка чтения БД")
                            .setMessage("Ошибка чтения заявки из БД: " + task.getException())
                            .setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            //.setNegativeButton("No", null)
                            .show();
                    //===============================================================================
                }
            }
        });
    }



    /** Обновление и отображение всех виджетов, если все обновились и готовы к отображению*/
    private void UpdateUI() {

        //если все виджеты обновились, то показываем
        if (tietCountMeetingsIsUpdate) {

            ShowProgressBar(false);

            tietCountMeetingsIsUpdate = false; //сбрасываем признак обновления поля
        }else { //если не все виджеты обновились, то дальшее крутим баранку

            ShowProgressBar(true);

        }

    }



    /**
     * Показать экран ожидания.
     * @param show показать/не показать экран ожидания
     */
    private void ShowProgressBar (boolean show) {
        if (show) {
            progressBar.setVisibility(View.VISIBLE);

            tietCountMeetings.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);

            tietCountMeetings.setVisibility(View.VISIBLE);
        }

    }

}