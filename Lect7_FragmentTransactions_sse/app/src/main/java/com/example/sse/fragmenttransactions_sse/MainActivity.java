package com.example.sse.fragmenttransactions_sse;

//import android.app.Activity;
//import android.app.FragmentManager;
//import android.app.FragmentTransaction;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

//Step-By-Step, Fragment Transactions

    //Two basic ways of working with fragments.
    //
    //1. Just include them in the Activity's layout.
    //
    //2. Instantatiate and work with them in code.
    // in code you have much more control.

    //3. create objects to reference the views, including fragments.
private
    Frag_One  f1;
    Frag_Two  f2;
    Frag_Three  f3;

    FragmentManager fm;  // we will need this later.

    private Button btnFrag1;
    private Button btnFrag2;
    private Button btnFrag3;
    private LinearLayout FragLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    //4. get references for our views.

        btnFrag1 = (Button) findViewById(R.id.btnFrag1);
        btnFrag2 = (Button) findViewById(R.id.btnFrag2);
        btnFrag3 = (Button) findViewById(R.id.btnFrag3);
        FragLayout = (LinearLayout) findViewById(R.id.FragLayout);

//        f1 = (Frag_One) findViewById(R.id.frag1);  //Q: Why won't this work for fragments?  Does the fragment even exist in R.java?
//        A: The View of the fragment will be null. The fragments will be in the Fragment Manager; thus, before we call the findViewById function, we create the fragment manager object, then add the fragment into the R.java.

    //5a.  We actually have to create the fragments ourselves.  We left R behind when we took control of rendering.
        f1 = new Frag_One();
        f2 = new Frag_Two();
        f3 = new Frag_Three();

    //5b. Grab a reference to the Activity's Fragment Manager, Every Activity has one!
       fm = getFragmentManager ();  //that was easy.
//         fm = getSupportFragmentManager();  // **When would you use this instead??
//         A: getSupportFragmentManager() does the same thing as the getFragmentManager() function. The difference is that getSupportFragmentManager() is used for devices below API 14, while getFragmentManager() is
//          used for devices with API >= 14. Thus. getSupportFragmentManager() is often used when we try to deliver newer features to old platforms.


    //5c. Now we can "plop" fragment(s) into our container.
    // starting with fragment 1 (f1)
        FragmentTransaction ft = fm.beginTransaction ();  //Create a reference to a fragment transaction.
        ft.add(R.id.FragLayout, f1, "tag1");  //now we have added our fragment to our Activity programmatically.  The other fragments exist, but have not been added yet.

        ft.addToBackStack ("myFrag1");  //why do we do this?    This function allows the fragment to go back to the previous fragment with back button, since the fragment does not have the functionality to go back by pressing back button.
        ft.commit ();  //don't forget to commit your changes.  It is a transaction after all.

        btnFrag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrag1();
            }
        });

        btnFrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrag2();
            }
        });

        btnFrag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrag3();
            }
        });

    }

    public void showFrag1() {

        // Added Code
        if (f1.isDetached()){
            f1 = (Frag_One) fm.findFragmentByTag("tag1");   //what should we do if f1 doesn't exist anymore?  How do we check and how do we fix?
            FragmentTransaction ft = fm.beginTransaction ();  //Create a reference to a fragment transaction.
            ft.detach(f2);
            ft.detach(f3);
            ft.attach(f1);
            ft.hide(f3);
            ft.hide(f2);
            ft.show(f1);
            ft.commit();
            Toast.makeText(getApplicationContext(), "1detached", Toast.LENGTH_SHORT).show();
        }
        else if(f2.isAdded()){
            FragmentTransaction ft = fm.beginTransaction ();
            ft.add(R.id.FragLayout,f1);
            ft.hide(f2);
            ft.show(f1);
            ft.commit();
        }
        else if (f1.isHidden()){
            f1 = (Frag_One) fm.findFragmentByTag("tag1");   //what should we do if f1 doesn't exist anymore?  How do we check and how do we fix?
            FragmentTransaction ft = fm.beginTransaction ();  //Create a reference to a fragment transaction.
            if (f1.isDetached()){
                ft.detach(f2);
                ft.detach(f3);
                ft.attach(f1);
            }
            ft.hide(f2);
            ft.show(f1);
            ft.commit();
            Toast.makeText(getApplicationContext(), "1 hidden", Toast.LENGTH_SHORT).show();
        }
        // Added Code End
}

    public void showFrag2() {

//        if (f2 == null)
//          f2 = new Frag_Two();

        // Added Code
        if(f2.isHidden()){
            FragmentTransaction ft = fm.beginTransaction();
            if(f2.isDetached()){
                ft.detach(f1);
                ft.detach(f3);
                ft.attach(f2);
            }
            ft.hide(f1);
            ft.show(f2);
            ft.commit();
            Toast.makeText(getApplicationContext(), "2 hidden", Toast.LENGTH_SHORT).show();
        }
        else if (f2.isDetached()){
            FragmentTransaction ft = fm.beginTransaction();
            ft.detach(f1);
            ft.detach(f3);
            ft.attach(f2);
            ft.show(f2);
            ft.commit();
            Toast.makeText(getApplicationContext(), "2 detached", Toast.LENGTH_SHORT).show();
        }
        else {
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.FragLayout, f2);
            ft.addToBackStack("myFrag2");  //Q: What is the back stack and why do we do this? The addToBackStack() allows the fragment to gp back to the previous fragment (committing the transaction to the back stack) on the back button pressed.
            ft.commit();
            Toast.makeText(getApplicationContext(), "2 else", Toast.LENGTH_SHORT).show();

        }
        // Added Code Ended
    }


    public void showFrag3() {

        // Added Code
        if (!f3.isAdded()){
            FragmentTransaction ft = fm.beginTransaction ();   //Create a reference to a fragment transaction.
            ft.add(R.id.FragLayout, f3, "tag3");
            ft.detach(f1);   //what would happen if f1, f2, or f3 were null?  how would we check and fix this? The bottom fragment will display nothing. To fix this problem, we need to make sure that every fragment are added.
            ft.detach(f2);
            ft.attach(f3);
            ft.addToBackStack("myFrag3");
            ft.commit();
        }
        FragmentTransaction ft = fm.beginTransaction ();   //Create a reference to a fragment transaction.
        ft.detach(f1);   //what would happen if f1, f2, or f3 were null?  how would we check and fix this? The bottom fragment will display nothing. To fix this problem, we need to make sure that every fragment are added.
        ft.detach(f2);
        ft.attach(f3);
        ft.show(f3);
        ft.addToBackStack("myFrag3");
        ft.commit();
        // Added Code End
    }
}
