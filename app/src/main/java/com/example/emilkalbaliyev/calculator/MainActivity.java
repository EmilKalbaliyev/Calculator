package com.example.emilkalbaliyev.calculator;

import android.content.ClipData;
import android.icu.text.DecimalFormat;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
TextView t;
    Button bC,bDiv,bMul,bADD,bDel,b1,b2,b3,b4,b5,b6,b7,b8,b9,b0,bDot,bOut,bEqual,bIn,bSub,last;
    MenuItem i1,i2,i3;
   // MenuItem lastt;
    String str="";
    String savedData="";
    String exp=" ";
    String []last3={"empty","empty","empty"};
    int check=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t= (TextView)findViewById(R.id.text);
        t.setSelected(true);
        bC=(Button)findViewById(R.id.bC);
        bDiv=(Button)findViewById(R.id.bDiv) ;
        bMul=(Button)findViewById(R.id.bMul);
        bDel=(Button)findViewById(R.id.bDel);
        b1=(Button)findViewById(R.id.b1);
        b2=(Button)findViewById(R.id.b2);
        b3=(Button)findViewById(R.id.b3);
        b4=(Button)findViewById(R.id.b4);
        b5=(Button)findViewById(R.id.b5);
        b6=(Button)findViewById(R.id.b6);
        b7=(Button)findViewById(R.id.b7);
        b8=(Button)findViewById(R.id.b8);
        b9=(Button)findViewById(R.id.b9);
        b0=(Button)findViewById(R.id.b0);
        bDot=(Button)findViewById(R.id.bDot);
        bOut=(Button)findViewById(R.id.bOut);
        bEqual=(Button)findViewById(R.id.bEqual);
        bIn=(Button)findViewById(R.id.bIn);
        bADD=(Button)findViewById(R.id.bAdd);
        bSub=(Button)findViewById(R.id.bSub);
        str= t.getText().toString();
        Log.d("salam", ""+9.4*3.1);

        //------------

        i1 =(MenuItem) findViewById(R.id.i1);
        i2 =(MenuItem) findViewById(R.id.i2);
        i3 =(MenuItem) findViewById(R.id.i3);
        last=(Button)findViewById(R.id.last);
   //     lastt =(MenuItem) findViewById(R.id.lastt);


        last.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view) {
                final PopupMenu popupmenu =new PopupMenu(MainActivity.this,t,Gravity.END);
                popupmenu.getMenuInflater().inflate(R.menu.last3calculation,popupmenu.getMenu());
                popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    MenuItem item=popupmenu.getMenu().findItem (R.id.i1).setTitle(last3[0]); // here itemIndex is int
                    MenuItem item1=popupmenu.getMenu().findItem(R.id.i2).setTitle(last3[1]); // here itemIndex is int
                    MenuItem item2=popupmenu.getMenu().findItem(R.id.i3).setTitle(last3[2]); // here itemIndex is int


                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        String s=  (String)item.getTitle();
                        if(!s.equals("empty")) {
                            str = s.split("[=]")[0];
                            t.setText(str);
                        }
                        return false;

                    }
                });
                popupmenu.show();
            }
        });

    }
    public void action(View v){
        if(check==1&& (v==b1||v==b2||v==b3||v==b4||v==b5||v==b6||v==b7||v==b8||v==b9||v==b0)){
            str="";
            check=0;
        }
        else if (check==1 &&(v==bDot&&str.lastIndexOf(".")!=-1||v==bIn)) check=1;
        else check=0;
        if(v==bC) str="";
        else if(v==b1) str+="1";
        else if(v==b2) str+="2";
        else if(v==b3) str+="3";
        else if(v==b4) str+="4";
        else if(v==b5) str+="5";
        else if(v==b6) str+="6";
        else if(v==b7) str+="7";
        else if(v==b8) str+="8";
        else if(v==b9) str+="9";
        else if(v==b0) str+="0";
        else if(v==bDel){ if (str.length()>0) str=str.substring(0, str.length() - 1);}
        else if(v==bOut) {
            if(savedData.isEmpty()) Toast.makeText(getApplicationContext(), "You do not save any data.", Toast.LENGTH_SHORT).show();
            else if (str.lastIndexOf(".") == -1 || str.lastIndexOf(".") < str.lastIndexOf("/") ||
                    str.lastIndexOf(".") < str.lastIndexOf("*") || str.lastIndexOf(".") < str.lastIndexOf("-")
                    || str.lastIndexOf(".") < str.lastIndexOf("+")) str += savedData;
            else Toast.makeText(getApplicationContext(), "Action can`t be done: 2 dots", Toast.LENGTH_SHORT).show();
        }
        else if (v==bEqual||v==bIn||v==bSub||v==bADD||v==bDiv||v==bMul||v==bDot) {
            if (!str.isEmpty() ){
                if(!str.substring(str.length() - 1).equals("+")
                        && !str.substring(str.length() - 1).equals("-")
                        && !str.substring(str.length() - 1).equals("*")
                        && !str.substring(str.length() - 1).equals("/")
                        && !str.substring(str.length() - 1).equals(".")) {

                            if (v == bEqual) {
                                if(str.split("[/*+-]").length != 1) {
                                    check = 1;
                                    calculate("equal");
                                    last3[2] = last3[1];
                                    last3[1] = last3[0];
                                    last3[0] = exp + "=" + str;
                                }
                                else check =1;
                             //   else Toast.makeText(getApplicationContext(), "You entered only one number.", Toast.LENGTH_SHORT).show();
                            }
                            else if (v == bIn) calculate("in");
                            else if (v == bSub) str += "-";
                            else if (v == bADD) str += "+";
                            else if (v == bDiv) str += "/";
                            else if (v == bMul) str += "*";
                            else if (v == bDot){
                                if(str.lastIndexOf(".") == -1 || str.lastIndexOf(".") < str.lastIndexOf("/") || str.lastIndexOf(".") < str.lastIndexOf("*")
                                        || str.lastIndexOf(".") < str.lastIndexOf("-") || str.lastIndexOf(".") < str.lastIndexOf("+")) str += ".";
                                else Toast.makeText(getApplicationContext(), "Action can`t be done: 2 dots.", Toast.LENGTH_SHORT).show();
                                }

                }
                else Toast.makeText(getApplicationContext(), "Expression is ended with '"+str.substring(str.length() - 1)+"'.", Toast.LENGTH_SHORT).show();
            }
            else Toast.makeText(getApplicationContext(), "Please enter number.", Toast.LENGTH_SHORT).show();

        }
        else Toast.makeText(getApplicationContext(), "Action can`t be done.", Toast.LENGTH_SHORT).show();

        t.setText(str);

    }
    public  void calculate(String type){
        DecimalFormat df = new DecimalFormat("#.####");
        exp=str;
        boolean zero=false;
            String ope[]=str.split("[+-]");
            String operators[]=str.split("[0-9./*]+");

            for(int i=0;i<ope.length;i++){
                Log.d("first", ope[i]);
                String high[]=ope[i].split("[0-9.]+");
                String number[]=ope[i].split("[*/]");

                double agregate = Double.parseDouble(number[0]);
                for(int j=1;j<high.length;j++){
                    if(high[j].equals("*")) agregate *= Double.parseDouble(number[j]);
                    if(high[j].equals("/")&&!number[j].equals("0")) agregate /= Double.parseDouble(number[j]);
                    else if (number[j].equals("0")) zero =true;
                }
                ope[i]=Double.toString(agregate);

            }
            if (!zero) {
                double main = Double.parseDouble(ope[0]);
                for (int i = 1; i < ope.length; i++) {
                    if (operators[i].equals("+"))
                        main += Double.parseDouble(ope[i]);
                    if (operators[i].equals("-"))
                        main -= Double.parseDouble(ope[i]);
                }
                if(type.equals("equal")) {
                    if (main >= 0)
                     str=df.format(main);
                    else {
                        str = "0";
                        Toast.makeText(getApplicationContext(), "Result is negative. Rounded to zero.", Toast.LENGTH_SHORT).show();
                    }
                    }
                else if(type.equals("in")){
                    if (main >=0)
                        savedData = df.format(main);
                    else {
                        savedData = "0";
                        Toast.makeText(getApplicationContext(), "Result is negative. Rounded to zero.", Toast.LENGTH_SHORT).show();

                    }
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "You can`t divide by 0", Toast.LENGTH_SHORT).show();
                check =0;
            }

    }
