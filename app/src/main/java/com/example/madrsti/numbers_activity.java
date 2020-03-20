package com.example.madrsti;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class numbers_activity extends AppCompatActivity {

    private static final int TEXT_COLOR = Color.parseColor("#595959");
    static String DEFAULT_HOW_ANS="اختر الاجابة الصحيحة";

    database db=new database();

    //Module name
    String module=MainActivity.module;
ConstraintLayout respType1;
ConstraintLayout respType2;
ConstraintLayout bigQCont;
int theCurrentAnswerIndex=-1;
ImageView imgQst;
    ImageView lvlDots[]=new ImageView[10];

int localScore=0;

TextView    modType,
            question,
            how_ans,
            t1button1,
        t1button2,
        t1button3,
        t1button4,
        t2button1,
        t2button2,
        t2button3,
        t2button4,
        lvlNum,
        txtQCont;

TextView button1,button2,button3,button4;
TextView respondsbutt[]=new TextView[4];
    ;

    data[] filData;
    int currentStage=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);


        initInstances();


        filData=new data[10];


//FilData=[{Type:A,question:"1+1",resp:[2,3,1,4],imgPath:null},...,{Type:A,question:"1+1",resp:[2,3,1,4],imgPath:null}]
        /*for(int i = 0; i<filData.length; i++)
            filData[i]=new data();

        filData[0].fillType1("ماهو مجموع 20+ 16 ؟", new String[]{"36","46","26","56"},1);
        filData[1].fillType1("ما هو العدد الذي يلي مباشرة 76 ؟", new String[]{"75","77","78","74"},2);
        filData[2].fillType1("ماهو العدد الأكبر من 26 ؟", new String[]{"25","27","21","24"},2);
        filData[3].fillType1("ماهو العدد الذي يسبق مباشرة 34 ؟", new String[]{"33","32","35","36"},1);
        filData[4].fillType1("ماهي مساحة المستطيل ؟", new String[]{"طول الضلع * 4","القاعدة * الارتفاع","الطول*العرض","(الطول+العرض)*2"},3);
        filData[5].fillType1("ما هو العدد الذي يلي مباشرة 45 ؟ ", new String[]{"44","43","47","46"},4);
        filData[6].fillType1("ماهو مجموع 6+5 ؟ ", new String[]{"65","56","10","11"},4);
        filData[7].fillType1("ماهو العدد الأصغر من 90 ؟ ", new String[]{"99","70","91","92"},2);
        filData[8].fillType1("كم يساوي المتر بالسنتيمتر ؟", new String[]{"10","60","100","1000"},3);
        filData[9].fillType1("ماهي عملية الحصر الصّحيحة ؟", new String[]{"14>12>9","8>7>11","20>19>32","0>1>2"},1);
*/
        db.loadMathData();
        filData=db.getMath();
        renderStage();
    }
void initInstances(){
    respType1=findViewById(R.id.responds_container1);
    respType2=findViewById(R.id.responds_container2);
    bigQCont=findViewById(R.id.picture_container);
    imgQst=findViewById(R.id.animal_img);
    modType=(TextView) findViewById(R.id.mod_type);
    how_ans=(TextView) findViewById(R.id.choosen_answer_tv);
    t1button1=(TextView) findViewById(R.id.t1button_ch1);
    t1button2=(TextView) findViewById(R.id.t1button_ch2);
    t1button3=(TextView) findViewById(R.id.t1button_ch3);
    t1button4=(TextView) findViewById(R.id.t1button_ch4);
    t2button1=(TextView) findViewById(R.id.t2button_ch1);
    t2button2=(TextView) findViewById(R.id.t2button_ch2);
    t2button3=(TextView) findViewById(R.id.t2button_ch3);
    t2button4=(TextView) findViewById(R.id.t2button_ch4);
    lvlNum=findViewById(R.id.qst_number_tv);
    txtQCont=findViewById(R.id.qst_container_tv);
    lvlDots[0]=findViewById(R.id.dot1);
    lvlDots[1]=findViewById(R.id.dot2);
    lvlDots[2]=findViewById(R.id.dot3);
    lvlDots[3]=findViewById(R.id.dot4);
    lvlDots[4]=findViewById(R.id.dot5);
    lvlDots[5]=findViewById(R.id.dot6);
    lvlDots[6]=findViewById(R.id.dot7);
    lvlDots[7]=findViewById(R.id.dot8);
    lvlDots[8]=findViewById(R.id.dot9);
    lvlDots[9]=findViewById(R.id.dot10);
}

    void setQstLayout(int type){

     //text with horizentail resp
        if (type==1){
            //Hide type2
            respType2.setVisibility(View.GONE);
            bigQCont.setVisibility(View.GONE);
            //show type 1
            respType1.setVisibility(View.VISIBLE);
            txtQCont.setVisibility(View.VISIBLE);
            //setRespButton
            respondsbutt[0]=t1button1;
            respondsbutt[1]=t1button2;
            respondsbutt[2]=t1button3;
            respondsbutt[3]=t1button4;
        }
        //image with table
        if(type==2){
            //show type2
            respType2.setVisibility(View.VISIBLE);
            bigQCont.setVisibility(View.VISIBLE);
            //hid type 2
            respType1.setVisibility(View.GONE);
            txtQCont.setVisibility(View.GONE);
            //setRespButton

            respondsbutt[0]=t2button2;
            respondsbutt[1]=t2button1;
            respondsbutt[2]=t2button4;
            respondsbutt[3]=t2button3;

        }
        resetButton();
    }

