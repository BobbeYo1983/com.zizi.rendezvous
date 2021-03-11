package com.zizi.rendezvous.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.DocumentReference;
import com.zizi.rendezvous.Activity.ActivityMeetings;
import com.zizi.rendezvous.GlobalApp;
import com.zizi.rendezvous.R;

/** Фразмент с описанием правил */
public class FragmentRegulations extends Fragment {

    private GlobalApp globalApp; //глобальный класс приложения, общий для всех компонентов

    public FragmentRegulations() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_regulations, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        globalApp = (GlobalApp) getActivity().getApplicationContext();
        globalApp.Log(getClass().getSimpleName(), "onActivityCreated", "Начато создание фрагмента", false);

        ActivityMeetings activityMeetings = (ActivityMeetings)getActivity(); // активити с фрагментами

        //ищем виджеты
        MaterialToolbar materialToolbar = getActivity().findViewById(R.id.material_toolbar); // верхняя панелька
        CheckBox cb_18_years = getActivity().findViewById(R.id.cb_18_years); //галка с подтверждением, что исполнилось 18 лет
        Button btn_accept_rules = getActivity().findViewById(R.id.btn_accept_rules); // кнопка принятия правил

        // materialToolbar //////////////////////////////////////////////////////////////////////////
        materialToolbar.setTitle("Правила"); // заголовок панельки
        materialToolbar.setNavigationIcon(null);//скрываем иконку навигации
        materialToolbar.getMenu().findItem(R.id.request).setVisible(false); // скрываем пункт заявки на встречу
        //===========================================================================================



        //btn_accept_rules//////////////////////////////////////////////////////////////////////////
        btn_accept_rules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cb_18_years.isChecked()) { //если отмечено, что исполнилось 18

                    //отмечаем в БД, что с правилами ознакомлен
                    globalApp.currentUser.setAcceptRules(true);
                    globalApp.GenerateDocumentReference("users", globalApp.currentUser.getUserID())
                            .set(globalApp.currentUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {//если запись в БД успешна

                                        activityMeetings.ChangeFragment(new FragmentRequestMeeting(), false);

                                    } else { //если запись в БД успешна

                                        //Покажем пользователю сообщение
                                        new AlertDialog.Builder(getContext())
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setTitle("Ошибка записи в БД")
                                                .setMessage("Ошибка записи в БД отметки, что правила приняты пользователем, повторите попытку позже.")
                                                .setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                })
                                                //.setNegativeButton("No", null)
                                                .show();

                                    }
                                }
                            });



                    
                } else { //если не исполнилось 18 лет

                    //Покажем пользователю сообщение
                    new AlertDialog.Builder(getContext())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Возрастное ограничение")
                            .setMessage("Приложение предназначено для пользователей старше 18 лет. " +
                                    "Если вам уже исполнилось 18 лет, подтвердите, поставив соответствующую галочку. " +
                                    "Если вам еще не исполнилось 18 лет, то вы не должны использовать это приложение.")
                            .setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            //.setNegativeButton("No", null)
                            .show();

                }
            }
        });
        //==========================================================================================



        //ЛОГИКА //////////////////////////////////////////////////////////////////////////////////////
        if (globalApp.GetBundle("moderation") != null && globalApp.GetBundle("moderation").equals("false")) { //если заявка пользователя отклонена на модерации, то показать правила

            //Показать сообщение пользователю
            new AlertDialog.Builder(getContext())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Нарушение правил")
                    .setMessage("Ваша заявка на встречу отклонена модератором, вы не соблюдаете правила приложения. " +
                            "Еще раз внимательно ознакомьтесь и примите правила. В случае повторных нарушений вы будете временно заблокированы.")
                    .setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    //.setNegativeButton("No", null)
                    .show();

        }
        //=======================================================================================

    }
}