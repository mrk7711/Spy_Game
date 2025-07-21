package com.example.spygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class RoleActivity extends AppCompatActivity {
    private int players;
    String selectedWord;
    private int spies;
    private int time;
    private String word;
    private String[] chosenList;
    private CountDownTimer countDownTimer;
    private List<Integer> spiesList;
    private int currentPlayerIndex = 0;
    private boolean isShowingRole = false;
    private boolean gameStarted = false;
    private TextView roleTextView;
    private TextView playerNumberTextView;

    String[] locations = {
            "مدرسه", "هواپیما", "رستوران", "ساحل", "ایستگاه قطار",
            "بیمارستان", "سینما", "سفینه فضایی", "زندان", "موزه", "سوپرمارکت",
            "کارخانه", "ورزشگاه", "کتابخانه", "کلاس درس", "استخر", "کشتی تفریحی"
    };

    String[] professions = {
            "پزشک", "مهندس", "معلم", "آشپز", "نقاش", "بازیگر", "خواننده", "راننده تاکسی",
            "پرستار", "آتش‌نشان", "کارآگاه", "پلیس", "وکیل", "قاضی", "نجار", "لوله‌کش",
            "برنامه‌نویس", "طراح گرافیک", "مدیر", "کارگر", "نویسنده", "مترجم", "خلبان", "مهماندار",
            "کشاورز", "دامپزشک", "روانشناس", "مشاور", "مکانیک", "سرباز", "سیاست‌مدار", "اقتصاددان",
            "تحلیلگر داده", "فروشنده", "بازاریاب", "حسابدار", "صندوق‌دار", "آرایشگر", "عکاس",
            "خبرنگار", "مجری", "بازی‌ساز", "توسعه‌دهنده وب", "مدرس دانشگاه", "فیلسوف", "نقشه‌کش",
            "مامور امنیتی", "پژوهشگر", "بنا", "راننده کامیون"
    };
    String[] objectsList = {
            "صندلی", "میز", "کتاب", "خودکار", "لیوان", "تلفن", "کیف", "ساعت", "کفش", "چتر",
            "کلاه", "کمد", "تلویزیون", "رادیو", "یخچال", "چراغ", "آینه", "مداد", "قیچی", "کاغذ",
            "کلید", "دوربین", "تبلت", "لپ‌تاپ", "ماشین حساب", "پنکه", "بخاری", "ماوس", "کیبورد", "هندزفری",
            "ترازو", "فرش", "پرده", "سطل", "قاشق", "چنگال", "چاقو", "تابلو", "بالش", "پتو",
            "تلویزیون", "موبایل", "بسته پستی", "بطری", "جا کلیدی", "دفتر", "پاک‌کن", "خط‌کش", "سطل زباله", "ریموت کنترل"
    };


    private Button nextRoleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);
        roleTextView = findViewById(R.id.roleTextView);
        playerNumberTextView = findViewById(R.id.playerNumberTextView);
        nextRoleButton = findViewById(R.id.nextRoleButton);
        Intent intent = getIntent();
        players = intent.getIntExtra("players", 0);
        spies = intent.getIntExtra("spies", 0);
        time = intent.getIntExtra("time", 0);
        word = intent.getStringExtra("word");
        switch(word)
        {
            case "مکان":
                chosenList=locations;
                break;
            case "شغل":
                chosenList=professions;
                break;
            case "اشیا":
                chosenList=objectsList;
                break;
            default:
                chosenList=objectsList;
                break;
        }
        selectedWord = chosenList[new Random().nextInt(chosenList.length)];
        nextRoleButton.setText("شروع بازی");
        roleTextView.setText("");
        playerNumberTextView.setText("");
        nextRoleButton.setOnClickListener(v -> {
            if (!gameStarted) {
                gameStarted = true;
                prepareRoles();
                showReadyScreen();  // حالت آماده برای بازیکن اول
                nextRoleButton.setText("نمایش نقش");
            } else {
                if (!isShowingRole) {
                    showNextRole();  // نمایش نقش برای بازیکن فعلی
                    isShowingRole = true;
                    nextRoleButton.setText("پنهان کن و بده به بعدی");
                } else {
                    isShowingRole = false;
                    currentPlayerIndex++;
                    if (currentPlayerIndex >= players) {
                        // نمایش نقش‌ها تمام شد
                        roleTextView.setText("");
                        playerNumberTextView.setText("");
                        nextRoleButton.setVisibility(View.GONE);
                        startGameTimer();
                    } else {
                        showReadyScreen();  // حالت آماده برای بازیکن بعدی
                        nextRoleButton.setText("نمایش نقش");
                    }
                }
            }
        });
    }
    private void prepareRoles() {
        List<Integer> playersList = new ArrayList<>();
        for (int i = 1; i <= players; i++) {
            playersList.add(i);
        }
        Collections.shuffle(playersList);
        spiesList = playersList.subList(0, spies);
    }
    private void showReadyScreen() {
        int playerNumber = currentPlayerIndex + 1;
        playerNumberTextView.setText("بازیکن شماره " + playerNumber);
        roleTextView.setText("برای دیدن نقش خود دکمه را بزنید");
    }
    private void showNextRole() {
        int playerNumber = currentPlayerIndex + 1;
        playerNumberTextView.setText("بازیکن شماره " + playerNumber);

        if (spiesList.contains(playerNumber)) {
            roleTextView.setText("شما جاسوس هستید 🤫");
        } else {
            roleTextView.setText(selectedWord);
        }
    }
    private void startGameTimer() {

        // مثلاً تایمر 5 دقیقه‌ای (300000 میلی‌ثانیه)
        countDownTimer = new CountDownTimer(time*60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long minutes = millisUntilFinished / 60000;
                long seconds = (millisUntilFinished % 60000) / 1000;

                String time = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
                roleTextView.setText("زمان باقی‌مانده: " + time);
            }

            @Override
            public void onFinish() {
                roleTextView.setText("زمان تمام شد! پایان بازی 🎯");
            }
        }.start();
    }
}