void renderStage(){
    data q=filData[currentStage];
    modType.setText(module);
        setQstLayout(q.type);


        //optional qustion/remark
        if(q.optionalQst!=null){
            how_ans.setText(q.optionalQst);
        }
        else
            how_ans.setText(DEFAULT_HOW_ANS);

        if (q.type==1)
            txtQCont.setText(q.question);
    //    if (q.type==2)
//imgQst.setImageIcon(q.path);

    respondsbutt[0].setText(q.responds[0]);
    respondsbutt[1].setText(q.responds[1]);
    respondsbutt[2].setText(q.responds[2]);
    respondsbutt[3].setText(q.responds[3]);

    setRespListener();
}
void checkAnswer(){
    removeKeyListener();
    if(filData[currentStage].correctAnswer==theCurrentAnswerIndex){
        buttonCorrect(theCurrentAnswerIndex);
        yoCorrect();
    }
    else
    {
        buttonCorrect(filData[currentStage].correctAnswer);
        buttonWrong(theCurrentAnswerIndex);
        yofool();
    }
    nextStage();
}
void exit(){
        numbers_activity.this.finish();
}

void nextStage(){

        currentStage=(currentStage+1);
    if (currentStage<10){
    lvlNum.setText(currentStage+1+"/10");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                renderStage();
            }
        }, 2000);

        dynamicProgress("");
    }

    if(currentStage>filData.length-1)
            gameOver();
}


void gameOver(){
MainActivity.updateScore(localScore);
numbers_activity.this.finish();
}
void removeKeyListener(){
    respondsbutt[0].setOnClickListener(null);
    respondsbutt[1].setOnClickListener(null);
    respondsbutt[2].setOnClickListener(null);
    respondsbutt[3].setOnClickListener(null);
}
void setRespListener(){
    respondsbutt[0].setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View l) {
            theCurrentAnswerIndex=1;
            checkAnswer();

        }
    });

    respondsbutt[1].setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View l) {
            theCurrentAnswerIndex=2;
            checkAnswer();

        }
    });
    respondsbutt[2].setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View l) {
            theCurrentAnswerIndex=3;    checkAnswer();

        }
    });
    respondsbutt[3].setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View l) {
            theCurrentAnswerIndex=4;
            checkAnswer();

        }
    });
    }
void dynamicProgress(String condition){
    lvlDots[currentStage].setImageResource(R.drawable.currentdot_lvl);

}

void yoCorrect(){
    lvlDots[currentStage].setImageResource(R.drawable.correctdot_lvl);
    localScore++;
}
void yofool(){
    lvlDots[currentStage].setImageResource(R.drawable.wrong_lvl);
    }
   void buttonWrong(int i){
       respondsbutt[i-1].setTextColor(Color.WHITE);
        if(filData[currentStage].type==1)
        respondsbutt[i-1].setBackgroundResource(R.drawable.vchoiseorange);
        else
            respondsbutt[i-1].setBackgroundResource(R.drawable.choiceorange);

   }
   void buttonCorrect(int i){
       respondsbutt[i-1].setTextColor(Color.WHITE);

       if(filData[currentStage].type==1)
            respondsbutt[i-1].setBackgroundResource(R.drawable.vchoicegreen);
        else
            respondsbutt[i-1].setBackgroundResource(R.drawable.choicegreen);
    }
void resetButton(){

    if(filData[currentStage].type==1)
        for (TextView i :respondsbutt){
            i.setTextColor(TEXT_COLOR);
            i.setBackgroundResource(R.drawable.choose_answer);
        }
    else
        for (TextView i :respondsbutt){
            i.setTextColor(TEXT_COLOR);
            i.setBackgroundResource(R.drawable.choice);
        }
}

}