//-------------------------------------------------------------------------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("text", String.valueOf(t.getText()));
        outState.putString("save", String.valueOf(savedData));
        outState.putString("exp", String.valueOf(exp));
        outState.putString("l1", String.valueOf(last3[0]));
        outState.putString("l2", String.valueOf(last3[1]));
        outState.putString("l3", String.valueOf(last3[2]));
        outState.putString("ch", String.valueOf(check));

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            String text = savedInstanceState.getString("text");
            if (text != null){
                str =text;
                t.setText(text);
            }
            String save = savedInstanceState.getString("save");
            if (save!=null) savedData=save;
            String expr = savedInstanceState.getString("exp");
            if (expr!=null) exp=expr;
            String l1 = savedInstanceState.getString("l1");
            if (l1!=null) last3[0]=l1;
            String l2 = savedInstanceState.getString("l2");
            if (l2!=null) last3[1]=l2;
            String l3 = savedInstanceState.getString("l3");
            if (l3!=null) last3[2]=l3;
            int ch = savedInstanceState.getInt("ch");
            check =ch;
        }

    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.lastt) {

            final PopupMenu popupmenu = new PopupMenu(MainActivity.this, t, Gravity.RIGHT);
            popupmenu.getMenuInflater().inflate(R.menu.last3calculation, popupmenu.getMenu());
            popupmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                MenuItem item = popupmenu.getMenu().findItem(R.id.i1).setTitle(last3[0]); // here itemIndex is int
                MenuItem item1 = popupmenu.getMenu().findItem(R.id.i2).setTitle(last3[1]); // here itemIndex is int
                MenuItem item2 = popupmenu.getMenu().findItem(R.id.i3).setTitle(last3[2]); // here itemIndex is int


                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    return false;
                }
            });
            popupmenu.show();


        }
        return true;
    }

*/

}
